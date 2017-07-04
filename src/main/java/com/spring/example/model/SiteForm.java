package com.spring.example.model;

import java.io.Serializable;
import java.util.Date;

import com.spring.example.domain.Address;

public class SiteForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String siteType;
	private int flowrate;
	private int totalArea;
	private String stallPosition;
	private Date stallTimeStart;
	private Date stallTimeEnd;
	private String stallSize;
	private String propertyReq;
	private String prohibiteGoods;
	private String history;
	private String supportingFacilities;
	private String siteDetails;
	private String ageStructure;
	private String maleVsFemale;
	private String consumption;
	private String userParticipation;
	private Address address;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	public int getFlowrate() {
		return flowrate;
	}

	public void setFlowrate(int flowrate) {
		this.flowrate = flowrate;
	}

	public int getTotalArea() {
		return totalArea;
	}

	public void setTotalArea(int totalArea) {
		this.totalArea = totalArea;
	}

	public String getStallPosition() {
		return stallPosition;
	}

	public void setStallPosition(String stallPosition) {
		this.stallPosition = stallPosition;
	}


	public Date getStallTimeStart() {
		return stallTimeStart;
	}

	public void setStallTimeStart(Date stallTimeStart) {
		this.stallTimeStart = stallTimeStart;
	}

	public Date getStallTimeEnd() {
		return stallTimeEnd;
	}

	public void setStallTimeEnd(Date stallTimeEnd) {
		this.stallTimeEnd = stallTimeEnd;
	}

	public String getStallSize() {
		return stallSize;
	}

	public void setStallSize(String stallSize) {
		this.stallSize = stallSize;
	}

	public String getPropertyReq() {
		return propertyReq;
	}

	public void setPropertyReq(String propertyReq) {
		this.propertyReq = propertyReq;
	}

	public String getProhibiteGoods() {
		return prohibiteGoods;
	}

	public void setProhibiteGoods(String prohibiteGoods) {
		this.prohibiteGoods = prohibiteGoods;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public String getSupportingFacilities() {
		return supportingFacilities;
	}

	public void setSupportingFacilities(String supportingFacilities) {
		this.supportingFacilities = supportingFacilities;
	}

	public String getSiteDetails() {
		return siteDetails;
	}

	public void setSiteDetails(String siteDetails) {
		this.siteDetails = siteDetails;
	}

	public String getAgeStructure() {
		return ageStructure;
	}

	public void setAgeStructure(String ageStructure) {
		this.ageStructure = ageStructure;
	}

	public String getMaleVsFemale() {
		return maleVsFemale;
	}

	public void setMaleVsFemale(String maleVsFemale) {
		this.maleVsFemale = maleVsFemale;
	}

	public String getConsumption() {
		return consumption;
	}

	public void setConsumption(String consumption) {
		this.consumption = consumption;
	}

	public String getUserParticipation() {
		return userParticipation;
	}

	public void setUserParticipation(String userParticipation) {
		this.userParticipation = userParticipation;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "SiteForm [name=" + name + ", flowrate=" + flowrate + ", totalArea=" + totalArea + ", stallPosition="
				+ stallPosition + ", stallTimeStart=" + stallTimeStart + ", stallTimeEnd=" + stallTimeEnd
				+ ", stallSize=" + stallSize + ", propertyReq=" + propertyReq + ", prohibiteGoods=" + prohibiteGoods
				+ ", history=" + history + ", supportingFacilities=" + supportingFacilities + ", siteDetails="
				+ siteDetails + ", ageStructure=" + ageStructure + ", maleVsFemale=" + maleVsFemale + ", consumption="
				+ consumption + ", userParticipation=" + userParticipation + ", address=" + address + "]";
	}
	
}
