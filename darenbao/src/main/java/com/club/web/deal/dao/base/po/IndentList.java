package com.club.web.deal.dao.base.po;

import java.math.BigDecimal;

public class IndentList {
    private Long id;

    private Long indentId;

    private Long tradeGoodSkuId;
    
    private Long cargoSkuId;

    private Integer number;

    private BigDecimal finalAmount;

    private String tradeGoodName;

    private String tradeGoodImgUrl;

    private BigDecimal tradeGoodAmount;
    
    private BigDecimal supplyPrice;

    private String tradeGoodType;

    private String cargoNo;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIndentId() {
        return indentId;
    }

    public void setIndentId(Long indentId) {
        this.indentId = indentId;
    }

    public Long getTradeGoodSkuId() {
        return tradeGoodSkuId;
    }

    public void setTradeGoodSkuId(Long tradeGoodSkuId) {
        this.tradeGoodSkuId = tradeGoodSkuId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getTradeGoodName() {
        return tradeGoodName;
    }

    public void setTradeGoodName(String tradeGoodName) {
        this.tradeGoodName = tradeGoodName == null ? null : tradeGoodName.trim();
    }

    public String getTradeGoodImgUrl() {
        return tradeGoodImgUrl;
    }

    public void setTradeGoodImgUrl(String tradeGoodImgUrl) {
        this.tradeGoodImgUrl = tradeGoodImgUrl == null ? null : tradeGoodImgUrl.trim();
    }

    public BigDecimal getTradeGoodAmount() {
        return tradeGoodAmount;
    }

    public void setTradeGoodAmount(BigDecimal tradeGoodAmount) {
        this.tradeGoodAmount = tradeGoodAmount;
    }

    public String getTradeGoodType() {
        return tradeGoodType;
    }

    public void setTradeGoodType(String tradeGoodType) {
        this.tradeGoodType = tradeGoodType == null ? null : tradeGoodType.trim();
    }
	public Long getCargoSkuId() {
		return cargoSkuId;
	}

	public void setCargoSkuId(Long cargoSkuId) {
		this.cargoSkuId = cargoSkuId;
	}
	public BigDecimal getSupplyPrice() {
		return supplyPrice;
	}

	public void setSupplyPrice(BigDecimal supplyPrice) {
		this.supplyPrice = supplyPrice;
	}

	public String getCargoNo() {
		return cargoNo;
	}

	public void setCargoNo(String cargoNo) {
		this.cargoNo = cargoNo;
	}
}