package com.club.web.store.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.store.domain.repository.CarriageRuleRepository;

@Configurable
public class CarriageRuleDo {
	
	@Autowired
	CarriageRuleRepository carriageRuleRepository;
	
    private Long id;

    private String templateName;

    private BigDecimal carriage;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
    
    public int insert() {
    	
    	return carriageRuleRepository.insertCarriageRule(this);
	}

	public int update() {
		
		return carriageRuleRepository.updateCarriageRule(this);
	}
}