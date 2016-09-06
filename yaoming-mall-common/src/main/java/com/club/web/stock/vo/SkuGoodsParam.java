package com.club.web.stock.vo;

/**
 * 参数设置
 * 
 * @author wqh
 * @add by 2016-04-06
 */
public class SkuGoodsParam {
	private String cargoSkuId;
	private String num;
	// 商品SkuId
	private String goodSkuId;
	//商品状态 0:下架 1:上架
	private int goodStatus = 0;

	/**
	 * @return the goodSkuId
	 */
	public String getGoodSkuId() {
		return goodSkuId;
	}

	/**
	 * @param goodSkuId
	 *            the goodSkuId to set
	 */
	public void setGoodSkuId(String goodSkuId) {
		this.goodSkuId = goodSkuId;
	}

	/**
	 * @return the cargoSkuId
	 */
	public String getCargoSkuId() {
		return cargoSkuId;
	}

	/**
	 * @param cargoSkuId
	 *            the cargoSkuId to set
	 */
	public void setCargoSkuId(String cargoSkuId) {
		this.cargoSkuId = cargoSkuId;
	}

	/**
	 * @return the num
	 */
	public String getNum() {
		return num;
	}

	/**
	 * @param num
	 *            the num to set
	 */
	public void setNum(String num) {
		this.num = num;
	}

	public int getGoodStatus() {
		return goodStatus;
	}

	public void setGoodStatus(int goodStatus) {
		this.goodStatus = goodStatus;
	}

}
