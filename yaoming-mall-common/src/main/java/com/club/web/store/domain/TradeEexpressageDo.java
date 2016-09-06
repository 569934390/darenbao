package com.club.web.store.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.store.domain.repository.TradeEexpressageRepository;
import com.club.web.util.IdGenerator;
@Configurable
public class TradeEexpressageDo {
	
	@Autowired
	TradeEexpressageRepository tradeEexpressageRepository;
	
    private Long id;

    private String logoUrl;

    private String name;

    private String number;

    private String officialWebsiteUrl;

    private Integer state;

    private Date createTime;

    private Date updateTime;

    private Long creator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl == null ? null : logoUrl.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getOfficialWebsiteUrl() {
        return officialWebsiteUrl;
    }

    public void setOfficialWebsiteUrl(String officialWebsiteUrl) {
        this.officialWebsiteUrl = officialWebsiteUrl == null ? null : officialWebsiteUrl.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }
    public int update(){
    	int result=0;
    	this.updateTime=new Date();
    	result=tradeEexpressageRepository.update(this);
    	return result;
    }
    public int insert(){
    	int result=0;
    	this.createTime=new Date();
    	this.updateTime=new Date();
    	this.id = IdGenerator.getDefault().nextId();
    	result=tradeEexpressageRepository.insert(this);
    	return result;
    }
    public int updateState(){
    	int result=0;
    	this.updateTime=new Date();
    	result=tradeEexpressageRepository.updateState(this);
    	return result;
    }
    public int delet(){
    	int result=0;
    	result=tradeEexpressageRepository.delet(this);
    	return result;
    }
}