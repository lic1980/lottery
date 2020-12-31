package com.lottery.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lottery.exception.InvalidParameter;
import com.lottery.model.Agent;
import com.lottery.model.AgentCustomerDepositRecord;
import com.lottery.model.Customer;
import com.lottery.model.CustomerBid;
import com.lottery.model.CustomerBidStatus;
import com.lottery.service.AgentService;
import com.lottery.service.CustomerBidService;
import com.lottery.service.CustomerDepositRecordService;
import com.lottery.service.CustomerService;
import com.lottery.service.ImageService;

@RestController
@RequestMapping(value = "/api/v1")
public class AgentController {
	private static final int DEPOSIT_MAX = 5000;
	private static final int DEPOSIT_MIN = -5000;
	@Autowired
	private AgentService agentService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerBidService customerBidService;
	@Autowired
	private CustomerDepositRecordService customerDepositRecordService;

	@GetMapping(value = "/agents/{id}")
	public Agent getAgent(@PathVariable Long id) {
		return agentService.findById(id).orElse(new Agent());
	}

	@GetMapping(value = "/agents/{id}/bids")
	public Page<CustomerBid> getAgentBids(@PathVariable Long id, @RequestParam String status, @RequestParam int page,
			@RequestParam int size) {
		List<CustomerBidStatus> statuses = new ArrayList<>();
		if ("history".equals(status)) {
			statuses.add(CustomerBidStatus.BIDDEN);
		} else {
			statuses.add(CustomerBidStatus.NEW);
			statuses.add(CustomerBidStatus.ACKED);
		}
		return customerBidService.findByCustomerAgentAndStatus(id, statuses, page - 1, size);
	}

	@PatchMapping(value = "/agents/{id}/bids/{bidId}")
	public CustomerBid changeBid(@PathVariable(name = "id") Long agentId, @PathVariable(name = "bidId") Long bidId)
			throws InvalidParameter {
		CustomerBid customerBid = customerBidService.findById(bidId).orElseThrow(InvalidParameter::new);
		if (!agentId.equals(customerBid.getCustomer().getAgent().getId())) {
			throw new InvalidParameter();
		}
		if (customerBid.getStatus() != CustomerBidStatus.NEW && customerBid.getStatus() != CustomerBidStatus.ACKED) {
			throw new InvalidParameter();
		}

		customerBid.setStatus(CustomerBidStatus.ACKED);
		return customerBidService.saveOrUpdateCustomerBid(customerBid);
	}

	@PostMapping(value = "/agents/{id}/bids/{bidId}/files")
	public String fileUpload(@PathVariable(name = "id") Long agentId, @PathVariable(name = "bidId") Long bidId,
			@RequestParam(value = "file") MultipartFile file) throws InvalidParameter {
		if (file.isEmpty()) {
			throw new InvalidParameter();
		}

		CustomerBid customerBid = customerBidService.findById(bidId).orElseThrow(InvalidParameter::new);
		if (!agentId.equals(customerBid.getCustomer().getAgent().getId())) {
			throw new InvalidParameter();
		}

		try {
			String path = imageService.store(file);
			customerBid.setImagePath(path);
			customerBid.setStatus(CustomerBidStatus.BIDDEN);
			customerBid.setBidTime(new Date());
			customerBidService.saveOrUpdateCustomerBid(customerBid);
			return path;
		} catch (IOException e) {
			throw new InvalidParameter();
		}
	}

	@GetMapping(value = "/agents/{id}/customers")
	public Page<Customer> getCustomers(@PathVariable(value = "id") Long agentId, @RequestParam int page,
			@RequestParam int size) {
		return customerService.findByAgent(agentId, page - 1, size);
	}

	@PostMapping(value = "/agents/{id}/customers/{customerId}/deposits")
	public AgentCustomerDepositRecord addCustomerBid(@PathVariable(value = "customerId") String customerId,
			@RequestBody AgentCustomerDepositRecord deposit) throws InvalidParameter {

		if (deposit.getAmount() < DEPOSIT_MIN || deposit.getAmount() > DEPOSIT_MAX) {
			throw new InvalidParameter();
		}

		if (deposit.getAgent() == null || deposit.getCustomer() == null) {
			throw new InvalidParameter();
		}

		Agent agent = agentService.findById(deposit.getAgent().getId()).orElseThrow(InvalidParameter::new);
		Customer customer = customerService.findById(deposit.getCustomer().getId()).orElseThrow(InvalidParameter::new);
		if (!customer.getAgent().getId().equals(agent.getId())) {
			throw new InvalidParameter();
		}

		return customerDepositRecordService.addCustomerDepositRecord(deposit);
	}
}
