package com.club.web.store.dao.base.po;

import java.math.BigDecimal;
import java.util.Date;

public class ShoppingCart {
    private Long id;

    private Long goodsId;

    private Long shopId;

    private Long userId;

    private Integer goodsCount;

    private BigDecimal goodsPrize;

    private Date createDate;

    private Date updateDate;

    private Integer opertionType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public BigDecimal getGoodsPrize() {
        return goodsPrize;
    }

    public void setGoodsPrize(BigDecimal goodsPrize) {
        this.goodsPrize = goodsPrize;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getOpertionType() {
        return opertionType;
    }

    public void setOpertionType(Integer opertionType) {
        this.opertionType = opertionType;
    }
}