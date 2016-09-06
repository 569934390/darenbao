package com.club.web.common.vo;

import com.club.framework.util.DBUtils;
import com.club.web.common.db.po.WfDbColumnsPO;
import org.apache.commons.lang.StringUtils;

public class DBColumn {
	private String tableName;
	private String  columnName;
	private String  displayName;
	private String  dbType;
	private String type;
	private String  isNull;
	private String  defaultValue;
	private Integer  length;

	public String getTableName() {
		return tableName;
	}


	public String getType() {
		if(type==null) return "";
		return StringUtils.isBlank(type) ? type : type.trim();
	}

	public String getColumnName() {
		return columnName;
	}

	public String getDbType() {
		return dbType;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getIsNull() {
		return StringUtils.isBlank(isNull) ? isNull : isNull.trim();
	}

	public Integer getLength() {
		return length;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}

	public void setLength(Integer length) {
		if(length!=null){
			this.length = length;
		}
		else {
			if(!com.club.framework.util.StringUtils.isEmpty(this.getDbType())) {
				this.length = Integer.parseInt(DBUtils.defaultLength(this.getDbType()));
			}
		}
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setType(String type) {
		if(this.type==null) {
			this.type = type;
		}
		else if(this.type.indexOf(type)==-1){
			this.type+=","+type;
		}
	}

	public void convert(WfDbColumnsPO po){
		po.setDefaultValue(defaultValue);
		po.setTableName(tableName);
		po.setColumnName(columnName);
		po.setDisplayName(displayName);
		po.setDbType(dbType);
		po.setType(type);
		po.setLength(length);
		po.setIsNull(isNull);
	}

	public static DBColumn parse(WfDbColumnsPO po){
		DBColumn column = new DBColumn();
		column.setTableName(po.getTableName());
		column.setDefaultValue(po.getDefaultValue());
		column.setDbType(po.getDbType());
		column.setType(po.getType());
		column.setColumnName(po.getColumnName());
		column.setDisplayName(po.getDisplayName());
		column.setLength(po.getLength());
		column.setIsNull(po.getIsNull());
		return column;
	}
}