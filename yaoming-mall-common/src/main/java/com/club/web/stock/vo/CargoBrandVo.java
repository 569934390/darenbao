package com.club.web.stock.vo;

import java.util.Date;

/**
* @Title: CargoBrandVo.java 
* @Package com.club.web.stock.vo 
* @Description: 品牌Vo
* @author 柳伟军   
* @date 2016年3月26日 上午9:31:50 
* @version V1.0
 */
public class CargoBrandVo {
    private String id;

    private String logo;

    private String name;

    private String url;

    private String supplierName;

    private Integer brandRecommendation;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public Integer getBrandRecommendation() {
        return brandRecommendation;
    }

    public void setBrandRecommendation(Integer brandRecommendation) {
        this.brandRecommendation = brandRecommendation;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }
}