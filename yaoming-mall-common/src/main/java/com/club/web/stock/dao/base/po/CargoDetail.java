package com.club.web.stock.dao.base.po;

import java.util.Date;

public class CargoDetail {
    private Long id;

    private Long cargoId;

    private String cargoInfoType;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    private byte[] cargoInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCargoId() {
        return cargoId;
    }

    public void setCargoId(Long cargoId) {
        this.cargoId = cargoId;
    }

    public String getCargoInfoType() {
        return cargoInfoType;
    }

    public void setCargoInfoType(String cargoInfoType) {
        this.cargoInfoType = cargoInfoType == null ? null : cargoInfoType.trim();
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

    public byte[] getCargoInfo() {
        return cargoInfo;
    }

    public void setCargoInfo(byte[] cargoInfo) {
        this.cargoInfo = cargoInfo;
    }
}