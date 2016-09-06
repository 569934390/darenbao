package com.club.web.store.dao.base.po;

public class GoodLabels {
    private Long id;

    private Long goodId;

    private Long goodBaseLabelId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public Long getGoodBaseLabelId() {
        return goodBaseLabelId;
    }

    public void setGoodBaseLabelId(Long goodBaseLabelId) {
        this.goodBaseLabelId = goodBaseLabelId;
    }
}