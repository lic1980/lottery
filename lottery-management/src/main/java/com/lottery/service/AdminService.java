package com.lottery.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.model.Agent;
import com.lottery.repo.AdminRepository;

@Service
public class AdminService {
	@Autowired
	private AdminRepository repo;

	public Optional<Agent> validatePassword(String tel, String plainPassword) {
		return repo.findByTelAndPassword(tel, plainPassword);
	}
}
