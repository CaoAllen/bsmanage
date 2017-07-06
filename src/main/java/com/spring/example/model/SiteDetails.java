package com.spring.example.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.StoredProcedureParameter;

import com.spring.example.domain.Address;
import com.spring.example.domain.Community;
import com.spring.example.domain.Picture;
import com.spring.example.domain.Price;
import com.spring.example.domain.Site;

import javax.persistence.ParameterMode;

public class SiteDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Site site;
	private Address address;
	private Community community;
	private List<Price> prices;
	private List<Picture> pictures;
	
	public Site getSite() {
		return site;
	}
	public void setSite(Site site) {
		this.site = site;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Community getCommunity() {
		return community;
	}
	public void setCommunity(Community community) {
		this.community = community;
	}
	public List<Price> getPrices() {
		return prices;
	}
	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}
	public List<Picture> getPictures() {
		return pictures;
	}
	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}
	
}
