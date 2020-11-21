package com.lottery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lottery.model.Lottery;
import com.lottery.repo.LotteryRepository;
import com.lottery.util.IdUtils;

@Service
public class LotteryService {
	@Autowired
	private LotteryRepository repo;
	
	public List<Lottery> find() {
		return repo.findAll();
	}
	
	public Optional<Lottery> findById(Long id) {
		return repo.findById(id);
	}
	
	public Lottery saveOrUpdate(Lottery lottery) {
		if (StringUtils.isEmpty(lottery.getId())) {
			lottery.setId(IdUtils.generateId());
		}
		return repo.save(lottery);
	}
}
