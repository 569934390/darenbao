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
    private boolean success=true;
    private String message;

    public BaseVo(){

    }
    public BaseVo(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
