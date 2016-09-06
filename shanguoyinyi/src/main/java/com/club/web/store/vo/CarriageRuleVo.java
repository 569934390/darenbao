package com.club.web.store.vo;

import java.math.BigDecimal;
import java.util.Date;

public class CarriageRuleVo {
    private String id;

    private String templateName;	//模板名称

    private BigDecimal carriage;	//默认运费

    private Date updateTime;		//更新时间
    
    private int useGoodsNum;		//使用商品个数

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName == null ? null : templateName.trim();
    }

    public BigDecimal getCarriage() {
		return carriage;
	}

	public void setCarriage(BigDecimal carriage) {
		this.carriage = carriage;
	}

	public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public int getUseGoodsNum() {
		return useGoodsNum;
	}

	public void setUseGoodsNum(int useGoodsNum) {
		this.useGoodsNum = useGoodsNum;
	}
}