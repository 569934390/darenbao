package com.club.web.event.dao.base.po;

import java.util.Date;

public class EventActivityPoint {
    private Long id;

    private Long eventActivityId;

    private Long eventActivityUserinfo;

    private Date createTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}