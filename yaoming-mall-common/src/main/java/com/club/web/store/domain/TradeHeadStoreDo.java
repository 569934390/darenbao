package com.club.web.store.domain;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.store.domain.repository.TradeHeadStoreRepository;
/**
* @Title: TradeHeadStoreDo.java 
* @Package com.club.web.store.domain 
* @Description: TODO总店DO
* @author 柳伟军   
* @date 2016年4月6日 上午11:31:53 
* @version V1.0
 */
@Configurable
public class TradeHeadStoreDo {
	

	private Logger logger = LoggerFactory.getLogger(TradeHeadStoreDo.class);
	
	@Autowired
	TradeHeadStoreRepository tradeHeadStoreRepository;

	private Long id;

    private String name;

    private String tel;

    private Long statue;

    private Long owner;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public Long getStatue() {
		return statue;
	}

	public void setStatue(Long statue) {
		this.statue = statue;
	}

	public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
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
    
	public void insert() {
		tradeHeadStoreRepository.insert(this);
	}

	public void update() {
		tradeHeadStoreRepository.update(this);
	}
}
