package com.club.core.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.club.framework.util.FrameWorkConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * <Description>分页类：分页参数及查询结果的封装 <br>
 * 
 * @author pan.xiaobo<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2014-9-1 <br>
 * @since V1.0<br>
 * @see com.club.appmgr.core.common <br>
 */
public class Page<T> extends RowBounds {

    // 分页参数中，当前页面,默认为1
    @JsonIgnore
    private int page = 1;

    // 分页参数中，起始记录下标，从0开始
    @JsonIgnore
    private int start;

    // 分页参数中，前台分页数对应于PageSize,每页记录数
    @JsonIgnore
    private int limit = FrameWorkConstants.DEFAULT_PAGE_SIZE;

    @JsonIgnore
    private int offset;

    // 总页面
    private int totalPages;

    // 总记录数
    private int totalRecords;

    // 记录封装
    private List<T> resultList;
    @JsonIgnore
    private Map<String,Object> conditons = new HashMap<String, Object>();

    public Page() {

    }

    /**
     * modify by pan.xiaobo 2015-12-11，因目前前台传入的参数一般是start跟limit，故改为这两个
     * 
     * @param start
     * @param pageSize
     */
    public Page(int start, int pageSize) {
        setStart(start);
        setLimit(pageSize);
    }

    public void initOtherData() {
        if(totalRecords==0){
            setTotalPages(0);
        }else{
            setTotalPages((int) ((totalRecords - 1) / limit) + 1);
        }

    }

    /**
     * @return the totalPages
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * @param totalPages the totalPages to set
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * @return the totalRecords
     */
    public int getTotalRecords() {
        return totalRecords;
    }

    /**
     * @param totalRecords the totalRecords to set
     */
    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
        initOtherData();
    }

    /**
     * @return the resultList
     */
    public List<T> getResultList() {
        return resultList;
    }

    /**
     * @param resultList the resultList to set
     */
    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    /**
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * @return the start
     */
    public int getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * @param limit the limit to set
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Map<String, Object> getConditons() {
        return conditons;
    }

    public void setConditons(Map<String, Object> conditons) {
        this.conditons = conditons;
    }

}
