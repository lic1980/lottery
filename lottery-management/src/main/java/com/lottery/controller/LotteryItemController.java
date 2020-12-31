package com.lottery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lottery.model.LotteryItem;
import com.lottery.service.LotteryItemService;

@RestController
@RequestMapping(value = "/api/v1")
public class LotteryItemController {

	@Autowired
	private LotteryItemService itemService;

	@GetMapping(value = "/lotteries/{id}/lottery-items")
	public List<LotteryItem> getLotteriess(@PathVariable Long id) {
		return itemService.findByLotteryId(id);
	}

	@GetMapping(value = "/lotteries/{lid}/lottery-items/{iid}")
	public LotteryItem getLottery(@PathVariable Long lid, @PathVariable Long iid) {
		return itemService.findById(iid).orElse(new LotteryItem());
	}
}
