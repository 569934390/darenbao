package com.club.web.event.vo;

import java.util.Date;

import com.club.web.stock.vo.ImageGroupVo;
import com.club.web.stock.vo.ImageVo;
/**
* @Title: EventActivityDetailsVo.java 
* @Package com.club.web.event.vo 
* @Description: TODO(活动查看详情) 
* @author 柳伟军   
* @date 2016年4月22日 下午3:02:04 
* @version V1.0
 */
public class EventActivityDetailsVo {
    private String id;
    
    private String subbranchId;
    
    private String subbranchName;

    private String activityTypeId;
    
    private String activityTypeName;
    
    private String type;
    
    private String typeName;

    private String title;

    private String sponsorName;

    private String sponsorTel;

    private Date beginTime;

    private Date endTime;

    private Date regStartTime;
    
    private Date regEndTime;
    
    private Long numberLimit;

    private Long activityStatus;
    
    private String activityStatusName;
    
    private Long status;
    
    private String statusName;

	private String activityPic;

    private Long pointNum;

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
    
    private Long participation;//报名人数

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivityTypeId() {
		return activityTypeId;
	}

	public void setActivityTypeId(String activityTypeId) {
		this.activityTypeId = activityTypeId;
	}

	public String getActivityTypeName() {
		return activityTypeName;
	}

	public void setActivityTypeName(String activityTypeName) {
		this.activityTypeName = activityTypeName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	public String getSponsorTel() {
		return sponsorTel;
	}

	public void setSponsorTel(String sponsorTel) {
		this.sponsorTel = sponsorTel;
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
		this.activityPic = activityPic;
	}

	public Long getPointNum() {
		return pointNum;
	}

	public void setPointNum(Long pointNum) {
		this.pointNum = pointNum;
	}

	public String getActivityAddress() {
		return activityAddress;
	}

	public void setActivityAddress(String activityAddress) {
		this.activityAddress = activityAddress;
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
		this.createAddress = createAddress;
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
		this.content = content;
	}

	public String getFailure() {
		return failure;
	}

	public void setFailure(String failure) {
		this.failure = failure;
	}

	public Long getParticipation() {
		return participation;
	}

	public void setParticipation(Long participation) {
		this.participation = participation;
	}

	public Long getNumberLimit() {
		return numberLimit;
	}

	public void setNumberLimit(Long numberLimit) {
		this.numberLimit = numberLimit;
	}

	public String getActivityStatusName() {
		return activityStatusName;
	}

	public void setActivityStatusName(String activityStatusName) {
		this.activityStatusName = activityStatusName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getSubbranchId() {
		return subbranchId;
	}

	public void setSubbranchId(String subbranchId) {
		this.subbranchId = subbranchId;
	}

	public String getSubbranchName() {
		return subbranchName;
	}

	public void setSubbranchName(String subbranchName) {
		this.subbranchName = subbranchName;
	}
}