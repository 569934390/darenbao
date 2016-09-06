package com.club.web.stock.domain;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.framework.util.StringUtils;
import com.club.web.stock.domain.repository.StockManagerRepository;
import com.club.web.stock.vo.CargoInboundDetailVo;

/**
 * 入库单对象
 * 
 * @author wqh
 * 
 * @add by 2016-03-20
 */
@Configurable
public class CargoInboundOrderDo {
	@Autowired
	StockManagerRepository repository;
	private Long id;

	private Integer status;

	private String sourceNo;

	private String remarks;

	private Date inboundTime;

	private Date subTime;

	private Date createTime;

	private long createBy;

	private String applyDesc;

	public String getApplyDesc() {
		return applyDesc;
	}

	public void setApplyDesc(String applyDesc) {
		this.applyDesc = applyDesc;
	}

	private List<CargoInboundDetailDo> detail;

	public List<CargoInboundDetailDo> getDetail() {
		return detail;
	}

	public void setDetail(List<CargoInboundDetailDo> detail) {
		this.detail = detail;
	}

	public long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(long createBy) {
		this.createBy = createBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSourceNo() {
		return sourceNo;
	}

	public void setSourceNo(String sourceNo) {
		this.sourceNo = sourceNo == null ? null : sourceNo.trim();
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks == null ? null : remarks.trim();
	}

	public Date getInboundTime() {
		return inboundTime;
	}

	public void setInboundTime(Date inboundTime) {
		this.inboundTime = inboundTime;
	}

	public Date getSubTime() {
		return subTime;
	}

	public void setSubTime(Date subTime) {
		this.subTime = subTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void save() throws Exception {
		repository.save(this);
		if (detail != null) {
			for (CargoInboundDetailDo inbound : detail) {
				inbound.save();
			}
		}
	}

	public void update(int status, String desc, long userId) throws Exception {
		List<CargoInboundDetailVo> inboundDetailList = null;
		CargoSkuStockDo stockDo = null;
		this.setStatus(status);
		this.setInboundTime(new Date());
		this.setSubTime(new Date());
		if (StringUtils.isNotEmpty(desc)) {
			this.setApplyDesc(desc);
		}
		repository.updateInboundOrderStatus(this);
		// 审核状态为(确认-3)-创建/更新库存
		if (status == 3) {
			inboundDetailList = repository.queryByInboundId(id);
			if (inboundDetailList != null && inboundDetailList.stream().count() > 0) {
				for (CargoInboundDetailVo dv : inboundDetailList) {
					stockDo = repository.createStockObj(dv.getSkuId(), dv.getCount(), userId);
					if (stockDo != null) {
						if (stockDo.getFlag() == 0) {
							stockDo.save();
						} else {
							stockDo.update(1, dv.getCount());
						}
					}
				}
			}
		}
	}

}