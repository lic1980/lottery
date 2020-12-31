package com.lottery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lottery.model.Lottery;
import com.lottery.service.LotteryService;

@RestController
@RequestMapping(value = "/api/v1")
public class LotteryController {

	@Autowired
	private LotteryService lotteryService;

	@GetMapping(value = "/lotteries")
	public Page<Lottery> getLotteriess(@RequestParam int page, @RequestParam int size) {
		return lotteryService.find(page -1 , size);
	}

	@GetMapping(value = "/lotteries/{id}")
	public Lottery getLottery(@PathVariable Long id) {
		return lotteryService.findById(id).orElse(new Lottery());
	}
}
