package com.spring.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.example.domain.Price;

public interface PriceRepository extends JpaRepository<Price, Long>{
	public List<Price> findBySiteId(Long id);
}
