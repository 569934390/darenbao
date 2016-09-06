package com.club.web.stock.vo;

import java.util.List;

/**
 * 库存对象
 * 
 * @author wqh
 * 
 * @add by 2016-03-29
 */
public class CargoSkuStockVo {

	// 库存对象Id
	private String id;
	private String skuId;
	private String skuCode;
	private String goodsName;
	private String goodsCode;
	private String classyName;
	private String brandName;
	private List<CargoSkuItemVo> item;
	private int noShelve;
	private int shelveNoShale;
	private int shelveNoPay;
	private int trueShelveNoPay;
	private int shelveNoSend;
	private int trueShelveNoSend;
	private int total;
	private int remainCount;

	public int getTrueShelveNoPay() {
		return trueShelveNoPay;
	}

	public void setTrueShelveNoPay(int trueShelveNoPay) {
		this.trueShelveNoPay = trueShelveNoPay;
	}

	public int getTrueShelveNoSend() {
		return trueShelveNoSend;
	}

	public void setTrueShelveNoSend(int trueShelveNoSend) {
		this.trueShelveNoSend = trueShelveNoSend;
	}

	public int getRemainCount() {
		return remainCount;
	}

	public void setRemainCount(int remainCount) {
		this.remainCount = remainCount;
	}

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

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
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

	public String getClassyName() {
		return classyName;
	}

	public void setClassyName(String classyName) {
		this.classyName = classyName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public List<CargoSkuItemVo> getItem() {
		return item;
	}

	public void setItem(List<CargoSkuItemVo> item) {
		this.item = item;
	}

	public int getNoShelve() {
		return noShelve;
	}

	public void setNoShelve(int noShelve) {
		this.noShelve = noShelve;
	}

	public int getShelveNoShale() {
		return shelveNoShale;
	}

	public void setShelveNoShale(int shelveNoShale) {
		this.shelveNoShale = shelveNoShale;
	}

	public int getShelveNoPay() {
		return shelveNoPay;
	}

	public void setShelveNoPay(int shelveNoPay) {
		this.shelveNoPay = shelveNoPay;
	}

	public int getShelveNoSend() {
		return shelveNoSend;
	}

	public void setShelveNoSend(int shelveNoSend) {
		this.shelveNoSend = shelveNoSend;
	}

	public int getTotal() {
		total = noShelve + shelveNoShale + shelveNoPay + shelveNoSend + remainCount;
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}