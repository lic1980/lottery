package com.lottery.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lottery.model.Agent;

public interface AgentRepository extends JpaRepository<Agent, Long>{
	Optional<Agent> findByTel(String tel);
	Optional<Agent> findByTelAndPassword(String tel, String password);
}
