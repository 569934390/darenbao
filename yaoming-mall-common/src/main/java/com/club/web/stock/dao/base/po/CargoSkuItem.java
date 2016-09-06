package com.club.web.stock.dao.base.po;

import java.util.Date;

public class CargoSkuItem {
    private Long id;

    private Long cargoSkuTypeId;

    private Long cargoBaseSkuItemId;

    private String value;

    private String name;

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

    public Long getCargoSkuTypeId() {
        return cargoSkuTypeId;
    }

    public void setCargoSkuTypeId(Long cargoSkuTypeId) {
        this.cargoSkuTypeId = cargoSkuTypeId;
    }

    public Long getCargoBaseSkuItemId() {
        return cargoBaseSkuItemId;
    }

    public void setCargoBaseSkuItemId(Long cargoBaseSkuItemId) {
        this.cargoBaseSkuItemId = cargoBaseSkuItemId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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