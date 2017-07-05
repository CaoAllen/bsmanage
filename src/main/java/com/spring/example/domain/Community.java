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

/**
 * 
 * @author acao
 *
 */
@Entity
@Table(name = "community")
public class Community implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
	private Long communityId;

	@Column(name = "site_id",nullable = false)
	private Long siteId;

	@Column(name = "year")
	private int year;
	
	@Column(name = "property_fee")
	private int propertyFee;

	@Column(name = "housing_price")
	private int housingPrice;

	@Column(name = "households")
	private int households;

	@Column(name = "occupancy_rate",length = 10)
	private String occupancyRate;
	
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

	public Long getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getPropertyFee() {
		return propertyFee;
	}

	public void setPropertyFee(int propertyFee) {
		this.propertyFee = propertyFee;
	}

	public int getHousingPrice() {
		return housingPrice;
	}

	public void setHousingPrice(int housingPrice) {
		this.housingPrice = housingPrice;
	}

	public int getHouseholds() {
		return households;
	}

	public void setHouseholds(int households) {
		this.households = households;
	}

	public String getOccupancyRate() {
		return occupancyRate;
	}

	public void setOccupancyRate(String occupancyRate) {
		this.occupancyRate = occupancyRate;
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
