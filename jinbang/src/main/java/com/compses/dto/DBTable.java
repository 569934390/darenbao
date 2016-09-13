package com.compses.dto;

import java.util.ArrayList;
import java.util.List;

public class DBTable {
	public String tableName;
	public List<String> pks  = new ArrayList<String>();
	public List<String> fks  = new ArrayList<String>(); 
	public List<DBColumn> columns = new ArrayList<DBColumn>();
	public String remarks;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<String> getPks() {
		return pks;
	}
	public void setPks(List<String> pks) {
		this.pks = pks;
	}
	public List<String> getFks() {
		return fks;
	}
	public void setFks(List<String> fks) {
		this.fks = fks;
	}
	public List<DBColumn> getColumns() {
		return columns;
	}
	public void setColumns(List<DBColumn> columns) {
		this.columns = columns;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
