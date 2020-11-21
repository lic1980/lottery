package com.lottery.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lottery.model.Lottery;
import com.lottery.model.LotteryRound;
import com.lottery.repo.LotteryRoundRepository;
import com.lottery.util.IdUtils;

@Service
public class LotteryRoundService {
	@Autowired
	private LotteryRoundRepository repo;
	
	public List<LotteryRound> find() {
		return repo.findAll();
	}
	
	public Optional<LotteryRound> findById(Long id) {
		return repo.findById(id);
	}
	
	public Page<LotteryRound> findByLotteryAndStatus(Long lotteryId, String status, int pageNum, int pageSize) {
		PageRequest page = PageRequest.of(pageNum, pageSize);
		Lottery lottery = new Lottery();
		lottery.setId(lotteryId);
		if ("active".equals(status)) {
			return repo.findByLotteryAndCutOffTimeGreaterThan(lottery, new Date(), page);
		} 
		return repo.findByLottery(lottery, page);
	}
	
	public LotteryRound saveOrUpdate(LotteryRound lotteryRound) {
		if (StringUtils.isEmpty(lotteryRound.getId())) {
			lotteryRound.setId(IdUtils.generateId());
		}
		return repo.save(lotteryRound);
	}
}
