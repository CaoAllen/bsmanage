package com.spring.example.model;

import java.io.Serializable;

public class Page implements Serializable{
	public enum Sort{
		ASC,
		DESC
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String sortName;
	private String sortDirection;
	private int pageNo;
	private int pageSize;
	
	public Page(String sortName, String sortDirection, int pageNo, int pageSize) {
		super();
		this.sortName = sortName;
		this.sortDirection = sortDirection;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	
	public Page(String sortName, int pageNo, int pageSize) {
		super();
		this.sortName = sortName;
		this.sortDirection = Sort.ASC.toString();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public String getSortName() {
		return sortName;
	}
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	public String getSortDirection() {
		return sortDirection;
	}
	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getPageStart(){
		return pageNo * pageSize;
	}
	
	@Override
	public String toString() {
		return "Page [sortName=" + sortName + ", sortDirection=" + sortDirection + ", pageNo=" + pageNo + ", pageSize="
				+ pageSize + "]";
	}
	
}
