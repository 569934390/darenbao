package com.club.web.store.vo;

import java.math.BigDecimal;

public class GoodsSimpleVO {
	private long goodsId;
	private String title;
	private BigDecimal price;
	private BigDecimal discount;
	private String coverImg;
	private BigDecimal rate;
	public GoodsSimpleVO(long goodsId, String title, BigDecimal price, BigDecimal discount, String coverImg,
			BigDecimal rate) {
		this.goodsId = goodsId;
		this.title = title;
		this.price = price;
		this.discount = discount;
		this.coverImg = coverImg;
		this.rate = rate;
	}
	public long getGoodsId() {
		return goodsId;
	}
	public String getTitle() {
		return title;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public String getCoverImg() {
		return coverImg;
	}
	public BigDecimal getRate() {
		return rate;
	}
}
