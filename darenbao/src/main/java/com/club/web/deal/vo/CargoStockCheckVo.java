package com.club.web.deal.vo;

import java.util.List;

import com.club.web.stock.vo.CargoSkuItemVo;

public class CargoStockCheckVo {
	
	private String id;
		
	private String classifyName;
		
	private String brandName;
	
	private String goodsName;
	
	private String goodsCode;
	
	private int indentNumber;
	
	private Integer indentStatus;
	
	private String skuId;
		
	private int shelveNopay;
	
	private int indentNopay;
	
	private int shelveNosend;
	
	private int indentNosend;
	
	private String skuCode;
	
	private List<CargoSkuItemVo> item;


	public List<CargoSkuItemVo> getItem() {
		return item;
	}

	public void setItem(List<CargoSkuItemVo> item) {
		this.item = item;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public int getIndentNumber() {
		return indentNumber;
	}

	public void setIndentNumber(int indentNumber) {
		this.indentNumber = indentNumber;
	}

	public Integer getIndentStatus() {
		return indentStatus;
	}

	public void setIndentStatus(Integer indentStatus) {
		this.indentStatus = indentStatus;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public int getShelveNopay() {
		return shelveNopay;
	}

	public void setShelveNopay(int shelveNopay) {
		this.shelveNopay = shelveNopay;
	}

	public int getIndentNopay() {
		return indentNopay;
	}

	public void setIndentNopay(int indentNopay) {
		this.indentNopay = indentNopay;
	}

	public int getShelveNosend() {
		return shelveNosend;
	}

	public void setShelveNosend(int shelveNosend) {
		this.shelveNosend = shelveNosend;
	}

	public int getIndentNosend() {
		return indentNosend;
	}

	public void setIndentNosend(int indentNosend) {
		this.indentNosend = indentNosend;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
}
