package com.club.web.stock.vo;

import java.util.Date;

/**
* @Title: CargoBaseSkuItemVo.java 
* @Package com.club.web.stock.vo
* @Description: 规格类型选项Vo
* @author hqLin
* @date 2016/03/19
* @version V1.0
 */

public class CargoBaseSkuItemVo {
    private String id;

    private String code;

    private String baseSkuTypeId;

    private String value;

    private String name;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getBaseSkuTypeId() {
		return baseSkuTypeId;
	}

	public void setBaseSkuTypeId(String baseSkuTypeId) {
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