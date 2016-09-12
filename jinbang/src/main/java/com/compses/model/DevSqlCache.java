package com.compses.model;

import java.util.Date;

public class DevSqlCache{

	private Integer id;
	private String sqlKey;
	private String context;
	private String type;
	private String state;
	private Date modifyTime;
	private Date createDate;

	public Integer getId(){
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSqlKey(){
		return sqlKey;
	}
	public void setSqlKey(String sqlKey) {
		this.sqlKey = sqlKey;
	}
	public String getContext(){
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getType(){
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getState(){
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getModifyTime(){
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Date getCreateDate(){
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
