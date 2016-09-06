package com.club.web.datasource.db.po;

import java.util.*;
import org.apache.commons.lang.StringUtils;
import com.club.framework.dto.AbstractDto;

public class WfDataSetPO extends AbstractDto{
	private Integer  dataSetId;
	private String  dataSetType;
	private String  dataUrl;
	private String  name;
	private String  displayName;
	private String  tableGroup;
	private String  joinGroup;
	private String  onGroup;
	private String  whereParams;
	private String  columns;
	private String  displayColumns;
	private Date  createDate;
	private String  status;
	private Integer  modifyUser;
    private String   modifyUserName ;

    public String getModifyUserName() {
        return modifyUserName;
    }
    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName;
    }
    public void setDataSetId(Integer dataSetId) {
        this.dataSetId = dataSetId;
    }
    public Integer getDataSetId() {
        return dataSetId;
    }
    public void setDataSetType(String dataSetType) {
        this.dataSetType = dataSetType;
    }

    public String getDataSetType() {
        return StringUtils.isBlank(dataSetType) ? dataSetType : dataSetType.trim();
    }
    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getDataUrl() {
        return StringUtils.isBlank(dataUrl) ? dataUrl : dataUrl.trim();
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return StringUtils.isBlank(name) ? name : name.trim();
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return StringUtils.isBlank(displayName) ? displayName : displayName.trim();
    }
    public void setTableGroup(String tableGroup) {
        this.tableGroup = tableGroup;
    }

    public String getTableGroup() {
        return StringUtils.isBlank(tableGroup) ? tableGroup : tableGroup.trim();
    }
    public void setJoinGroup(String joinGroup) {
        this.joinGroup = joinGroup;
    }

    public String getJoinGroup() {
        return StringUtils.isBlank(joinGroup) ? joinGroup : joinGroup.trim();
    }
    public void setOnGroup(String onGroup) {
        this.onGroup = onGroup;
    }

    public String getOnGroup() {
        return StringUtils.isBlank(onGroup) ? onGroup : onGroup.trim();
    }
    public void setWhereParams(String whereParams) {
        this.whereParams = whereParams;
    }

    public String getWhereParams() {
        return StringUtils.isBlank(whereParams) ? whereParams : whereParams.trim();
    }
    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getColumns() {
        return StringUtils.isBlank(columns) ? columns : columns.trim();
    }
    public void setDisplayColumns(String displayColumns) {
        this.displayColumns = displayColumns;
    }

    public String getDisplayColumns() {
        return StringUtils.isBlank(displayColumns) ? displayColumns : displayColumns.trim();
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return createDate;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return StringUtils.isBlank(status) ? status : status.trim();
    }
    public void setModifyUser(Integer modifyUser) {
        this.modifyUser = modifyUser;
    }
    public Integer getModifyUser() {
        return modifyUser;
    }
}