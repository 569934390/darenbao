package com.compses.model;


import java.util.Date;

/**
 订单明细记录 Date:2016-08-13 20:56:34
 **/
public class OrderDetail{

    /**主键id**/
    private String detailId;
    /**标题**/
    private String title;
    /**子标题**/
    private String subTitle;
    /**状态**/
    private String status;
    /**订单状态**/
    private String orderStatus;
    /**订单类别（1  买入，0 卖出）**/
    private String orderCategory;

    private Date modifyTime;

    public String getDetailId(){
        return detailId;
    }
    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSubTitle(){
        return subTitle;
    }
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getOrderStatus(){
        return orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    public String getOrderCategory(){
        return orderCategory;
    }
    public void setOrderCategory(String orderCategory) {
        this.orderCategory = orderCategory;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
