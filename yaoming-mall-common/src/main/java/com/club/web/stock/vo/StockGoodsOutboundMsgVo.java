/**
 * 
 */
package com.club.web.stock.vo;

import java.util.List;

/**
 * 出库单联合查询对象
 * 
 * @author wqh
 * @add by 2016-03-31
 */
public class StockGoodsOutboundMsgVo {
	private String skuId;
	private String brandName;
	private String classifyName;
	private String goodsCode;
	private String goodsName;
	private List<CargoSkuItemVo> item;
	private String skuCode;
	private int goodCount;
	private String id;
	private int skuCount;

	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public List<CargoSkuItemVo> getItem() {
		return item;
	}

	public void setItem(List<CargoSkuItemVo> item) {
		this.item = item;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public int getGoodCount() {
		return goodCount;
	}

	public void setGoodCount(int goodCount) {
		this.goodCount = goodCount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSkuCount() {
		return skuCount;
	}

	public void setSkuCount(int skuCount) {
		this.skuCount = skuCount;
	}
}
