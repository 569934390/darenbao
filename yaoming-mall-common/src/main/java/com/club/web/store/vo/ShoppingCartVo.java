package com.club.web.store.vo;

/**
 * 购物车信息对象
 * 
 * @author wqh
 * 
 * @add by 2016-04-07
 */
public class ShoppingCartVo {
	private String id;
	// 商品skuId
	private String goodId;
	// 商品Id
	private String goodsId;
	// 商店Id
	private String shopId;
	// 商店名称
	private String shopName;
	// 用户Id
	private String userId;
	// 商品数量
	private Integer goodCount;
	// 商品市场价格
	private double marketPrice;
	// 商品促销价格
	private double retailPrice;
	// 商品零售价价格
	private double salePrice;
	// 商品描述
	private String goodDesc;
	// 商品名称
	private String goodTitle;
	// 商品图片地址
	private String goodImgUrl;
	// 货品skuId
	private long skuId;
	// 规格值列表
	private String skuItem;
	// 销量
	private int saleNum;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGoodDesc() {
		return goodDesc;
	}

	public void setGoodDesc(String goodDesc) {
		this.goodDesc = goodDesc;
	}

	public String getGoodTitle() {
		return goodTitle;
	}

	public void setGoodTitle(String goodTitle) {
		this.goodTitle = goodTitle;
	}

	public String getGoodImgUrl() {
		return goodImgUrl;
	}

	public void setGoodImgUrl(String goodImgUrl) {
		this.goodImgUrl = goodImgUrl;
	}

	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getGoodCount() {
		return goodCount;
	}

	public void setGoodCount(Integer goodCount) {
		this.goodCount = goodCount;
	}

	public double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public double getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(double retailPrice) {
		this.retailPrice = retailPrice;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public long getSkuId() {
		return skuId;
	}

	public void setSkuId(long skuId) {
		this.skuId = skuId;
	}

	public String getSkuItem() {
		return skuItem;
	}

	public void setSkuItem(String skuItem) {
		this.skuItem = skuItem;
	}

	public int getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(int saleNum) {
		this.saleNum = saleNum;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

}