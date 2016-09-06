package com.club.web.stock.vo;

import java.math.BigDecimal;

/**
 * 
 * @author yunpeng.xiong
 *
 */
public class CargoSkuSimpleVo {
	private String id;
	private String cargoId;
	private String code;
	private BigDecimal price;
	private String sku;	// SKU选定项Name列表，使用","分隔
	private String skuLong;	// SKU选定项Name列表，使用","分隔，带TypeName
	private String skuValue;	// SKU选定项ID列表，使用","分隔，有小到大有序
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCargoId() {
		return cargoId;
	}
	public void setCargoId(String cargoId) {
		this.cargoId = cargoId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getSkuLong() {
		return skuLong;
	}
	public void setSkuLong(String skuLong) {
		this.skuLong = skuLong;
	}
	public String getSkuValue() {
		return skuValue;
	}
	public void setSkuValue(String skuValue) {
		this.skuValue = skuValue;
	}
	
}
