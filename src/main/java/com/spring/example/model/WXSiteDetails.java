package com.spring.example.model;

import com.spring.example.domain.Sales;

public class WXSiteDetails extends SiteDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Sales sales;

	public Sales getSales() {
		return sales;
	}

	public void setSales(Sales sales) {
		this.sales = sales;
	}
}
