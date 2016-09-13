package com.compses.model;

import java.util.Date;

public class DopPrivilegeUser {
	private Integer id;
	private String name;
	private Integer department;
	private String realName;
	private String password;
	private String phone;
	private String email;
	private String address;
	private Date startTime;
	private Date endTime;
	private Date modifyTime;
	private Integer allowError;
	private Date modifyPasswordTime;
	private String state;
	private String direction;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDepartment() {
		return department;
	}
	public void setDepartment(Integer department) {
		this.department = department;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Integer getAllowError() {
		return allowError;
	}
	public void setAllowError(Integer allowError) {
		this.allowError = allowError;
	}
	public Date getModifyPasswordTime() {
		return modifyPasswordTime;
	}
	public void setModifyPasswordTime(Date modifyPasswordTime) {
		this.modifyPasswordTime = modifyPasswordTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
}
