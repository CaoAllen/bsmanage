package com.spring.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.example.domain.Sales;

public interface SalesRepository extends JpaRepository<Sales, Long>{
	public Sales findBySiteId(Long siteId);
}
