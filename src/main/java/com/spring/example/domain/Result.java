package com.spring.example.domain;

import java.io.Serializable;

public class Result implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object object;
	private boolean status;
	
	public Result(boolean status){
		this.status = status;
	}

	public Result(Object object, boolean status) {
		super();
		this.object = object;
		this.status = status;
	}


	public Object getObject() {
		return object;
	}


	public void setObject(Object object) {
		this.object = object;
	}


	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
