package com.club.web.store.vo;

import java.util.Date;

public class SettleTimeVo {
	 private String id;

	    private String settleDate;

	    private Date updateTime;

	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }

	    public String getSettleDate() {
	        return settleDate;
	    }

	    public void setSettleDate(String settleDate) {
	        this.settleDate = settleDate;
	    }

	    public Date getUpdateTime() {
	        return updateTime;
	    }

	    public void setUpdateTime(Date updateTime) {
	        this.updateTime = updateTime;
	    }
}