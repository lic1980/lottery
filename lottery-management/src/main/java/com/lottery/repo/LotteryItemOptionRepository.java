package com.lottery.repo;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lottery.model.Lottery;
import com.lottery.model.LotteryItem;
import com.lottery.model.LotteryItemOption;

public interface LotteryItemOptionRepository extends JpaRepository<LotteryItemOption, Long>{
	List<LotteryItemOption> findByItemLottery(Lottery lottery, Sort sort);
	List<LotteryItemOption> findByItem(LotteryItem item, Sort sort);
	List<LotteryItemOption> findByIdIn(List<Long> ids);
	void deleteByItem(LotteryItem item);
	void deleteByItemLottery(Lottery lottery);
}
