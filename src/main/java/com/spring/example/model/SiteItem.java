package com.spring.example.model;

import java.io.Serializable;
import java.math.BigInteger;

public class SiteItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private BigInteger siteId;
	private String name;
	private int flowrate;
	private String siteType;
	private String status;
	private String addressDetail;
	public BigInteger getSiteId() {
		return siteId;
	}
	public void setSiteId(BigInteger siteId) {
		this.siteId = siteId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getFlowrate() {
		return flowrate;
	}
	public void setFlowrate(int flowrate) {
		this.flowrate = flowrate;
	}
	public String getSiteType() {
		return siteType;
	}
	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAddressDetail() {
		return addressDetail;
	}
	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}
	
}
