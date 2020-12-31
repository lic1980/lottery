package com.lottery.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.model.SiteAdmin;
import com.lottery.repo.SiteAdminRepository;
import com.lottery.util.SecurityUtils;

@Service
public class SiteAdminService {
	@Autowired
	private SiteAdminRepository repo;

	public Optional<SiteAdmin> validatePassword(String tel, String plainPassword) {
		return repo.findByTelAndPassword(tel, SecurityUtils.encryptPassword(plainPassword));
	}
}
