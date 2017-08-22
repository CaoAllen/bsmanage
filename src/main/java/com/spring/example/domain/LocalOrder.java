package com.spring.example.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "local_order")
public class LocalOrder implements Serializable{

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
	
	@Column(name = "customer_id")
	private Long customerId;
	
	@Column(name = "total_fee")
	private BigDecimal totalFee;
	
	@Column(name = "contact_id")
	private Long contactId;
	
	@Column(name = "need_invoice")
	private boolean needInvoice;
	
	@Column(name = "need_arrange")
	private boolean needArrange;

	@Column(name = "need_transport")
	private boolean needTransport;	
	
	@Column(name = "need_jianzhi")
	private boolean needJianzhi;	
	
	@Column(name = "point")
	private int point;
	
	@Column(name = "order_detail")
	private String orderDetail;

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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	public boolean isNeedInvoice() {
		return needInvoice;
	}

	public void setNeedInvoice(boolean needInvoice) {
		this.needInvoice = needInvoice;
	}

	public boolean isNeedArrange() {
		return needArrange;
	}

	public void setNeedArrange(boolean needArrange) {
		this.needArrange = needArrange;
	}

	public boolean isNeedTransport() {
		return needTransport;
	}

	public void setNeedTransport(boolean needTransport) {
		this.needTransport = needTransport;
	}

	public boolean isNeedJianzhi() {
		return needJianzhi;
	}

	public void setNeedJianzhi(boolean needJianzhi) {
		this.needJianzhi = needJianzhi;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(String orderDetail) {
		this.orderDetail = orderDetail;
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
