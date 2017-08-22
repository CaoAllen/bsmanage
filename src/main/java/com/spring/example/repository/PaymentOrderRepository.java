package com.spring.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.example.domain.PaymentOrder;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long>{
	public PaymentOrder findByOrderNo(String orderNo);
}
