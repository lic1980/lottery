package com.lottery.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lottery.model.Lottery;

public interface LotteryRepository extends JpaRepository<Lottery, Long>{
	
}
