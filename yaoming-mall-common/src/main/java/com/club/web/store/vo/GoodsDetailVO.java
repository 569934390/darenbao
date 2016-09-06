package com.club.web.store.vo;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class GoodsDetailVO {
	
	private boolean isRecommend;
	private boolean isHot;
	
	private List<GoodsSpecVO> specs;
	private ClassifyVO classify;
	private List<String> imagesTitle;
	private List<String> imagesDetail;
	private BigDecimal monthSell;
	private BigDecimal stock;
	private GoodsSimpleVO simpleData;

	public GoodsDetailVO(GoodsSimpleVO simpleData, List<GoodsSpecVO> specs, ClassifyVO classify, List<String> imagesTitle, List<String> imagesDetail, boolean isRecommend, boolean isHot, BigDecimal monthSell, BigDecimal stock) {
		this.simpleData = simpleData;
		this.specs = specs;
		this.isRecommend = isRecommend;
		this.isHot = isHot;
		this.classify = classify;
		this.imagesTitle = imagesTitle;
		this.imagesDetail =	imagesDetail; 
		this.monthSell = monthSell;
		this.stock = stock;
	}

	public long getGoodsId() {
		return simpleData.getGoodsId();
	}
	public String getTitle() {
		return simpleData.getTitle();
	}
	public BigDecimal getPrice() {
		return simpleData.getPrice();
	}
	public BigDecimal getDiscount() {
		return simpleData.getDiscount();
	}
	public String getCoverImg() {
		return simpleData.getCoverImg();
	}
	public BigDecimal getRate() {
		return simpleData.getRate();
	}
	public List<GoodsSpecVO> getSpecs() {
		return specs;
	}
	public ClassifyVO getClassify() {
		return classify;
	}
	public List<String> getImagesTitle() {
		return imagesTitle;
	}
	public List<String> getImagesDetail() {
		return imagesDetail;
	}
	public BigDecimal getMonthSell() {
		return monthSell;
	}
	public BigDecimal getStock() {
		return stock;
	}

	@JsonIgnore
	public boolean isDiscountGoods(){
		return getDiscount().compareTo(BigDecimal.ZERO)>0 && getDiscount().compareTo(getPrice())<0;
	}
	@JsonIgnore
	public boolean isRecommend(){
		return isRecommend;
	}
	@JsonIgnore
	public boolean isHot() {
		return isHot;
	}
	@JsonIgnore
	public GoodsSimpleVO getSimpleData() {
		return simpleData;
	}
}
