package com.club.web.stock.dao.base.po;

import java.util.Date;

public class Cargo {
    private Long id;

    private Long supplierId;

    private Long brandId;

    private Long classifyId;

    private String name;

    private String cargoNo;

    private String description;

    private Long smallImageId;

    private Long showImageGroupId;

    private Long detailImageGroupId;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCargoNo() {
        return cargoNo;
    }

    public void setCargoNo(String cargoNo) {
        this.cargoNo = cargoNo == null ? null : cargoNo.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Long getSmallImageId() {
        return smallImageId;
    }

    public void setSmallImageId(Long smallImageId) {
        this.smallImageId = smallImageId;
    }

    public Long getShowImageGroupId() {
        return showImageGroupId;
    }

    public void setShowImageGroupId(Long showImageGroupId) {
        this.showImageGroupId = showImageGroupId;
    }

    public Long getDetailImageGroupId() {
        return detailImageGroupId;
    }

    public void setDetailImageGroupId(Long detailImageGroupId) {
        this.detailImageGroupId = detailImageGroupId;
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