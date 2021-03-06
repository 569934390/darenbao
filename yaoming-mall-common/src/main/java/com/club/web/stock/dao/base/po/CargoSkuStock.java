package com.club.web.stock.dao.base.po;

import java.util.Date;

public class CargoSkuStock {
    private Long id;

    private Long cargoSkuId;

    private Integer outShelvesNo;

    private Integer onSalesNo;

    private Integer onPayNo;

    private Integer onSendNo;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    private Integer remainCount;

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

    public Integer getOutShelvesNo() {
        return outShelvesNo;
    }

    public void setOutShelvesNo(Integer outShelvesNo) {
        this.outShelvesNo = outShelvesNo;
    }

    public Integer getOnSalesNo() {
        return onSalesNo;
    }

    public void setOnSalesNo(Integer onSalesNo) {
        this.onSalesNo = onSalesNo;
    }

    public Integer getOnPayNo() {
        return onPayNo;
    }

    public void setOnPayNo(Integer onPayNo) {
        this.onPayNo = onPayNo;
    }

    public Integer getOnSendNo() {
        return onSendNo;
    }

    public void setOnSendNo(Integer onSendNo) {
        this.onSendNo = onSendNo;
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

    public Integer getRemainCount() {
        return remainCount;
    }

    public void setRemainCount(Integer remainCount) {
        this.remainCount = remainCount;
    }
}