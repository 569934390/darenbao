package com.compses.model;

import com.compses.dto.*;
import com.compses.dto.UserInfo;

import java.util.Date;
import java.util.List;

/**
 服务表 Date:2016-07-23 17:47:22
 **/
public class ServiceInfo{

    /**id**/
    private String serviceId;
    /**类别id**/
    private String serviceCategoryId;
    /**类别名称**/
    private String serviceCategoryName;
    /**名称**/
    private String serviceName;
    /**描述**/
    private String serviceConent;
    /**图片**/
    private String servicePhoto;
    /**价格**/
    private String servicePrice;
    /**购买单位**/
    private String serviceUnit;
    /**省id**/
    private String serviceProviceId;
    /**省名称**/
    private String serviceProviceName;
    /**市id**/
    private String serviceCityId;
    /**市名称**/
    private String serviceCityName;
    /**服务方式**/        //0线下1线上
    private String serviceType;
    /**是否开启展示联系方式**/        //0否1是
    private String isMobile;
    /**是否谈价**/                  //0否1是
    private String isPrice;
    /**发布人id**/
    private String publisherId;
    /**发布人姓名**/
    private String publisherName;
    /**发布人联系方式**/
    private String publisherMobile;
    /**发布时间**/
    private Date publishDate;
    /**发布经度**/
    private double lng;
    /**发布纬度**/
    private double lat;
    /**有效时间**/
    private Integer validityPeriod;
    /**上架状态**/                    //0下架1上架
    private String shelfStatus;
    /**备注**/
    private String remark;
    /**被收藏次数**/
    private Integer favouriteNum;
    /**购买次数**/
    private Integer buyNum;

    private int star; //评星

    private int score;  //评分

    private List<String> photoList;

    private int isFavourite;

    private com.compses.dto.UserInfo userInfo;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceCategoryId() {
        return serviceCategoryId;
    }

    public void setServiceCategoryId(String serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
    }

    public String getServiceCategoryName(){
        return serviceCategoryName;
    }
    public void setServiceCategoryName(String serviceCategoryName) {
        this.serviceCategoryName = serviceCategoryName;
    }
    public String getServiceName(){
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public String getServiceConent(){
        return serviceConent;
    }
    public void setServiceConent(String serviceConent) {
        this.serviceConent = serviceConent;
    }
    public String getServicePhoto(){
        return servicePhoto;
    }
    public void setServicePhoto(String servicePhoto) {
        this.servicePhoto = servicePhoto;
    }
    public String getServicePrice(){
        return servicePrice;
    }
    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }
    public String getServiceProviceName(){
        return serviceProviceName;
    }
    public void setServiceProviceName(String serviceProviceName) {
        this.serviceProviceName = serviceProviceName;
    }
    public String getServiceCityName(){
        return serviceCityName;
    }
    public void setServiceCityName(String serviceCityName) {
        this.serviceCityName = serviceCityName;
    }
    public String getServiceType(){
        return serviceType;
    }
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
    public String getIsMobile(){
        return isMobile;
    }
    public void setIsMobile(String isMobile) {
        this.isMobile = isMobile;
    }
    public String getIsPrice(){
        return isPrice;
    }
    public void setIsPrice(String isPrice) {
        this.isPrice = isPrice;
    }
    public String getPublisherName(){
        return publisherName;
    }
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
    public String getPublisherMobile(){
        return publisherMobile;
    }
    public void setPublisherMobile(String publisherMobile) {
        this.publisherMobile = publisherMobile;
    }
    public Date getPublishDate(){
        return publishDate;
    }
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
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
    public Integer getValidityPeriod(){
        return validityPeriod;
    }
    public void setValidityPeriod(Integer validityPeriod) {
        this.validityPeriod = validityPeriod;
    }
    public String getShelfStatus(){
        return shelfStatus;
    }
    public void setShelfStatus(String shelfStatus) {
        this.shelfStatus = shelfStatus;
    }
    public String getRemark(){
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public List<String> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<String> photoList) {
        this.photoList = photoList;
    }

    public Integer getFavouriteNum() {
        return favouriteNum;
    }

    public void setFavouriteNum(Integer favouriteNum) {
        this.favouriteNum = favouriteNum;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public int getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(int isFavourite) {
        this.isFavourite = isFavourite;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getServiceUnit() {
        return serviceUnit;
    }

    public void setServiceUnit(String serviceUnit) {
        this.serviceUnit = serviceUnit;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getServiceProviceId() {
        return serviceProviceId;
    }

    public void setServiceProviceId(String serviceProviceId) {
        this.serviceProviceId = serviceProviceId;
    }

    public String getServiceCityId() {
        return serviceCityId;
    }

    public void setServiceCityId(String serviceCityId) {
        this.serviceCityId = serviceCityId;
    }
}
