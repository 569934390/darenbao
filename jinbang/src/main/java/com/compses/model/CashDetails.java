package com.compses.model;

import java.util.Date;

/**
 提现详情 Date:2016-08-29 16:25:58
 **/
public class CashDetails{

    /**主键id**/
    private String cashId;
    /**标题**/
    private String title;
    /**修改时间**/
    private Date modifyTime;
    /**状态**/
    private String status;
    /**取现状态 **/
    private String detailsStatus;

    public String getCashId(){
        return cashId;
    }
    public void setCashId(String cashId) {
        this.cashId = cashId;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Date getModifyTime(){
        return modifyTime;
    }
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getDetailsStatus(){
        return detailsStatus;
    }
    public void setDetailsStatus(String detailsStatus) {
        this.detailsStatus = detailsStatus;
    }
}
