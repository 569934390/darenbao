package com.club.web.event.dao.base.po;

import java.util.Date;

public class EventActivitySignUp {
    private Long id;

    private Long eventActivityId;

    private Long eventActivityUserinfo;

    private String tel;

    private Date createTime;

    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventActivityId() {
        return eventActivityId;
    }

    public void setEventActivityId(Long eventActivityId) {
        this.eventActivityId = eventActivityId;
    }

    public Long getEventActivityUserinfo() {
        return eventActivityUserinfo;
    }

    public void setEventActivityUserinfo(Long eventActivityUserinfo) {
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