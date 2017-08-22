package com.spring.example.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "payment_order")
public class PaymentOrder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
	private Long orderId;
	
    @Column(name = "order_no")
	private String orderNo;
    
    @Column(name = "timeStamp")
	private String timeStamp;
    
    @Column(name = "nonce_str")
	private String nonceStr;
    
    @Column(name = "prepay_id")
	private String prepayId;
    
    @Column(name = "sign_type")
	private String signType;
    
    @Column(name = "pay_sign")
	private String paySign;
    
    @Column(name = "fee")
	private int totalFee;
    
    @Column(name = "status")
	private int status;
    
    @Column(name = "create_time",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

    @Column(name = "create_user",nullable = false,length = 20)
	private String createUser;

    @Column(name = "update_time",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

    @Column(name = "update_user",nullable = false,length = 20)
	private String updateUser;
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getPrepayId() {
		return prepayId;
	}
	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getPaySign() {
		return paySign;
	}
	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}
	public int getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
}
