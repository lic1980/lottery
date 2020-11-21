package com.lottery.repo;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lottery.model.Agent;
import com.lottery.model.Customer;
import com.lottery.model.CustomerBid;
import com.lottery.model.CustomerBidStatus;

public interface CustomerBidRepository extends JpaRepository<CustomerBid, Long>{
	Page<CustomerBid> findByCustomer(Customer cus, Pageable pageable);
	Page<CustomerBid> findByCustomerAgentAndStatusIn(Agent agent, List<CustomerBidStatus> statuses, Pageable pageable);
}
