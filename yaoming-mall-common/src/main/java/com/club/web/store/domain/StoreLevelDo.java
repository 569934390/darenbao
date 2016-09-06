package com.club.web.store.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.store.domain.repository.StoreLevelRepository;
/**
* @Title: StoreLevelDo.java 
* @Package com.club.web.store.domain 
* @Description: TODO等级DO
* @author 柳伟军   
* @date 2016年4月6日 上午11:32:13 
* @version V1.0
 */
@Configurable
public class StoreLevelDo {
	
	@Autowired
	private StoreLevelRepository storeLevelRepository;
	
    private Long levelId;

    private Long traId;

    private String name;

    private Long statue;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    private String storePro;

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public Long getTraId() {
        return traId;
    }

    public void setTraId(Long traId) {
        this.traId = traId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getStatue() {
        return statue;
    }

    public void setStatue(Long statue) {
        this.statue = statue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public String getStorePro() {
        return storePro;
    }

    public void setStorePro(String storePro) {
        this.storePro = storePro == null ? null : storePro.trim();
    }
    
	public void insert() {
		storeLevelRepository.insert(this);
	}

	public void update() {
		storeLevelRepository.update(this);
	}
}