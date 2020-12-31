package com.lottery.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lottery.exception.InvalidParameter;
import com.lottery.model.Customer;
import com.lottery.model.CustomerBid;
import com.lottery.model.Lottery;
import com.lottery.model.LotteryItem;
import com.lottery.model.LotteryItemOption;
import com.lottery.model.LotteryRound;
import com.lottery.service.CustomerBidService;
import com.lottery.service.CustomerService;
import com.lottery.service.LotteryItemOptionService;
import com.lottery.service.LotteryRoundService;
import com.lottery.util.LotteryMathUtils;

@RestController
@RequestMapping(value = "/api/v1")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerBidService customerBidService;
	@Autowired
	private LotteryRoundService lotteryRoundService;
	@Autowired
	private LotteryItemOptionService lotteryItemOptionService;

	
	
	@GetMapping(value = "/customers/{id}")
	public Customer getCustomer(@PathVariable Long id) {
		return customerService.findById(id).orElse(new Customer());
	}

	@GetMapping(value = "/customers/{id}/bids")
	public Page<CustomerBid> getCustomerBids(@PathVariable Long id, @RequestParam int page, @RequestParam int size) {
		return customerBidService.findByCustomer(id, page - 1, size);
	}

	@PostMapping(value = "/customers/{id}/bids")
	public CustomerBid addCustomerBid(@PathVariable(value = "id") Long customerId, @RequestBody CustomerBid bid)
			throws InvalidParameter {
		if (bid.getLotteryRound() == null) {
			throw new InvalidParameter();
		}
		if (bid.getTimes() < 1) {
			throw new InvalidParameter();
		}
		LotteryRound lotteryRound = lotteryRoundService.findById(bid.getLotteryRound().getId())
				.orElseThrow(InvalidParameter::new);
		Lottery lottery = lotteryRound.getLottery();
		List<LotteryItemOption> options = lotteryItemOptionService.findByLotteryId(lottery.getId());

		Map<LotteryItem, Integer> lotteryItemSelectedNumMap = new HashMap<>();
		for (LotteryItemOption optionPassed : bid.getOptions()) {

			LotteryItemOption option = findOption(options, optionPassed.getId()).orElseThrow(InvalidParameter::new);
			LotteryItem item = option.getItem();
			if (lotteryItemSelectedNumMap.containsKey(item)) {
				lotteryItemSelectedNumMap.put(item, lotteryItemSelectedNumMap.get(item) + 1);
			} else {
				lotteryItemSelectedNumMap.put(item, 1);
			}
		}
		if (lotteryItemSelectedNumMap.size() < lottery.getMinItemNum()) {
			throw new InvalidParameter();
		}
		int totalFee = 1;
		for(Entry<LotteryItem, Integer> entry:lotteryItemSelectedNumMap.entrySet()) {
			int minOptionNum = entry.getKey().getMinOptionNum();
			int optionSelectedNum = entry.getValue();
			if (optionSelectedNum < minOptionNum) {
                totalFee = 0;
            } else {
                totalFee = totalFee * (LotteryMathUtils.getFeeUnit(optionSelectedNum, minOptionNum));
            }
		}		
		float fee = totalFee * lottery.getBaseFee() * bid.getTimes();
		bid.setFee(fee);
		Customer customer = customerService.findById(customerId).orElseThrow(InvalidParameter::new);
		if (customer.getDeposit() - fee < 0) {
			throw new InvalidParameter();
		}
		return customerBidService.saveOrUpdateCustomerBid(bid);
	}

	private Optional<LotteryItemOption> findOption(List<LotteryItemOption> options, Long	 optionId) {
		for (LotteryItemOption option : options) {
			if (option.getId().equals(optionId)) {
				return Optional.of(option);
			}
		}
		return Optional.empty();
	}
}
