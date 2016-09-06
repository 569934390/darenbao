package com.club.web.stock.dao.base.po;

import java.util.Date;

public class CargoSkuOption {
    private Long id;

    private Long cargoSkuId;

    private Long cargoSkuItemId;

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

    public Long getCargoSkuId() {
        return cargoSkuId;
    }

    public void setCargoSkuId(Long cargoSkuId) {
        this.cargoSkuId = cargoSkuId;
    }

    public Long getCargoSkuItemId() {
        return cargoSkuItemId;
    }

    public void setCargoSkuItemId(Long cargoSkuItemId) {
        this.cargoSkuItemId = cargoSkuItemId;
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