/**
 * 
 */
package com.club.web.weixin.vo;

/**
 * @author wqh
 * @add by 2016-05-20
 */
public class Condition {
	/**
	 * 店铺Id
	 */
	private long shopId;
	/**
	 * 分类Id
	 */
	private long classifyId;
	/**
	 * 销量排序0-降序1-升序
	 */
	private int saleNumSort;
	/**
	 * 价格排序0-降序1-升序
	 */
	private int priceSort;
	/**
	 * 开始价格
	 */
	private double startPrice;
	/**
	 * 结束价格
	 */
	private double endPrice;
	/**
	 * 品牌
	 */
	private long brandId;
	/**
	 * 用途
	 */
	private long labelId;
	/**
	 * 商品名称
	 */
	private String goodName;
	/**
	 * 栏目
	 */
	private String columnId;

	public long getShopId() {
		return shopId;
	}

	public void setShopId(long shopId) {
		this.shopId = shopId;
	}

	public long getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(long classifyId) {
		this.classifyId = classifyId;
	}

	public int getSaleNumSort() {
		return saleNumSort;
	}

	public void setSaleNumSort(int saleNumSort) {
		this.saleNumSort = saleNumSort;
	}

	public int getPriceSort() {
		return priceSort;
	}

	public void setPriceSort(int priceSort) {
		this.priceSort = priceSort;
	}

	public double getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(double startPrice) {
		this.startPrice = startPrice;
	}

	public double getEndPrice() {
		return endPrice;
	}

	public void setEndPrice(double endPrice) {
		this.endPrice = endPrice;
	}

	public long getBrandId() {
		return brandId;
	}

	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}

	public long getLabelId() {
		return labelId;
	}

	public void setLabelId(long labelId) {
		this.labelId = labelId;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
}
