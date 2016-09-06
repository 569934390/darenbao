package com.club.web.common.vo;

import java.util.ArrayList;
import java.util.List;

import com.club.web.common.db.po.WfDbMetaPO;
import org.apache.commons.lang.StringUtils;

public class DBMeta {
	private String  dbName;
	private String  type;

	private List<DBTable> tables = new ArrayList<DBTable>();
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getDbName() {
		return StringUtils.isBlank(dbName) ? dbName : dbName.trim();
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return StringUtils.isBlank(type) ? type : type.trim();
	}
	public List<DBTable> getTables() {
		return tables;
	}
	public void setTables(List<DBTable> tables) {
		this.tables = tables;
	}
	
	public void convert(WfDbMetaPO po){
		po.setDbName(this.getDbName());
		po.setType(this.getType());
	}

	public static DBMeta parse(WfDbMetaPO metaPO){
		DBMeta meta = new DBMeta();
		meta.setType(metaPO.getType());
		meta.setDbName(metaPO.getDbName());
		return meta;
	}
}
