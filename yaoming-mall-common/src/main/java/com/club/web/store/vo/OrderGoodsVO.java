package com.club.web.store.vo;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OrderGoodsVO {
	private GoodsDetailVO goods;
	private BigDecimal price;
	private int count;
	public OrderGoodsVO(GoodsDetailVO goods, BigDecimal price, int count) {
		this.goods = goods;
		this.price = price;
		this.count = count;
	}
	@JsonIgnore
	public GoodsDetailVO getGoods() {
		return goods;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public int getCount() {
		return count;
	}
	public long getGoodsId() {
		return goods.getGoodsId();
	}
	public String getTitle() {
		return goods.getTitle();
	}
	public String getCoverImg() {
		return goods.getCoverImg();
	}
	public List<GoodsSpecVO> getSpecs() {
		return goods.getSpecs();
	}
}
