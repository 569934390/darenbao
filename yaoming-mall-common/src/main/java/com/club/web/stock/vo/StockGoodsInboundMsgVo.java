/**
 * 
 */
package com.club.web.stock.vo;

import java.util.List;

/**
 * 入库单联合查询对象
 * 
 * @author wqh
 * @add by 2016-03-22
 */
public class StockGoodsInboundMsgVo {
	private String skuId;
	private String brandName;
	private String typeName;
	private String goodsNo;
	private String goodsName;
	private List<CargoSkuItemVo> item;
	private String skuCode;
	private int goodCount;
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public int getGoodCount() {
		return goodCount;
	}

	public void setGoodCount(int goodCount) {
		this.goodCount = goodCount;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public List<CargoSkuItemVo> getItem() {
		return item;
	}

	public void setItem(List<CargoSkuItemVo> item) {
		this.item = item;
	}

	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
