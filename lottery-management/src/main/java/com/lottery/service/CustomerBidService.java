package com.lottery.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lottery.model.Agent;
import com.lottery.model.Customer;
import com.lottery.model.CustomerBid;
import com.lottery.model.CustomerBidStatus;
import com.lottery.repo.CustomerBidRepository;
import com.lottery.util.IdUtils;

@Service
public class CustomerBidService {
	@Autowired
	private CustomerBidRepository repo;
	@Autowired
	private CustomerService customerService;
	
	public Optional<CustomerBid> findById(Long id) {
		return repo.findById(id);
	}
	
	public Page<CustomerBid> findByCustomer(Long cusId, int pageNum, int pageSize) {
		PageRequest page = PageRequest.of(pageNum, pageSize);
		Customer cus = new Customer();
		cus.setId(cusId);
		return repo.findByCustomer(cus, page);
	}
	
	public Page<CustomerBid> findByCustomerAgentAndStatus(Long agentId, List<CustomerBidStatus> statuses, int pageNum, int pageSize) {
		PageRequest page = PageRequest.of(pageNum, pageSize);
		Agent agent = new Agent();
		agent.setId(agentId);
		return repo.findByCustomerAgentAndStatusIn(agent, statuses, page);
	}
	
	@Transactional(rollbackOn = {RuntimeException.class, Error.class})
	public CustomerBid saveOrUpdateCustomerBid(CustomerBid bid) {
		if (StringUtils.isEmpty(bid.getId())) {
			bid.setId(IdUtils.generateId());
			bid.setStatus(CustomerBidStatus.NEW);
			bid.setCreateTime(new Date());
		}
		CustomerBid result = repo.save(bid);
		Customer customer = bid.getCustomer();
		customer.setDeposit(customer.getDeposit() - bid.getFee());
		customerService.saveOrUpdate(customer);
		return result;
	}
}
