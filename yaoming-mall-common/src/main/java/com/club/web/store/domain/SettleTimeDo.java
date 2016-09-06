package com.club.web.store.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.store.dao.extend.SettleTimeExtendMapper;
import com.club.web.store.domain.repository.SettleTimeRepository;
import com.club.web.util.IdGenerator;
@Configurable
public class SettleTimeDo {
	@Autowired
	SettleTimeRepository settleTimeRepository; 
	 private Long id;

	    private Integer settleDate;

	    private Date updateTime;

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public Integer getSettleDate() {
	        return settleDate;
	    }

	    public void setSettleDate(Integer settleDate) {
	        this.settleDate = settleDate;
	    }

	   

	   
    public int update(){
    	int result=0;
    	this.setUpdateTime(new Date());
    	result=settleTimeRepository.update(this);
    	return result;
    }
    public int insert(){
    	int result=0;
    	this.setUpdateTime(new Date());
    	this.id = IdGenerator.getDefault().nextId();
    	result=settleTimeRepository.insert(this);
    	return result;
    }

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}