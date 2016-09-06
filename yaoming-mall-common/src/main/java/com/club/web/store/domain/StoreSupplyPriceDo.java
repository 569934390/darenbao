package com.club.web.store.domain;

public class StoreSupplyPriceDo {
    private Long id;

    private Long storeLevelId;

    private Long goodId;

    private Long goodSkuId;

    private Double supplyPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreLevelId() {
        return storeLevelId;
    }

    public void setStoreLevelId(Long storeLevelId) {
        this.storeLevelId = storeLevelId;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public Long getGoodSkuId() {
        return goodSkuId;
    }

    public void setGoodSkuId(Long goodSkuId) {
        this.goodSkuId = goodSkuId;
    }

    public Double getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(Double supplyPrice) {
        this.supplyPrice = supplyPrice;
    }
}