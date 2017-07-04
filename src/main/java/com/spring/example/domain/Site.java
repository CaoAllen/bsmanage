package com.spring.example.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
*
* @author Allen Cao
*
*/
@Entity
@Table(name = "site")
public class Site implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "site_id")
	private Long siteId;
	
    @Column(name = "name")
	private String name;

    @Column(name = "flowrate")
    private int flowrate;
    
    @Column(name = "site_type")
	private String siteType;
	
    @Column(name = "stall_size")
	private String stallSize;
	
    @Column(name = "stall_time_start")
    @Temporal(TemporalType.TIME)
	private Date stallTimeStart;
	
    @Column(name = "stall_time_end")
    @Temporal(TemporalType.TIME)
	private Date stallTimeEnd;

    @Column(name = "total_area")
	private int totalArea;
	
    @Column(name = "stall_position")
	private String stallPosition;

    @Column(name = "history")
    private String history;
	
    @Column(name = "property_req")
	private String propertyReq;
	
    @Column(name = "prohibite_goods")
	private String prohibiteGoods;
	
    @Column(name = "supporting_facilities")
	private String supportingFacilities;
	
    @Column(name = "user_participation")
	private String userParticipation;
	
    @Column(name = "male_vs_female")
	private String maleVsFemale;
	
    @Column(name = "age_structure")
	private String ageStructure;
	
    @Column(name = "consumption")
	private String consumption;
	
    @Column(name = "site_details")
    @Size(max = 1000)
	private String siteDetails;
    
    @Column(name = "status")
    @Size(max = 2)
    private String status;
    
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
    
    @OneToMany(targetEntity= Picture.class,mappedBy="site",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private Set<Picture> pictures = new HashSet<Picture>();

	public Set<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(Set<Picture> pictures) {
		this.pictures = pictures;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
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

	public String getStallSize() {
		return stallSize;
	}

	public void setStallSize(String stallSize) {
		this.stallSize = stallSize;
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

	public String getSupportingFacilities() {
		return supportingFacilities;
	}

	public void setSupportingFacilities(String supportingFacilities) {
		this.supportingFacilities = supportingFacilities;
	}

	public String getUserParticipation() {
		return userParticipation;
	}

	public void setUserParticipation(String userParticipation) {
		this.userParticipation = userParticipation;
	}

	public String getMaleVsFemale() {
		return maleVsFemale;
	}

	public void setMaleVsFemale(String maleVsFemale) {
		this.maleVsFemale = maleVsFemale;
	}

	public String getAgeStructure() {
		return ageStructure;
	}

	public void setAgeStructure(String ageStructure) {
		this.ageStructure = ageStructure;
	}

	public String getConsumption() {
		return consumption;
	}

	public void setConsumption(String consumption) {
		this.consumption = consumption;
	}

	public String getSiteDetails() {
		return siteDetails;
	}

	public void setSiteDetails(String siteDetails) {
		this.siteDetails = siteDetails;
	}

	public int getFlowrate() {
		return flowrate;
	}

	public void setFlowrate(int flowrate) {
		this.flowrate = flowrate;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Site [siteId=" + siteId + ", name=" + name + ", flowrate=" + flowrate + ", siteType=" + siteType
				+ ", stallSize=" + stallSize + ", stallTimeStart=" + stallTimeStart + ", stallTimeEnd=" + stallTimeEnd
				+ ", totalArea=" + totalArea + ", stallPosition=" + stallPosition + ", history=" + history
				+ ", propertyReq=" + propertyReq + ", prohibiteGoods=" + prohibiteGoods + ", supportingFacilities="
				+ supportingFacilities + ", userParticipation=" + userParticipation + ", maleVsFemale=" + maleVsFemale
				+ ", ageStructure=" + ageStructure + ", consumption=" + consumption + ", siteDetails=" + siteDetails
				+ ", status=" + status + ", createTime=" + createTime + ", createUser=" + createUser + ", updateTime="
				+ updateTime + ", updateUser=" + updateUser + ", pictures=" + pictures + "]";
	}
	
}
