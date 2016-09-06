package com.club.web.datasource.db.po;

import java.util.*;
import org.apache.commons.lang.StringUtils;
import com.club.framework.dto.AbstractDto;

public class WfGeneralTableColumnsPO extends AbstractDto{
	private Integer  tableId;
	private Integer  columnId;
	private Integer  genColumnId;
	private Integer  dataSetId;
	private String  columnIndex;
	private String  columnName;
	private String  hidden;
	private String  width;
	private String  render;
	private Integer  columnOrder;
    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }
    public Integer getTableId() {
        return tableId;
    }
    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }
    public Integer getColumnId() {
        return columnId;
    }
    public void setGenColumnId(Integer genColumnId) {
        this.genColumnId = genColumnId;
    }
    public Integer getGenColumnId() {
        return genColumnId;
    }
    public void setDataSetId(Integer dataSetId) {
        this.dataSetId = dataSetId;
    }
    public Integer getDataSetId() {
        return dataSetId;
    }
    public void setColumnIndex(String columnIndex) {
        this.columnIndex = columnIndex;
    }

    public String getColumnIndex() {
        return StringUtils.isBlank(columnIndex) ? columnIndex : columnIndex.trim();
    }
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return StringUtils.isBlank(columnName) ? columnName : columnName.trim();
    }
    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    public String getHidden() {
        return StringUtils.isBlank(hidden) ? hidden : hidden.trim();
    }
    public void setWidth(String width) {
        this.width = width;
    }

    public String getWidth() {
        return StringUtils.isBlank(width) ? width : width.trim();
    }
    public void setRender(String render) {
        this.render = render;
    }

    public String getRender() {
        return StringUtils.isBlank(render) ? render : render.trim();
    }
    public void setColumnOrder(Integer columnOrder) {
        this.columnOrder = columnOrder;
    }
    public Integer getColumnOrder() {
        return columnOrder;
    }
}