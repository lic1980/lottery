package com.lottery.repo;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lottery.model.Lottery;
import com.lottery.model.LotteryRound;

public interface LotteryRoundRepository extends JpaRepository<LotteryRound, Long>{
	Page<LotteryRound> findByLotteryAndCutOffTimeGreaterThan(Lottery lottery, Date date, Pageable pageable);
	Page<LotteryRound> findByLottery(Lottery lottery, Pageable pageable);
}
