package com.compses.dto;

import java.util.Date;

public class PushModel {
	private String type;
	private String data;
	private Date pushTime = new Date();
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Date getPushTime() {
		return pushTime;
	}
	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}
	
	
}
