package com.club.web.store.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.util.Assert;

import com.club.web.store.domain.repository.SalesReturnReasonRepository;
import com.club.web.util.CommonUtil;
import com.club.web.util.IdGenerator;

@Configurable
public class SalesReturnReasonDo {
	@Autowired
	SalesReturnReasonRepository salesReturnReasonRepository;
    private Long id;

    private String reason;

    private String rank;

    private Date updateTime;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
    	try {
			int i=Integer.parseInt(rank);
		} catch (Exception e) {
			Assert.isTrue(e.getMessage()==null, "排序号必须填数字");
		}
        this.rank = rank == null ? null : rank.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public int insert(){
    	
    	this.id = IdGenerator.getDefault().nextId();
    	this.setCreateTime(new Date());
    	int result=salesReturnReasonRepository.insert(this);
		return result;
    }
    public int update(){
    	
    	this.setUpdateTime(new Date());
    	int result=salesReturnReasonRepository.update(this);
    	
		return result;
    }
     public int delet(){
    	
    	int result=salesReturnReasonRepository.delet(this);
    	
		return result;
    }
}