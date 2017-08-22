package com.spring.example.utils;

public enum OrderStatus {
	PaymentPending(1,"������"),
	PaymentComplete(2,"�������"),
	Verifing(3,"�����"),
	Ongoing(4,"������"),
	Finished(5,"�������"),
	Cancelled(6,"����ȡ��"),
	Closed(7,"�����ر�"),
	Failed(8,"����ʧ��");
	
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
