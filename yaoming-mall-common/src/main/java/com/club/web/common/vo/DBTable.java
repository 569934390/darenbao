package com.club.web.common.vo;

import java.util.*;

import com.club.web.common.db.po.WfDbTablePO;
import org.apache.commons.lang.StringUtils;

public class DBTable {
	private String  dbName;
	private String  tableName;
	private String  remarks;
	private String  source;
	private Date modifyTime;

	public List<String> pks  = new ArrayList<String>();
	public List<String> fks  = new ArrayList<String>(); 
	public List<DBColumn> columns = new ArrayList<DBColumn>();

	public Map<String, DBColumn> getColumnMap() {
		return columnMap;
	}

	public void setColumnMap(Map<String, DBColumn> columnMap) {
		this.columnMap = columnMap;
	}

	public Map<String,DBColumn> columnMap = new HashMap<String, DBColumn>();

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName() {
		return StringUtils.isBlank(tableName) ? tableName : tableName.trim();
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRemarks() {
		return StringUtils.isBlank(remarks) ? remarks : remarks.trim();
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public String getSource() {
		return source;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public void setSource(String source) {
		this.source = source;
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
	
	public void convert(WfDbTablePO po){
		po.setTableName(tableName);
		po.setRemarks(remarks);
		po.setModifyTime(modifyTime);
		po.setSource(source);
		po.setDbName(dbName);
	}

	public static DBTable parse(WfDbTablePO po){
		DBTable table = new DBTable();
		table.setTableName(po.getTableName());
		table.setDbName(po.getDbName());
		table.setSource(po.getSource());
		table.setRemarks(po.getRemarks());
		table.setModifyTime(po.getModifyTime());
		return table;
	}
}
