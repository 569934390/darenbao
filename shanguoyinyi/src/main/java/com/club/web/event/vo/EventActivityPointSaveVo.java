package com.club.web.event.vo;

import java.util.Date;

public class EventActivityPointSaveVo {
    private String id;

    private String eventActivityId;

    private String eventActivityUserinfo;

    private Date createTime;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEventActivityId() {
		return eventActivityId;
	}

	public void setEventActivityId(String eventActivityId) {
		this.eventActivityId = eventActivityId;
	}

	public String getEventActivityUserinfo() {
		return eventActivityUserinfo;
	}

	public void setEventActivityUserinfo(String eventActivityUserinfo) {
		this.eventActivityUserinfo = eventActivityUserinfo;
	}

	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}