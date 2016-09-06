package com.club.web.store.vo;

import java.util.Date;

/**
* @Title: StoreLevelVo.java 
* @Package com.club.web.store.vo 
* @Description: 店铺等级Vo
* @author 柳伟军   
* @date 2016年3月26日 上午9:53:49 
* @version V1.0
 */
public class StoreLevelVo {
    private String levelId;

    private Long traId;
    
    private String headStoreName;

    private String name;

    private Long statue;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    private String storePro;

    public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public Long getTraId() {
        return traId;
    }

    public void setTraId(Long traId) {
        this.traId = traId;
    }

    public String getHeadStoreName() {
		return headStoreName;
	}

	public void setHeadStoreName(String headStoreName) {
		this.headStoreName = headStoreName;
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
}