package com.club.web.stock.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.stock.domain.repository.StockManagerRepository;

@Configurable
public class CargoOutboundDetailDo {
	@Autowired
	StockManagerRepository repository;
	private Long id;

	private Long outboundId;

	private Long skuId;

	private Integer count;

	/**
	 * 1-存在(更新)0-不存在(新增)
	 */
	private int flag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOutboundId() {
		return outboundId;
	}

	public void setOutboundId(Long outboundId) {
		this.outboundId = outboundId;
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

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public void save() throws Exception {
		repository.save(this);
	}

	public void update() throws Exception {
		repository.updateOutbound(this);
	}
}