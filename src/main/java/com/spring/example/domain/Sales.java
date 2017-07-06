package com.spring.example.domain;

import java.io.Serializable;
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
@Table(name = "sales")
public class Sales implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_id")
	private Long salesId;
	
    @Column(name = "site_id")
	private Long siteId;

    @Column(name = "sales_volumn")
    private Long salesVolumn;

    @Column(name = "score")
    private int score;
    
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

	public Long getSalesId() {
		return salesId;
	}

	public void setSalesId(Long salesId) {
		this.salesId = salesId;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Long getSalesVolumn() {
		return salesVolumn;
	}

	public void setSalesVolumn(Long salesVolumn) {
		this.salesVolumn = salesVolumn;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
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

	@Override
	public String toString() {
		return "Sales [salesId=" + salesId + ", siteId=" + siteId + ", salesVolumn=" + salesVolumn + ", score=" + score
				+ ", createTime=" + createTime + ", createUser=" + createUser + ", updateTime=" + updateTime
				+ ", updateUser=" + updateUser + "]";
	}
    
}
