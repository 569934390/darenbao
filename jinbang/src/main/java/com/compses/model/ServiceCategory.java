package com.compses.model;

import java.util.Date;

/**
 服务类别表 Date:2016-07-24 16:07:26
 **/
public class ServiceCategory{

    /**id**/
    private String serviceCategoryId;
    /**服务类别名称**/
    private String categoryName;
    /**创建时间**/
    private Date createDate;
    /**所属省id**/
    private String nativeProviceId;
    /**所属省名称**/
    private String nativeProviceName;
    /**所属市id**/
    private String nativeCityId;
    /**所属市名称**/
    private String nativeCityName;
    /**状态**/
    private String status;
    /**备注**/
    private String remark;
    /****/
    private String chargeUserId;
    /****/
    private String iconUrl;

    public String getServiceCategoryId(){
        return serviceCategoryId;
    }
    public void setServiceCategoryId(String serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
    }
    public String getCategoryName(){
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public Date getCreateDate(){
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public String getNativeProviceName(){
        return nativeProviceName;
    }
    public void setNativeProviceName(String nativeProviceName) {
        this.nativeProviceName = nativeProviceName;
    }
    public String getNativeCityName(){
        return nativeCityName;
    }
    public void setNativeCityName(String nativeCityName) {
        this.nativeCityName = nativeCityName;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getRemark(){
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getChargeUserId(){
        return chargeUserId;
    }
    public void setChargeUserId(String chargeUserId) {
        this.chargeUserId = chargeUserId;
    }
    public String getIconUrl(){
        return iconUrl;
    }
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getNativeProviceId() {
        return nativeProviceId;
    }

    public void setNativeProviceId(String nativeProviceId) {
        this.nativeProviceId = nativeProviceId;
    }

    public String getNativeCityId() {
        return nativeCityId;
    }

    public void setNativeCityId(String nativeCityId) {
        this.nativeCityId = nativeCityId;
    }
}
