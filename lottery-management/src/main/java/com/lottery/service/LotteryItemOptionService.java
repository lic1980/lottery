package com.lottery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.lottery.model.Lottery;
import com.lottery.model.LotteryItemOption;
import com.lottery.repo.LotteryItemOptionRepository;

@Service
public class LotteryItemOptionService {
	@Autowired
	private LotteryItemOptionRepository repo;

	
	public List<LotteryItemOption> findByLotteryId(Long lotteryId) {
		Lottery lottery = new Lottery();
		lottery.setId(lotteryId);
		Order itemOrder = new Order(Direction.ASC, "item.order");
		Order optionOrder = new Order(Direction.ASC, "order");
		return repo.findByItemLottery(lottery, Sort.by(itemOrder, optionOrder));
	}
	
	public List<LotteryItemOption> findByLotteryIds(List<Long> lotteryIds) {
		return repo.findByIdIn(lotteryIds);
	}

}
