package com.spring.example.service;

import java.io.IOException;
import java.sql.SQLException;

import org.codehaus.jackson.JsonProcessingException;

import com.spring.example.domain.LocalOrder;
import com.spring.example.domain.PaymentOrder;
import com.spring.example.model.SignInfo;
import com.spring.example.utils.OrderStatus;

public interface OrderService {
	void addOrder(String orderNo, int totalFee, String orders, String orderInfo, SignInfo signInfo)
			throws SQLException, JsonProcessingException, IOException;

	LocalOrder getLocalOrder(String orderNo) throws Exception;
	
	PaymentOrder getPaymentOrder(String orderNo) throws Exception;
	
	PaymentOrder updatePaymentOrder(Long orderId, OrderStatus oStatus) throws Exception;
	
	PaymentOrder updatePaymentOrder(String orderNo, OrderStatus oStatus) throws Exception;
}
