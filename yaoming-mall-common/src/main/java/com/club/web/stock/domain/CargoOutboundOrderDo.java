package com.club.web.stock.domain;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.framework.util.StringUtils;
import com.club.web.stock.domain.repository.StockManagerRepository;

@Configurable
public class CargoOutboundOrderDo {
	@Autowired
	StockManagerRepository repository;
	private Long id;

	private Integer status;

	private Integer outboundType;

	private String sourceNo;

	private Date outboundTime;

	private Date subTime;

	private String remarks;

	private Date createTime;

	private Long createBy;

	private String applyDesc;

	private List<CargoOutboundDetailDo> detail;

	public List<CargoOutboundDetailDo> getDetail() {
		return detail;
	}

	public void setDetail(List<CargoOutboundDetailDo> detail) {
		this.detail = detail;
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

	public Integer getOutboundType() {
		return outboundType;
	}

	public void setOutboundType(Integer outboundType) {
		this.outboundType = outboundType;
	}

	public String getSourceNo() {
		return sourceNo;
	}

	public void setSourceNo(String sourceNo) {
		this.sourceNo = sourceNo == null ? null : sourceNo.trim();
	}

	public Date getOutboundTime() {
		return outboundTime;
	}

	public void setOutboundTime(Date outboundTime) {
		this.outboundTime = outboundTime;
	}

	public Date getSubTime() {
		return subTime;
	}

	public void setSubTime(Date subTime) {
		this.subTime = subTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks == null ? null : remarks.trim();
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

	public String getApplyDesc() {
		return applyDesc;
	}

	public void setApplyDesc(String applyDesc) {
		this.applyDesc = applyDesc == null ? null : applyDesc.trim();
	}

	public void save() throws Exception {
		repository.save(this);
		if (this.detail != null && this.detail.stream().count() > 0) {
			for (CargoOutboundDetailDo detailDo : detail) {
				detailDo.save();
			}
		}
	}

	public void update(int status, String desc) throws Exception {
		this.setStatus(status);
		if (StringUtils.isNotEmpty(desc)) {
			this.setApplyDesc(desc);
		}
		this.setOutboundTime(new Date());
		this.setSubTime(new Date());
		repository.updateOutbound(this);
	}
}