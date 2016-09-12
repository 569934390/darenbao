package com.compses.model;

/**
 * Created by jocelynsuebb on 16/7/30.
 */

import java.util.Date;

/**
 订单表 Date:2016-07-30 23:28:28
 **/
public class OrderInfo{
    //下单时间
    //数量

    /**id**/
    private String orderId;
    /**名称--set服务名称**/
    private String orderName;
    /**需求id**/
    private String reqId;
    /**服务id**/
    private String serviceId;
    /**订单编号**/
    private String orderCode;
    /**价格**/
    private double orderPrice;
    /**市id**/
    private String orderCityId;
    /**市名称**/
    private String orderCityName;
    /**下单(需求发布)人id**/
    private String publisherId;
    /**下单(需求发布)人姓名**/
    private String publisherName;
    /**下单(需求发布)人联系方式**/
    private String publisherMobile;
    /**有效时间**/
    private Date validityPeriod;
    /**订单状态**/
    private String orderStatus;
    /**所属品牌运营官id**/
    private String nativeBandId;
    /**所属品牌运营官名称**/
    private String nativeBandName;
    /**所属区域运营官id**/
    private String nativeCityId;
    /**所属区域运营官名称**/
    private String nativeCityName;
    /**所属顶级运营官id**/
    private String nativeTopId;
    /**所属顶级运营官名称**/
    private String nativeTopName;
    /**接单(服务提供)人id**/
    private String pickerId;
    /**接单(服务提供)人姓名**/
    private String pickerName;
    /**接单(服务提供)人联系方式**/
    private String pickerMobile;
    /**支付订单号**/
    private String payCode;
    /**支付方式**/
    private String payChannel;
    /**备注(留言)**/
    private String remark;
    /**购买数量**/
    private Integer buyNum;
    /**订单类别   1:会员订单  2:预付2元订单  3:5元橙子卡订单**/
    private String orderType;
    /**下单时间**/
    private Date orderDate;
    /**支付时间**/
    private Date payDate;
    /**服务起始时间**/
    private Date serviceStartDate;
    /**服务结束时间**/
    private Date serviceEndDate;
    /**订单结束时间**/
    private Date orderEndDate;
    /**发布需求内容类型 0:文本 1:语音**/
    private String publishCategory;
    /**发布文本内容**/
    private String content;
    /**发布媒体文件**/
    private String media;
    //取消原因
    private String cancelReason;
    //回复取消订单的原因
    private String applyCancelReason;
    //类型(1同意 0不同意)
    private String cancelType;
    //取消时间、退款时间
    private Date cancelDate;

    public String getCancelType() {
        return cancelType;
    }

    public void setCancelType(String cancelType) {
        this.cancelType = cancelType;
    }
/*---------------------非数据库字段---------------------------*/

    private Integer mediaLength;

    private String portrait;

    private String servicePhoto;

    private String servicePortrait;
    /**价格**/
    private double servicePrice;
    /**购买单位**/
    private String serviceUnit;
    /**发布人经度**/
    private double lng;
    /**发布人纬度**/
    private double lat;

    public String getOrderId(){
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getOrderName(){
        return orderName;
    }
    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
    public String getReqId(){
        return reqId;
    }
    public void setReqId(String reqId) {
        this.reqId = reqId;
    }
    public String getServiceId(){
        return serviceId;
    }
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
    public String getOrderCode(){
        return orderCode;
    }
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderCityId() {
        return orderCityId;
    }

    public void setOrderCityId(String orderCityId) {
        this.orderCityId = orderCityId;
    }

    public String getOrderCityName(){
        return orderCityName;
    }
    public void setOrderCityName(String orderCityName) {
        this.orderCityName = orderCityName;
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
    public String getOrderStatus(){
        return orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    public String getNativeCityName(){
        return nativeCityName;
    }
    public void setNativeCityName(String nativeCityName) {
        this.nativeCityName = nativeCityName;
    }
    public String getPickerName(){
        return pickerName;
    }
    public void setPickerName(String pickerName) {
        this.pickerName = pickerName;
    }
    public String getPickerMobile(){
        return pickerMobile;
    }
    public void setPickerMobile(String pickerMobile) {
        this.pickerMobile = pickerMobile;
    }
    public String getPayCode(){
        return payCode;
    }
    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }
    public String getPayChannel(){
        return payChannel;
    }
    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }
    public String getRemark(){
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPublishCategory() {
        return publishCategory;
    }

    public void setPublishCategory(String publishCategory) {
        this.publishCategory = publishCategory;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public Integer getMediaLength() {
        return mediaLength;
    }

    public void setMediaLength(Integer mediaLength) {
        this.mediaLength = mediaLength;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getPickerId() {
        return pickerId;
    }

    public void setPickerId(String pickerId) {
        this.pickerId = pickerId;
    }

    public Date getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(Date validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getServicePhoto() {
        return servicePhoto;
    }

    public void setServicePhoto(String servicePhoto) {
        this.servicePhoto = servicePhoto;
    }

    public String getNativeBandName() {
        return nativeBandName;
    }

    public void setNativeBandName(String nativeBandName) {
        this.nativeBandName = nativeBandName;
    }

    public String getNativeTopName() {
        return nativeTopName;
    }

    public void setNativeTopName(String nativeTopName) {
        this.nativeTopName = nativeTopName;
    }

    public String getNativeBandId() {
        return nativeBandId;
    }

    public void setNativeBandId(String nativeBandId) {
        this.nativeBandId = nativeBandId;
    }

    public String getNativeCityId() {
        return nativeCityId;
    }

    public void setNativeCityId(String nativeCityId) {
        this.nativeCityId = nativeCityId;
    }

    public String getNativeTopId() {
        return nativeTopId;
    }

    public void setNativeTopId(String nativeTopId) {
        this.nativeTopId = nativeTopId;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getServiceStartDate() {
        return serviceStartDate;
    }

    public void setServiceStartDate(Date serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    public Date getServiceEndDate() {
        return serviceEndDate;
    }

    public void setServiceEndDate(Date serviceEndDate) {
        this.serviceEndDate = serviceEndDate;
    }

    public Date getOrderEndDate() {
        return orderEndDate;
    }

    public void setOrderEndDate(Date orderEndDate) {
        this.orderEndDate = orderEndDate;
    }

    public String getServicePortrait() {
        return servicePortrait;
    }

    public void setServicePortrait(String servicePortrait) {
        this.servicePortrait = servicePortrait;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceUnit() {
        return serviceUnit;
    }

    public void setServiceUnit(String serviceUnit) {
        this.serviceUnit = serviceUnit;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getApplyCancelReason() {
        return applyCancelReason;
    }

    public void setApplyCancelReason(String applyCancelReason) {
        this.applyCancelReason = applyCancelReason;
    }

    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }
}

