package com.spring.example.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

import javax.inject.Inject;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.example.domain.LocalOrder;
import com.spring.example.domain.PaymentOrder;
import com.spring.example.exception.ResourceNotFoundException;
import com.spring.example.model.SignInfo;
import com.spring.example.repository.LocalOrderRepository;
import com.spring.example.repository.PaymentOrderRepository;
import com.spring.example.utils.JsonUtil;
import com.spring.example.utils.OrderStatus;
import com.spring.example.utils.SessionContextUtil;


@Service
@Transactional
public class OrderServiceImpl implements OrderService{

	private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

	private PaymentOrderRepository paymentOrderRepository;
	private LocalOrderRepository localOrderRepository;

	@Inject
	public OrderServiceImpl(PaymentOrderRepository paymentOrderRepository, LocalOrderRepository localOrderRepository) {
		this.paymentOrderRepository = paymentOrderRepository;
		this.localOrderRepository = localOrderRepository;
	}
	
	public void addOrder(String orderNo, int totalFee, String orders, String orderInfo, SignInfo signInfo) throws SQLException, JsonProcessingException, IOException{
		addPaymentOrder(orderNo, totalFee, signInfo);
		addLocalOrder(orderNo, totalFee, orders, orderInfo);
	}
	
	public LocalOrder getLocalOrder(String orderNo) throws Exception{
		LocalOrder order = localOrderRepository.findByOrderNo(orderNo);
		if(order == null){
			throw new ResourceNotFoundException(orderNo);
		}
		return order;
	}
	
	public PaymentOrder getPaymentOrder(String orderNo) throws Exception{
		PaymentOrder order = paymentOrderRepository.findByOrderNo(orderNo);
		if(order == null){
			throw new ResourceNotFoundException(orderNo);
		}
		return order;
	}
	
	public PaymentOrder updatePaymentOrder(Long orderId, OrderStatus oStatus) throws Exception{
		PaymentOrder order = paymentOrderRepository.findOne(orderId);
		if(order == null){
			throw new ResourceNotFoundException(orderId);
		}
		order.setStatus(oStatus.getValue());
		paymentOrderRepository.save(order);
		return order;
	}
	
	public PaymentOrder updatePaymentOrder(String orderNo, OrderStatus oStatus) throws Exception{
		PaymentOrder order = paymentOrderRepository.findByOrderNo(orderNo);
		if(order == null){
			throw new ResourceNotFoundException(orderNo);
		}
		order.setStatus(oStatus.getValue());
		paymentOrderRepository.save(order);
		return order;
	}

	private void addPaymentOrder(String orderNo, int totalFee, SignInfo signInfo) throws SQLException{
		PaymentOrder order = new PaymentOrder();
		order.setOrderNo(orderNo);
		order.setNonceStr(signInfo.getNonceStr());
		order.setPaySign(signInfo.getPaySign());
		order.setPrepayId(signInfo.getPrepay());
		order.setSignType(signInfo.getSignType());
		order.setTimeStamp(signInfo.getTimeStamp());
		order.setTotalFee(totalFee);//cents
		order.setStatus(OrderStatus.PaymentPending.getValue());
		
		order.setCreateTime(new Date());
		order.setCreateUser(SessionContextUtil.getUserName());
		order.setUpdateTime(new Date());
		order.setUpdateUser(SessionContextUtil.getUserName());
		
		paymentOrderRepository.save(order);
	}
	
	private void addLocalOrder(String orderNo, int totalFee, String orders, String orderInfo) throws SQLException, JsonProcessingException, IOException{
		LocalOrder order = new LocalOrder();
		order.setOrderNo(orderNo);
		order.setTotalFee(new BigDecimal(totalFee/100.00));
		ObjectMapper mapper = JsonUtil.getMapperInstance(false); 
		JsonNode node = mapper.readTree(orderInfo);
		order.setNeedArrange(node.get("needArrange").asBoolean());
		order.setNeedInvoice(node.get("needInvoice").asBoolean());
		order.setNeedTransport(node.get("needTransport").asBoolean());
		order.setNeedJianzhi(node.get("needJianzhi").asBoolean());
		order.setPoint(node.get("point").asInt());
		order.setOrderDetail(orders);
		order.setCreateTime(new Date());
		order.setCreateUser(SessionContextUtil.getUserName());
		order.setUpdateTime(new Date());
		order.setUpdateUser(SessionContextUtil.getUserName());
		localOrderRepository.save(order);
	}
}
