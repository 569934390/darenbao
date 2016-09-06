package com.club.web.stock.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.stock.domain.repository.StockManagerRepository;

@Configurable
public class CargoSkuStockDo {
	@Autowired
	StockManagerRepository repository;
	private Long id;

	private Long cargoSkuId;

	private Integer outShelvesNo;

	private Integer onSalesNo;

	private Integer onPayNo;

	private Integer onSendNo;

	private Date createTime;

	private Long createBy;

	private Date updateTime;

	private Long updateBy;

	private Integer remainCount;

	public Integer getRemainCount() {
		return remainCount;
	}

	public void setRemainCount(Integer remainCount) {
		this.remainCount = remainCount;
	}

	// TODO 需要说明这个标记的作用
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

	public Long getCargoSkuId() {
		return cargoSkuId;
	}

	public void setCargoSkuId(Long cargoSkuId) {
		this.cargoSkuId = cargoSkuId;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public void save() throws Exception {
		repository.save(this);
		
		// TODO 这段代码被使用4次，可以写成一个方法
		// BEGIN
		try {
			CargoSkuStockLogDo stockLog = repository.createStockLogObj(this, 1, id, this.getOutShelvesNo());
			if (stockLog != null) {
				stockLog.save();
			}
		} catch (Exception e) {

		}
		// END
	}

	public void update(int status, int count) throws Exception {
		repository.updateStock(this);
		try {
			CargoSkuStockLogDo stockLog = repository.createStockLogObj(this, status, id, count);
			if (stockLog != null) {
				stockLog.save();
			}
		} catch (Exception e) {

		}
	}

	public void update(int count, int variable, long userId) throws Exception {
		this.setRemainCount(count);
		this.setOutShelvesNo(this.getOutShelvesNo() + variable);
		this.setUpdateBy(userId);
		this.setUpdateTime(new Date());
		repository.updateStock(this);
		try {
			CargoSkuStockLogDo stockLog = repository.createStockLogObj(this, 7, id, count);
			if (stockLog != null) {
				stockLog.save();
			}
		} catch (Exception e) {

		}
	}

	public void update(int variable_pay, int variable_send, int truePay, int trueSend, long userId) throws Exception {
		this.setOnSalesNo(this.getOnSalesNo() - (variable_pay + variable_send));
		this.setOnPayNo(truePay);
		this.setOnSendNo(trueSend);
		this.setUpdateBy(userId);
		this.setUpdateTime(new Date());
		repository.updateStock(this);
	}

	public void upate(int count, long userId, int status) throws Exception {
		if (status == 0) {
			this.setOutShelvesNo(this.getOutShelvesNo() - count);
			this.setOnSalesNo(this.getOnSalesNo() + count);
		} else if (status == 1) {
			this.setOutShelvesNo(this.getOutShelvesNo() + count);
			this.setOnSalesNo(this.getOnSalesNo() - count);
		}
		this.setUpdateBy(userId);
		this.setUpdateTime(new Date());
		repository.updateStock(this);
		try {
			CargoSkuStockLogDo stockLog = repository.createStockLogObj(this, status + 2, id, count);
			if (stockLog != null) {
				stockLog.save();
			}
		} catch (Exception e) {

		}
	}
}