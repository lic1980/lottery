package com.lottery.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lottery.model.Agent;
import com.lottery.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	Page<Customer> findByAgent(Agent agent, Pageable pageable);
	Optional<Customer> findByTel(String tel);
	Optional<Customer> findByTelAndPassword(String tel, String encryptPassword);
}
