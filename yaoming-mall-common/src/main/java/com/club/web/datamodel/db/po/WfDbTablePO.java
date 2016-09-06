package com.club.web.datamodel.db.po;


import com.club.framework.dto.AbstractDto;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

public class WfDbTablePO extends AbstractDto {
	private String  dbName;
	private String  tableName;
	private String  remarks;
	private String  source;
	private Date modifyTime;
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbName() {
        return StringUtils.isBlank(dbName) ? dbName : dbName.trim();
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
    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return StringUtils.isBlank(source) ? source : source.trim();
    }
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }
}