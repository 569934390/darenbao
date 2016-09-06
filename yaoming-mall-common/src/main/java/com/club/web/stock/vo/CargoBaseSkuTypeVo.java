package com.club.web.stock.vo;

import java.util.Date;

/**
* @Title: CargoBaseSkuTypeVo.java 
* @Package com.club.web.stock.vo
* @Description: 规格类型Vo
* @author hqLin
* @date 2016/03/19
* @version V1.0
 */

public class CargoBaseSkuTypeVo {
    private String id;

    private Integer type;

    private String name;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;
    
    private String typeName;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}