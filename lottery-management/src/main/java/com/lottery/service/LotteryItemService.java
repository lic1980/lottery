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
import com.lottery.repo.LotteryItemRepository;
import com.lottery.util.IdUtils;

@Service
public class LotteryItemService {
	@Autowired
	private LotteryItemRepository repo;
	
	public List<LotteryItem> findByLotteryId(Long lotteryId) {
		Lottery lottery = new Lottery();
		lottery.setId(lotteryId);
		Order order = new Order(Direction.ASC, "order");
		return repo.findByLottery(lottery, Sort.by(order));
	}
	
	public void deleteById(Long id) {
		repo.deleteById(id);
	}
	
	public Optional<LotteryItem> findById(Long id) {
		return repo.findById(id);
	}
	
	public LotteryItem saveOrUpdate(LotteryItem item) {
		if (StringUtils.isEmpty(item.getId())) {
			item.setId(IdUtils.generateId());
		}
		return repo.save(item);
	}

	public void deleteByLottery(Lottery lottery) {
		repo.deleteByLottery(lottery);
	}

}
