package com.lottery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lottery.model.LotteryItemOption;
import com.lottery.service.LotteryItemOptionService;

@RestController
@RequestMapping(value = "/api/v1")
public class LotteryItemOptionController {

	@Autowired
	private LotteryItemOptionService lotteryItemOptionService;
	
	
	@GetMapping(value = "/lotteries/{id}/options")
	public List<LotteryItemOption> getLottery(@PathVariable Long id) {
		return lotteryItemOptionService.findByLotteryId(id);
	}
}
