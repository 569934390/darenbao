package com.club.web.stock.vo;

import java.util.Date;

/**
 * 出库单对象
 * 
 * @author wqh
 * @add by 2016-03-31
 */
public class CargoOutboundOrderVo {
	private String id;

	private Integer status;

	private Integer outboundType;

	private String sourceNo;

	private Date outboundTime;

	private Date subTime;

	private String remarks;

	private Date createTime;

	private String createBy;

	private String applyDesc;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getApplyDesc() {
		return applyDesc;
	}

	public void setApplyDesc(String applyDesc) {
		this.applyDesc = applyDesc == null ? null : applyDesc.trim();
	}
}