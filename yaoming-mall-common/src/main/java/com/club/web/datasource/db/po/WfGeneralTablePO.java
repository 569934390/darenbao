package com.club.web.datasource.db.po;

import java.util.*;
import org.apache.commons.lang.StringUtils;
import com.club.framework.dto.AbstractDto;

public class WfGeneralTablePO extends AbstractDto{
	private Integer  tableId;
	private String  tableName;
	private String  isPage;
	private String  isCheckbox;
	private String  extJson;
	private Integer  dataSetId;
    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }
    public Integer getTableId() {
        return tableId;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return StringUtils.isBlank(tableName) ? tableName : tableName.trim();
    }
    public void setIsPage(String isPage) {
        this.isPage = isPage;
    }

    public String getIsPage() {
        return StringUtils.isBlank(isPage) ? isPage : isPage.trim();
    }
    public void setIsCheckbox(String isCheckbox) {
        this.isCheckbox = isCheckbox;
    }

    public String getIsCheckbox() {
        return StringUtils.isBlank(isCheckbox) ? isCheckbox : isCheckbox.trim();
    }
    public void setExtJson(String extJson) {
        this.extJson = extJson;
    }

    public String getExtJson() {
        return StringUtils.isBlank(extJson) ? extJson : extJson.trim();
    }
    public void setDataSetId(Integer dataSetId) {
        this.dataSetId = dataSetId;
    }
    public Integer getDataSetId() {
        return dataSetId;
    }
}