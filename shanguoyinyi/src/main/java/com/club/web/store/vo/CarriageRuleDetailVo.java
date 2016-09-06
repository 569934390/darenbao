package com.club.web.store.vo;

import java.math.BigDecimal;

public class CarriageRuleDetailVo {
    private String id;

    private String carriageRuleId;		//配送规则id

    private BigDecimal indentMoneyFull;	//订单满额

    private BigDecimal carriageFull;	//满额运费

    private BigDecimal carriageNotFull;	//未满额运费

    private String deliverRegion;		//配送区域

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCarriageRuleId() {
		return carriageRuleId;
	}

	public void setCarriageRuleId(String carriageRuleId) {
		this.carriageRuleId = carriageRuleId;
	}

	public BigDecimal getIndentMoneyFull() {
        return indentMoneyFull;
    }

    public void setIndentMoneyFull(BigDecimal indentMoneyFull) {
        this.indentMoneyFull = indentMoneyFull == null ? BigDecimal.ZERO : indentMoneyFull;
    }

    public BigDecimal getCarriageFull() {
        return carriageFull;
    }

    public void setCarriageFull(BigDecimal carriageFull) {
        this.carriageFull = carriageFull == null ? BigDecimal.ZERO : carriageFull;
    }

    public BigDecimal getCarriageNotFull() {
        return carriageNotFull;
    }

    public void setCarriageNotFull(BigDecimal carriageNotFull) {
        this.carriageNotFull = carriageNotFull == null ? BigDecimal.ZERO : carriageNotFull;
    }

    public String getDeliverRegion() {
        return deliverRegion;
    }

    public void setDeliverRegion(String deliverRegion) {
        this.deliverRegion = deliverRegion == null ? null : deliverRegion.trim();
    }
}