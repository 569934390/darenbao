package com.compses.dto;

import java.util.ArrayList;
import java.util.List;

public class DBMeta {
	private String dbName;
	private String type;
	private List<DBTable> tables = new ArrayList<DBTable>();
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<DBTable> getTables() {
		return tables;
	}
	public void setTables(List<DBTable> tables) {
		this.tables = tables;
	}
}
