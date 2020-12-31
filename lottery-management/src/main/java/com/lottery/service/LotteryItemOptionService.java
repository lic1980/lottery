package com.lottery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lottery.model.Lottery;
import com.lottery.model.LotteryItem;
import com.lottery.model.LotteryItemOption;
import com.lottery.repo.LotteryItemOptionRepository;
import com.lottery.util.IdUtils;

@Service
public class LotteryItemOptionService {
	@Autowired
	private LotteryItemOptionRepository repo;

	public Optional<LotteryItemOption> findById(Long id) {
		return repo.findById(id);
	}
	
	public void deleteById(Long id) {
		repo.deleteById(id);
	}
	
	public void deleteByItem(LotteryItem item) {
		repo.deleteByItem(item);
	}
	
	public void deleteByLottery(Lottery lottery) {
		repo.deleteByItemLottery(lottery);
	}
	
	public List<LotteryItemOption> findByLotteryId(Long lotteryId) {
		Lottery lottery = new Lottery();
		lottery.setId(lotteryId);
		Order itemOrder = new Order(Direction.ASC, "item.order");
		Order optionOrder = new Order(Direction.ASC, "order");
		return repo.findByItemLottery(lottery, Sort.by(itemOrder, optionOrder));
	}
	
	public List<LotteryItemOption> findByLotteryItemId(Long lotteryItemId) {
		LotteryItem item = new LotteryItem();
		item.setId(lotteryItemId);
		Order optionOrder = new Order(Direction.ASC, "order");
		return repo.findByItem(item, Sort.by(optionOrder));
	}
	
	public List<LotteryItemOption> findByLotteryIds(List<Long> lotteryIds) {
		return repo.findByIdIn(lotteryIds);
	}

	public LotteryItemOption saveOrUpdate(LotteryItemOption option) {
		if (StringUtils.isEmpty(option.getId())) {
			option.setId(IdUtils.generateId());
		}
		return repo.save(option);
	}
}
