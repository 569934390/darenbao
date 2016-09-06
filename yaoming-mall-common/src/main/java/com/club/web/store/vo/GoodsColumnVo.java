package com.club.web.store.vo;

import java.util.Date;

/**   
* @Title: GoodsColumnVo.java
* @Package com.club.web.store.vo 
* @Description: 商品基础栏目VO 
* @author hqLin   
* @date 2016/03/30
* @version V1.0   
*/
public class GoodsColumnVo {
    private String id;

    private String shopId;

    private String columnName;

    private String ruleSourceId;

    private Integer orderBy;

    private Integer status;

    private String showyn;

    private String showpicture;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;
    
    private String ShopFlag;
    
    private String ruleId;
    
    private String valueId;
    
    private String ruleVal;
    
    private String ruleId2;
    
    private Date ruleStarttime;

    private Date ruleEndtime;
    
    private String sourceId;
    
    private Integer ruleNumber;
    
    private String ruleName;
    
    private String showpictureId;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getRuleSourceId() {
		return ruleSourceId;
	}

	public void setRuleSourceId(String ruleSourceId) {
		this.ruleSourceId = ruleSourceId;
	}

	public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName == null ? null : columnName.trim();
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

	public String getShopFlag() {
		return ShopFlag;
	}

	public void setShopFlag(String shopFlag) {
		ShopFlag = shopFlag;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getValueId() {
		return valueId;
	}

	public void setValueId(String valueId) {
		this.valueId = valueId;
	}

	public String getRuleVal() {
		return ruleVal;
	}

	public void setRuleVal(String ruleVal) {
		this.ruleVal = ruleVal;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public Integer getRuleNumber() {
		return ruleNumber;
	}

	public void setRuleNumber(Integer ruleNumber) {
		this.ruleNumber = ruleNumber;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getShowpictureId() {
		return showpictureId;
	}

	public void setShowpictureId(String showpictureId) {
		this.showpictureId = showpictureId;
	}

	public String getRuleId2() {
		return ruleId2;
	}

	public void setRuleId2(String ruleId2) {
		this.ruleId2 = ruleId2;
	}

	public Date getRuleStarttime() {
        return ruleStarttime;
    }

    public void setRuleStarttime(Date ruleStarttime) {
        this.ruleStarttime = ruleStarttime;
    }

    public Date getRuleEndtime() {
        return ruleEndtime;
    }

    public void setRuleEndtime(Date ruleEndtime) {
        this.ruleEndtime = ruleEndtime;
    }
}