package com.club.web.store.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.store.domain.repository.GoodsColumnRepository;

/**   
* @Title: GoodsColumnDo.java
* @Package com.club.web.store.domain 
* @Description: 商品基础栏目domain类 
* @author hqLin   
* @date 2016/03/30
* @version V1.0   
*/

@Configurable
public class GoodsColumnDo {
	
	@Autowired
	GoodsColumnRepository goodsColumnRepository;
	
	private Long id;

    private Long shopId;

    private String columnName;

    private Long ruleSourceId;

    private Integer orderBy;

    private Integer status;

    private String showyn;

    private String showpicture;

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

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName == null ? null : columnName.trim();
    }

    public Long getRuleSourceId() {
        return ruleSourceId;
    }

    public void setRuleSourceId(Long ruleSourceId) {
        this.ruleSourceId = ruleSourceId;
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

    public String getShowyn() {
        return showyn;
    }

    public void setShowyn(String showyn) {
        this.showyn = showyn == null ? null : showyn.trim();
    }

    public String getShowpicture() {
        return showpicture;
    }

    public void setShowpicture(String showpicture) {
        this.showpicture = showpicture == null ? null : showpicture.trim();
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
    	
    	return goodsColumnRepository.addGoodsColumn(this);
	}

	public int update() {
		
		return goodsColumnRepository.editGoodsColumn(this);
	}
}