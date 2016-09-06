package com.club.web.event.dao.base.po;

import java.util.Date;

public class EventActivity {
	private Long id;
    
    private Long subbranchId;

	private Long activityTypeId;

	private Long type;

	private String title;

	private String sponsorName;

	private String sponsorTel;

	private Date beginTime;

	private Date endTime;

	private Long numberLimit;

	private Date regStartTime;

	private Date regEndTime;

	private Long activityStatus;

	private Long status;

	private String activityPic;

	private String activityAddress;

	private Double activityLongitude;

	private Double activityLatitude;

	private String createAddress;

	private Double createLongitude;

	private Double createLatitude;

	private Date createTime;

	private Long createBy;

	private Date updateTime;

	private Long updateBy;

	private String content;

	private String failure;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getActivityTypeId() {
		return activityTypeId;
	}

	public void setActivityTypeId(Long activityTypeId) {
		this.activityTypeId = activityTypeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName == null ? null : sponsorName.trim();
	}

	public String getSponsorTel() {
		return sponsorTel;
	}

	public void setSponsorTel(String sponsorTel) {
		this.sponsorTel = sponsorTel == null ? null : sponsorTel.trim();
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getNumberLimit() {
		return numberLimit;
	}

	public void setNumberLimit(Long numberLimit) {
		this.numberLimit = numberLimit;
	}

	public Date getRegStartTime() {
		return regStartTime;
	}

	public void setRegStartTime(Date regStartTime) {
		this.regStartTime = regStartTime;
	}

	public Date getRegEndTime() {
		return regEndTime;
	}

	public void setRegEndTime(Date regEndTime) {
		this.regEndTime = regEndTime;
	}

	public Long getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(Long activityStatus) {
		this.activityStatus = activityStatus;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getActivityPic() {
		return activityPic;
	}

	public void setActivityPic(String activityPic) {
		this.activityPic = activityPic == null ? null : activityPic.trim();
	}

	public String getActivityAddress() {
		return activityAddress;
	}

	public void setActivityAddress(String activityAddress) {
		this.activityAddress = activityAddress == null ? null : activityAddress.trim();
	}

	public Double getActivityLongitude() {
		return activityLongitude;
	}

	public void setActivityLongitude(Double activityLongitude) {
		this.activityLongitude = activityLongitude;
	}

	public Double getActivityLatitude() {
		return activityLatitude;
	}

	public void setActivityLatitude(Double activityLatitude) {
		this.activityLatitude = activityLatitude;
	}

	public String getCreateAddress() {
		return createAddress;
	}

	public void setCreateAddress(String createAddress) {
		this.createAddress = createAddress == null ? null : createAddress.trim();
	}

	public Double getCreateLongitude() {
		return createLongitude;
	}

	public void setCreateLongitude(Double createLongitude) {
		this.createLongitude = createLongitude;
	}

	public Double getCreateLatitude() {
		return createLatitude;
	}

	public void setCreateLatitude(Double createLatitude) {
		this.createLatitude = createLatitude;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public String getFailure() {
		return failure;
	}

	public void setFailure(String failure) {
		this.failure = failure;
	}

	public Long getSubbranchId() {
		return subbranchId;
	}

	public void setSubbranchId(Long subbranchId) {
		this.subbranchId = subbranchId;
	}

}