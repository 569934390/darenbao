package com.club.web.datamodel.db.po;


import com.club.framework.dto.AbstractDto;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Transient;
import java.util.List;

public class WfDbColumnsPO extends AbstractDto {
	private String  tableName;
	private String  columnName;
	private String  displayName;
	private String  dbType;
	private String  type;
	private String  isNull;
	private String  defaultValue;
	private Integer  length;

    public WfDbColumnsPO[] getColumnModels() {
        return columnModels;
    }

    public void setColumnModels(WfDbColumnsPO[] columnModels) {
        this.columnModels = columnModels;
    }

    private WfDbColumnsPO[] columnModels ;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    @Transient
    private String  dbName ;

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return StringUtils.isBlank(tableName) ? tableName : tableName.trim();
    }
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return StringUtils.isBlank(columnName) ? columnName : columnName.trim();
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return StringUtils.isBlank(displayName) ? displayName : displayName.trim();
    }
    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDbType() {
        return StringUtils.isBlank(dbType) ? dbType : dbType.trim();
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return StringUtils.isBlank(type) ? type : type.trim();
    }
    public void setIsNull(String isNull) {
        this.isNull = isNull;
    }

    public String getIsNull() {
        return StringUtils.isBlank(isNull) ? isNull : isNull.trim();
    }
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return StringUtils.isBlank(defaultValue) ? defaultValue : defaultValue.trim();
    }
    public void setLength(Integer length) {
        this.length = length;
    }
    public Integer getLength() {
        return length;
    }
}