package com.lottery.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lottery.model.Agent;
import com.lottery.model.Customer;
import com.lottery.repo.CustomerRepository;
import com.lottery.util.IdUtils;
import com.lottery.util.SecurityUtils;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository repo;

	public Customer saveOrUpdate(Customer customer) {
		if (StringUtils.isEmpty(customer.getId())) {
			customer.setId(IdUtils.generateId());
		}
		if (!StringUtils.isEmpty(customer.getPlainPassword())) {
			customer.setPassword(SecurityUtils.encryptPassword(customer.getPlainPassword()));
		}
		return repo.save(customer);
	}
	
	public Page<Customer> findAll(int pageNum, int pageSize) {
		Pageable page = PageRequest.of(pageNum, pageSize);
		return repo.findAll(page);
	}
	
	public Optional<Customer> findById(Long id) {
		return repo.findById(id);
	}
	
	public Optional<Customer> findByTel(String tel) {
		return repo.findByTel(tel);
	}
	
	public Page<Customer> findByAgent(Long agentId, int pageNum, int pageSize) {
		PageRequest page = PageRequest.of(pageNum, pageSize);
		Agent agent = new Agent();
		agent.setId(agentId);
		return repo.findByAgent(agent, page);
	}

	public Optional<Customer> validatePassword(String tel, String plainPassword) {
		return repo.findByTelAndPassword(tel, SecurityUtils.encryptPassword(plainPassword));
	}
}
