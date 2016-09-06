package com.club.web.store.vo;

import java.math.BigDecimal;
import java.util.List;

public class GoodsCommentVO {
	private BigDecimal rate;
	private List<CommentVO> list;
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public List<CommentVO> getList() {
		return list;
	}
	public void setList(List<CommentVO> list) {
		this.list = list;
	}
}
