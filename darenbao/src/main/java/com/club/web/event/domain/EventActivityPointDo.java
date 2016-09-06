package com.club.web.event.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.event.domain.repository.EventActivityPointRepository;

@Configurable
public class EventActivityPointDo {
	
	@Autowired EventActivityPointRepository eventActivityPointRepository;
	
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

	public void insert() {
		eventActivityPointRepository.insert(this);		
	}

	public void delete() {
		eventActivityPointRepository.delete(this);		
	}
}