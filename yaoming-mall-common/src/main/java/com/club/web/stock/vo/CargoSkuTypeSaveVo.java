package com.club.web.stock.vo;

public class CargoSkuTypeSaveVo {
	private long skuTypeId;
	private long[] skuItemIds;
	
	public long getSkuTypeId() {
		return skuTypeId;
	}
	public void setSkuTypeId(long skuTypeId) {
		this.skuTypeId = skuTypeId;
	}
	public long[] getSkuItemIds() {
		return skuItemIds;
	}
	public void setSkuItemIds(long[] skuItemIds) {
		this.skuItemIds = skuItemIds;
	}
}
