package com.club.web.store.vo;

import java.util.List;

public class DiscountGoodsVO {
	private String time;
	private List<GoodsSimpleVO> list;
	
	public DiscountGoodsVO(String time, List<GoodsSimpleVO> list) {
		this.time = time;
		this.list = list;
	}

	public String getTime() {
		return time;
	}

	public List<GoodsSimpleVO> getList() {
		return list;
	}
	
}
