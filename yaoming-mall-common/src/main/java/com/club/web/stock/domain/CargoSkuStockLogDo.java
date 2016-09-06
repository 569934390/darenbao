package com.club.web.stock.domain;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.club.web.stock.domain.repository.StockManagerRepository;

public class CargoSkuStockLogDo {
	private Logger logger = LoggerFactory.getLogger(CargoSkuStockLogDo.class);
	@Autowired
	StockManagerRepository repository;
	private Long id;

	private Long cargoSkuId;

	private Integer outShelvesNo;

	private Integer onSalesNo;

	private Integer onPayNo;

	private Integer onSendNo;

	private Long inboundId;

	private Date createTime;

	private Long createBy;

	private Integer updateCount;

	private Integer updateStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCargoSkuId() {
		return cargoSkuId;
	}

	public void setCargoSkuId(Long cargoSkuId) {
		this.cargoSkuId = cargoSkuId;
	}

	public Long getInboundId() {
		return inboundId;
	}

	public void setInboundId(Long inboundId) {
		this.inboundId = inboundId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Integer getUpdateCount() {
		return updateCount;
	}

	public void setUpdateCount(Integer updateCount) {
		this.updateCount = updateCount;
	}

	public Integer getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(Integer updateStatus) {
		this.updateStatus = updateStatus;
	}

	public void save() {
		try {
			repository.save(this);
		} catch (Exception e) {
			logger.error("保存库存日志异常:<save>", e);
		}
	}

	public Integer getOutShelvesNo() {
		return outShelvesNo;
	}

	public void setOutShelvesNo(Integer outShelvesNo) {
		this.outShelvesNo = outShelvesNo;
	}

	public Integer getOnSalesNo() {
		return onSalesNo;
	}

	public void setOnSalesNo(Integer onSalesNo) {
		this.onSalesNo = onSalesNo;
	}

	public Integer getOnPayNo() {
		return onPayNo;
	}

	public void setOnPayNo(Integer onPayNo) {
		this.onPayNo = onPayNo;
	}

	public Integer getOnSendNo() {
		return onSendNo;
	}

	public void setOnSendNo(Integer onSendNo) {
		this.onSendNo = onSendNo;
	}
}