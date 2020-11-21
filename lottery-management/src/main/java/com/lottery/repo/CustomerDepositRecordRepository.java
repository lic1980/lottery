package com.lottery.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.lottery.model.AgentCustomerDepositRecord;

public interface CustomerDepositRecordRepository extends JpaRepository<AgentCustomerDepositRecord, Long>{
}
