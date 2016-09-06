package com.club.web.stock.domain;

import java.util.Date;

public abstract class BaseDo {

    private Date createTime;
    private long createBy;
    private Date updateTime;
    private long updateBy;
    
	public Date getCreateTime() {
		return createTime;
	}
	public long getCreateBy() {
		return createBy;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public void setCreateBy(long createBy) {
		this.createBy = createBy;
		setCreateTime(new Date());
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public long getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(long updateBy) {
		this.updateBy = updateBy;
		setUpdateTime(new Date());
	}
    
}
