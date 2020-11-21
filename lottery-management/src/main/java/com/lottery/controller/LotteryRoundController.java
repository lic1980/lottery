package com.lottery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lottery.model.LotteryRound;
import com.lottery.service.LotteryRoundService;

@RestController
@RequestMapping(value = "/api/v1")
public class LotteryRoundController {

	@Autowired
	private LotteryRoundService lotteryRoundService;

	@GetMapping(value = "/lotteries/{id}/rounds")
	public Page<LotteryRound> getLotteryRounds(@PathVariable Long id, @RequestParam(required = false, defaultValue = "active") String status, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "20") int size) {
		return lotteryRoundService.findByLotteryAndStatus(id, status, page - 1, size);
	}
}
