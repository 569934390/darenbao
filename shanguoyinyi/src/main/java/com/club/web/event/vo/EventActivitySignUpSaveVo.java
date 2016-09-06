package com.club.web.event.vo;

import java.util.Date;

public class EventActivitySignUpSaveVo {
    private String id;

    private String eventActivityId;

    private String eventActivityUserinfo;

    private String tel;

    private Date createTime;

    private String note;

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

	public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}