package com.spring.example.utils;

public enum OrderStatus {
	PaymentPending(1,"待付款"),
	PaymentComplete(2,"付款完成"),
	Verifing(3,"审核中"),
	Ongoing(4,"进行中"),
	Finished(5,"订单完成"),
	Cancelled(6,"订单取消"),
	Closed(7,"订单关闭"),
	Failed(8,"交易失败");
	
	private OrderStatus(int value, String description){
		this.value = value;
		this.description = description;
	}
	
	private int value;
	private String description;
	
	public int getValue() {
		return value;
	}
	public String getDescription() {
		return description;
	}
	
	public String getDescriptionByValue(int value){
		for (OrderStatus oStatus : values()) {
			if(value == oStatus.getValue()) return oStatus.getDescription();
		}
		return "";
	}
	
	public OrderStatus getStatus(int value){
		for (OrderStatus oStatus : values()) {
			if(value == oStatus.getValue()) return oStatus;
		}
		return null;
	}
}
