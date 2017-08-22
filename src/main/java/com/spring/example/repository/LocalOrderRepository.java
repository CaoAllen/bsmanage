package com.spring.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.example.domain.LocalOrder;

public interface LocalOrderRepository extends JpaRepository<LocalOrder, Long>{
	public LocalOrder findByOrderNo(String orderNo);
}
