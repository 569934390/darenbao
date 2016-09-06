package com.club.web.store.dao.base.po;

import java.math.BigDecimal;

public class CarriageRuleDetail {
    private Long id;

    private Long carriageRuleId;

    private BigDecimal indentMoneyFull;

    private BigDecimal carriageFull;

    private BigDecimal carriageNotFull;

    private String deliverRegion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarriageRuleId() {
        return carriageRuleId;
    }

    public void setCarriageRuleId(Long carriageRuleId) {
        this.carriageRuleId = carriageRuleId;
    }

    public BigDecimal getIndentMoneyFull() {
        return indentMoneyFull;
    }

    public void setIndentMoneyFull(BigDecimal indentMoneyFull) {
        this.indentMoneyFull = indentMoneyFull;
    }

    public BigDecimal getCarriageFull() {
        return carriageFull;
    }

    public void setCarriageFull(BigDecimal carriageFull) {
        this.carriageFull = carriageFull;
    }

    public BigDecimal getCarriageNotFull() {
        return carriageNotFull;
    }

    public void setCarriageNotFull(BigDecimal carriageNotFull) {
        this.carriageNotFull = carriageNotFull;
    }

    public String getDeliverRegion() {
        return deliverRegion;
    }

    public void setDeliverRegion(String deliverRegion) {
        this.deliverRegion = deliverRegion == null ? null : deliverRegion.trim();
    }
}