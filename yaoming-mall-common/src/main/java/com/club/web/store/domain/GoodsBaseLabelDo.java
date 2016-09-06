package com.club.web.store.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.store.domain.repository.GoodsBaseLabelRepository;

/**   
* @Title: GoodsBaseLabelDo.java
* @Package com.club.web.store.domain 
* @Description: 商品基础标签domain类 
* @author hqLin   
* @date 2016/03/30
* @version V1.0   
*/

@Configurable
public class GoodsBaseLabelDo {
	
	@Autowired
	GoodsBaseLabelRepository goodsBaseLabelRepository;
	
    private Long id;

    private Long shopId;

    private String labelName;

    private Integer orderBy;

    private Integer status;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName == null ? null : labelName.trim();
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public int insert() {
    	
    	return goodsBaseLabelRepository.addGoodsBaseLabel(this);
	}

	public int update() {
		
		return goodsBaseLabelRepository.editGoodsBaseLabel(this);
	}
}