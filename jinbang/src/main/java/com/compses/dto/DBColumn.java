package com.compses.dto;

public class DBColumn {
	public String name;
	public String type;
	public Integer size;
	public Integer nullAble;
	public String defaultValue;
	public String remarks;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getNullAble() {
		return nullAble;
	}
	public void setNullAble(Integer nullAble) {
		this.nullAble = nullAble;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
}