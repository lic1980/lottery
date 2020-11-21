package com.lottery.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lottery.model.Admin;
import com.lottery.model.Agent;

public interface AdminRepository  extends JpaRepository<Admin, Long>{
	Optional<Agent> findByTelAndPassword(String tel, String password);
}
