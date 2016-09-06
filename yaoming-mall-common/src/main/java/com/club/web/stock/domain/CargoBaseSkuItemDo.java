package com.club.web.stock.domain;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.stock.domain.repository.CargoBaseSkuItemRepository;

/**   
* @Title: CargoBaseSkuItemDo.java
* @Package com.club.web.stock.domain
* @Description: 商品基础规格选项domain类 
* @author hqLin   
* @date 2016/03/19
* @version V1.0   
*/

@Configurable
public class CargoBaseSkuItemDo {
	private Logger logger = LoggerFactory.getLogger(CargoBaseSkuItemDo.class);
	
	@Autowired
	CargoBaseSkuItemRepository repository;
	
	private Long id;

    private String code;

    private Long baseSkuTypeId;

    private String value;

    private String name;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Long getBaseSkuTypeId() {
        return baseSkuTypeId;
    }

    public void setBaseSkuTypeId(Long baseSkuTypeId) {
        this.baseSkuTypeId = baseSkuTypeId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
}