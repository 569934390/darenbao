package com.club.web.stock.vo;

/**
 * 入库单明细对象
 * 
 * @author wqh
 * @add by 2016-03-31
 */
public class CargoInboundDetailVo {
	private Long id;

	private Long skuId;

	private Integer count;

	private Long inboundId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Long getInboundId() {
		return inboundId;
	}

	public void setInboundId(Long inboundId) {
		this.inboundId = inboundId;
	}
}