package com.lottery.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lottery.model.Lottery;
import com.lottery.repo.LotteryRepository;
import com.lottery.util.IdUtils;

@Service
public class LotteryService {
	@Autowired
	private LotteryRepository repo;
	
	public Page<Lottery> find(int pageNum, int pageSize) {
		Pageable page = PageRequest.of(pageNum, pageSize);
		return repo.findAll(page);
	}
	
	public void deleteById(Long id) {
		repo.deleteById(id);
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
