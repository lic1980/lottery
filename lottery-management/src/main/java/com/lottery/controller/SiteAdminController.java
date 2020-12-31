package com.lottery.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lottery.exception.InvalidParameter;
import com.lottery.model.Agent;
import com.lottery.model.Customer;
import com.lottery.model.Lottery;
import com.lottery.model.LotteryItem;
import com.lottery.model.LotteryItemOption;
import com.lottery.service.AgentService;
import com.lottery.service.CustomerService;
import com.lottery.service.LotteryItemOptionService;
import com.lottery.service.LotteryItemService;
import com.lottery.service.LotteryService;

@RestController
@RequestMapping(value = "/api/v1")
public class SiteAdminController {
	private static Logger LOGGER = Logger.getLogger(SiteAdminController.class);

	@Autowired
	private AgentService agentService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private LotteryService lotteryService;
	@Autowired
	private LotteryItemService lotteryItemService;
	@Autowired
	private LotteryItemOptionService lotteryItemOptionService;

	@GetMapping(value = "/admins/{id}/agents")
	public Page<Agent> getAllAgents(@RequestParam int page, @RequestParam int size) throws InvalidParameter {
		return agentService.findAll(page - 1, size);
	}

	@GetMapping(value = "/admins/{id}/users")
	public Page<Customer> getAllUsers(@RequestParam int page, @RequestParam int size) throws InvalidParameter {
		return customerService.findAll(page - 1, size);
	}

	@PostMapping(value = "/admins/{id}/lotteries")
	public Lottery postLottery(@RequestBody Lottery lottery) {
		return lotteryService.saveOrUpdate(lottery);
	}

	@PostMapping(value = "/admins/{aid}/lotteries/{lid}/items")
	public LotteryItem postLotteryItem(@PathVariable Long lid, @RequestBody LotteryItem lotteryItem)
			throws InvalidParameter {
		Lottery lottery = lotteryService.findById(lid).orElseThrow(InvalidParameter::new);
		lotteryItem.setLottery(lottery);
		return lotteryItemService.saveOrUpdate(lotteryItem);
	}

	@PostMapping(value = "/admins/{aid}/lotteries/{lid}/items/{iid}/options")
	public LotteryItemOption postLotteryItemOption(@PathVariable Long iid,
			@RequestBody LotteryItemOption lotteryItemOption) throws InvalidParameter {
		LotteryItem item = lotteryItemService.findById(iid).orElseThrow(InvalidParameter::new);
		lotteryItemOption.setItem(item);
		return lotteryItemOptionService.saveOrUpdate(lotteryItemOption);
	}

	@PostMapping(value = "/admins/{aid}/lotteries/{lid}")
	public Lottery postLottery(@PathVariable Long lid, @RequestBody Lottery lottery) throws InvalidParameter {
		lotteryService.findById(lid).orElseThrow(InvalidParameter::new);
		return lotteryService.saveOrUpdate(lottery);
	}

	@PutMapping(value = "/admins/{aid}/lotteries/{lid}")
	public Lottery putLottery(@PathVariable Long lid, @RequestBody Lottery lottery) throws InvalidParameter {
		Lottery target = lotteryService.findById(lid).orElseThrow(InvalidParameter::new);
		target.setBaseFee(lottery.getBaseFee());
		target.setMinItemNum(lottery.getMinItemNum());
		target.setName(lottery.getName());

		return lotteryService.saveOrUpdate(target);
	}

	@PutMapping(value = "/admins/{aid}/lotteries/{lid}/items/{iid}")
	public LotteryItem putLotteryItem(@PathVariable Long iid, @RequestBody LotteryItem lotteryItem)
			throws InvalidParameter {
		
		LotteryItem target = lotteryItemService.findById(iid).orElseThrow(InvalidParameter::new);
		target.setName(lotteryItem.getName());
		target.setOrder(lotteryItem.getOrder());
		return lotteryItemService.saveOrUpdate(target);
	}

	@PutMapping(value = "/admins/{aid}/lotteries/{lid}/items/{iid}/options/{oid}")
	public LotteryItemOption putLotteryItemOption(@PathVariable Long oid,
			@RequestBody LotteryItemOption lotteryItemOption) throws InvalidParameter {
		LotteryItemOption target = lotteryItemOptionService.findById(oid).orElseThrow(InvalidParameter::new);
		target.setOrder(lotteryItemOption.getOrder());
		target.setText(lotteryItemOption.getText());
		return lotteryItemOptionService.saveOrUpdate(lotteryItemOption);
	}

	@DeleteMapping(value = "/admins/{aid}/lotteries/{lid}/items/{iid}/options/{oid}")
	public void deleteLotteryItemOption(@PathVariable Long oid) throws InvalidParameter {
		lotteryItemOptionService.findById(oid).orElseThrow(InvalidParameter::new);
		lotteryItemOptionService.deleteById(oid);
	}
	
	@DeleteMapping(value = "/admins/{aid}/lotteries/{lid}/items/{iid}")
	public void deleteLotteryItem(@PathVariable Long iid) throws InvalidParameter {
		LotteryItem item = lotteryItemService.findById(iid).orElseThrow(InvalidParameter::new);

		lotteryItemOptionService.deleteByItem(item);
		lotteryItemService.deleteById(iid);
	}
	
	@DeleteMapping(value = "/admins/{aid}/lotteries/{lid}")
	public void deleteLottery(@PathVariable Long lid) throws InvalidParameter {
		Lottery lottery = lotteryService.findById(lid).orElseThrow(InvalidParameter::new);

		lotteryItemOptionService.deleteByLottery(lottery);
		lotteryItemService.deleteByLottery(lottery);
		lotteryService.deleteById(lid);
	}
}
