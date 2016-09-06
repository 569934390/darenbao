package com.club.web.deal.vo;

import com.club.web.store.vo.TradeGoodSkuVo;

/**
 * 订单条目Vo
 * @author zhuzd
 *
 */
public class IndentListVo {
    
	private String id;

    private String indentId;
    
    private String tradeGoodSkuId;
    
    private String cargoSkuId;

    private TradeGoodSkuVo tradeGoodSku;
    
    private Integer number;

    private String finalAmount;

    private String tradeGoodName;

    private String tradeGoodImgUrl;

    private String tradeGoodAmount;
    
    private String supplyPrice;

    private String tradeGoodType;  //规格汇总
    
    private String cargoNo;
    
	public String getTradeGoodSkuId() {
		return tradeGoodSkuId;
	}

	public void setTradeGoodSkuId(String tradeGoodSkuId) {
		this.tradeGoodSkuId = tradeGoodSkuId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIndentId() {
		return indentId;
	}

	public void setIndentId(String indentId) {
		this.indentId = indentId;
	}

	public TradeGoodSkuVo getTradeGoodSku() {
		return tradeGoodSku;
	}

	public void setTradeGoodSku(TradeGoodSkuVo tradeGoodSku) {
		this.tradeGoodSku = tradeGoodSku;
	}

	public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(String finalAmount) {
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

    public String getTradeGoodAmount() {
        return tradeGoodAmount;
    }

    public void setTradeGoodAmount(String tradeGoodAmount) {
        this.tradeGoodAmount = tradeGoodAmount;
    }

    public String getTradeGoodType() {
        return tradeGoodType;
    }

    public void setTradeGoodType(String tradeGoodType) {
        this.tradeGoodType = tradeGoodType == null ? null : tradeGoodType.trim();
    }
	public String getCargoSkuId() {
		return cargoSkuId;
	}

	public void setCargoSkuId(String cargoSkuId) {
		this.cargoSkuId = cargoSkuId;
	}
	public String getSupplyPrice() {
		return supplyPrice;
	}

	public void setSupplyPrice(String supplyPrice) {
		this.supplyPrice = supplyPrice;
	}

	public String getCargoNo() {
		return cargoNo;
	}

	public void setCargoNo(String cargoNo) {
		this.cargoNo = cargoNo;
	}
        
}