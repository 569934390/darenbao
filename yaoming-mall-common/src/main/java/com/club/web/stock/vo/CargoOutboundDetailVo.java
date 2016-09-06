package com.club.web.stock.vo;

/**
 * 出库单明细对象
 * 
 * @author wqh
 * @add by 2016-03-31
 */
public class CargoOutboundDetailVo {
	private String id;

	private String outboundId;

	private String skuId;

	private Integer count;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOutboundId() {
		return outboundId;
	}

	public void setOutboundId(String outboundId) {
		this.outboundId = outboundId;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}