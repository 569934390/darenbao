package com.compses.model;

import java.util.Date;

/**
 评论信息表 Date:2016-08-14 01:13:04
 **/
public class CommentInfo{

    /**id**/
    private String commentId;
    /**评论者id**/
    private String reviewerId;
    /**服务id**/
    private String serviceId;
    /**星级**/
    private Integer starLevel;
    /**评论时间**/
    private Date reviewDate;
    /**内容**/
    private String content;
    /**图片**/
    private String photos;
    /**备注**/
    private String remark;

    private Date createTime;

    private String orderId;

    /*---------------------------------------*/

    /**真名**/
    private String realName;
    /**  性别 -1:女， 0：未知  1：男  默认：0**/
    private Integer gender;
    /**头像**/
    private String portrait;

    public String getCommentId(){
        return commentId;
    }
    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
    public String getReviewerId(){
        return reviewerId;
    }
    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId;
    }
    public String getServiceId(){
        return serviceId;
    }
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(Integer starLevel) {
        this.starLevel = starLevel;
    }

    public Date getReviewDate(){
        return reviewDate;
    }
    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getPhotos(){
        return photos;
    }
    public void setPhotos(String photos) {
        this.photos = photos;
    }
    public String getRemark(){
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
