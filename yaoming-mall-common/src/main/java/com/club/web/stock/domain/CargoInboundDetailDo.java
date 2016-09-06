package com.club.web.stock.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.stock.domain.repository.StockManagerRepository;

/**
 * 入库单明细对象
 * 
 * @author wqh
 * 
 * @add by 2016-03-20
 */
@Configurable
public class CargoInboundDetailDo {
	@Autowired
	StockManagerRepository repository;
	private Long id;

	private Long skuId;

	private Integer count;

	private Long inboundId;
	/**
	 * 1-存在(更新)0-不存在(新增)
	 */
	private int flag;

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

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

	public void save() throws Exception {
		repository.save(this);
	}

	public void update() throws Exception {
		repository.updateInboundDetail(this);
	}
}