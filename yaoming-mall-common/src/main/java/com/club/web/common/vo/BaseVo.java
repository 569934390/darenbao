package com.club.web.common.vo;

import com.club.core.common.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by lifei on 2016/9/4.
 */
public class BaseVo{
    @JsonIgnore
    private String selectColumns;
    @JsonIgnore
    private Integer start;
    @JsonIgnore
    private Integer limit;
    public String getSelectColumns() {
        return selectColumns;
    }

    public void setSelectColumns(String selectColumns) {
        this.selectColumns = selectColumns;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
