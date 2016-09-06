package com.club.web.stock.vo;

import java.math.BigDecimal;

public class CargoSkuAddVo {
	private String skuCode;
	private BigDecimal price;
	private long[] skuItems;
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public long[] getSkuItems() {
		return skuItems;
	}
	public void setSkuItems(long[] skuItems) {
		this.skuItems = skuItems;
	}
}
