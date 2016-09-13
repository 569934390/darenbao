package com.compses.model;

import java.util.Date;

/**
 需求表 Date:2016-07-24 14:14:55
 **/
public class Requirement{

    /**需求id**/
    private String requermentId;
    /**城市id**/
    private String cityId;
    /**需求类型**/
    private String reqCategoryId;
    /**需求名称**/
    private String reqName;
    /**发布者id**/
    private String publisherId;
    /**发布者名称**/
    private String publisherName;
    /**发布时间**/
    private Date publishDate;
    /**发布内容类型**/
    private String publishCategory;
    /**发布内容**/
    private String content;
    /**发布媒体文件**/
    private String media;
    /**发布人经度**/
    private double lng;
    /**发布人纬度**/
    private double lat;
    /**备注**/
    private String remark;

    private String reqCategoryName;

    public String getRequermentId() {
        return requermentId;
    }

    public void setRequermentId(String requermentId) {
        this.requermentId = requermentId;
    }

    public String getReqName(){
        return reqName;
    }
    public void setReqName(String reqName) {
        this.reqName = reqName;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public void setPublishCategory(String publishCategory) {
        this.publishCategory = publishCategory;
    }

    public String getPublisherName(){
        return publisherName;
    }
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
    public Date getPublishDate(){
        return publishDate;
    }
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getMedia(){
        return media;
    }
    public void setMedia(String media) {
        this.media = media;
    }
    public double getLng(){
        return lng;
    }
    public void setLng(double lng) {
        this.lng = lng;
    }
    public double getLat(){
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public String getRemark(){
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPublishCategory() {
        return publishCategory;
    }

    public String getReqCategoryId() {
        return reqCategoryId;
    }

    public void setReqCategoryId(String reqCategoryId) {
        this.reqCategoryId = reqCategoryId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getReqCategoryName() {
        return reqCategoryName;
    }

    public void setReqCategoryName(String reqCategoryName) {
        this.reqCategoryName = reqCategoryName;
    }
}
