package com.lottery.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lottery.model.SiteAdmin;

public interface SiteAdminRepository  extends JpaRepository<SiteAdmin, Long>{
	Optional<SiteAdmin> findByTelAndPassword(String tel, String password);
}
