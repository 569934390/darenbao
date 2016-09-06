package com.club.web.store.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.store.domain.repository.TimeCycleRepository;
@Configurable
public class TimeCycleDo {

	private Long id;

	private Integer duration;
	
	private Integer type;

	private Date updateTime;
	
	@Autowired
	private TimeCycleRepository repository; 
	
    public void update(){
    	repository.update(this);
    }
    public void add(){
    	repository.insert(this);
    }
    
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
}