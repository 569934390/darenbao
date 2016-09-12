package com.compses.dto;


import com.compses.model.ServiceInfo;

import java.util.Date;
import java.util.List;

public class UserInfo {

	/**id**/
	private String userId;
	/**员工录名**/
	private String loginName;
	/**密码**/
	private String password;
	/**真名**/
	private String realName;
	/**  性别 -1:女， 0：未知  1：男  默认：0**/
	private Integer gender;
	/**头像**/
	private String portrait;

	private String userStatus;  //用户状态：是否接单中  0:未接单  1:已接单

	private double lng;   //经度

	private double lat;   //维度

	private double distance;  //距离

	private ServiceInfo serviceInfo;   //服务

	private Date lastLoginTime;		//最后登录时间

	private int ServiceNum;		//项目数

	private int pickOrderNum;	//接单次数

	private List<String> photoList;		//用户照片集合

	/**认证时间**/
	private String authenticationTime;
	/**我的技能认证照片**/
	private String skillImages;
	/**我的认证视频**/
	private String skillVideo;

	private String isBusy;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getAuthenticationTime() {
		return authenticationTime;
	}

	public void setAuthenticationTime(String authenticationTime) {
		this.authenticationTime = authenticationTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public ServiceInfo getServiceInfo() {
		return serviceInfo;
	}
	

	public void setServiceInfo(ServiceInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	public String getIsBusy() {
		return isBusy;
	}

	public void setIsBusy(String isBusy) {
		this.isBusy = isBusy;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public int getServiceNum() {
		return ServiceNum;
	}

	public void setServiceNum(int serviceNum) {
		ServiceNum = serviceNum;
	}

	public int getPickOrderNum() {
		return pickOrderNum;
	}

	public void setPickOrderNum(int pickOrderNum) {
		this.pickOrderNum = pickOrderNum;
	}

	public String getSkillImages() {
		return skillImages;
	}

	public void setSkillImages(String skillImages) {
		this.skillImages = skillImages;
	}

	public String getSkillVideo() {
		return skillVideo;
	}

	public void setSkillVideo(String skillVideo) {
		this.skillVideo = skillVideo;
	}

	public List<String> getPhotoList() {
		return photoList;
	}

	public void setPhotoList(List<String> photoList) {
		this.photoList = photoList;
	}
}
