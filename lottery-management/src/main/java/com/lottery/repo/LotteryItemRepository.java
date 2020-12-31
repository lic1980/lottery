package com.lottery.repo;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lottery.model.Lottery;
import com.lottery.model.LotteryItem;

public interface LotteryItemRepository extends JpaRepository<LotteryItem, Long>{
	List<LotteryItem> findByLottery(Lottery lottery, Sort sort);
	void deleteByLottery(Lottery lottery);
}
