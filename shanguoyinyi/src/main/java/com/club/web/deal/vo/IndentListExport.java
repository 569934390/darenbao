package com.club.web.deal.vo;

/**
 * 订单条目导出Vo
 * @author zhuzd
 *
 */
public class IndentListExport {
    
    private String id;
	
    private String indentId;
    
    private Integer number;

    private String finalAmount;

    private String tradeGoodName;

    private String tradeGoodType;  //规格汇总
    
    private String cargoNo;

	public String getIndentId() {
		return indentId;
	}

	public void setIndentId(String indentId) {
		this.indentId = indentId;
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
		this.tradeGoodName = tradeGoodName;
	}

	public String getTradeGoodType() {
		return tradeGoodType;
	}

	public void setTradeGoodType(String tradeGoodType) {
		this.tradeGoodType = tradeGoodType;
	}

	public String getCargoNo() {
		return cargoNo;
	}

	public void setCargoNo(String cargoNo) {
		this.cargoNo = cargoNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}