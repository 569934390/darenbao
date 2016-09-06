package com.club.web.store.domain;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.store.domain.repository.CarriageRuleDetailRepository;

@Configurable
public class CarriageRuleDetailDo {
	
	@Autowired
	CarriageRuleDetailRepository carriageRuleDetailRepository;
	
    private Long id;

    private Long carriageRuleId;

    private BigDecimal indentMoneyFull;

    private BigDecimal carriageFull;

    private BigDecimal carriageNotFull;

    private String deliverRegion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarriageRuleId() {
        return carriageRuleId;
    }

    public void setCarriageRuleId(Long carriageRuleId) {
        this.carriageRuleId = carriageRuleId;
    }

    public BigDecimal getIndentMoneyFull() {
        return indentMoneyFull;
    }

    public void setIndentMoneyFull(BigDecimal indentMoneyFull) {
        this.indentMoneyFull = indentMoneyFull;
    }

    public BigDecimal getCarriageFull() {
        return carriageFull;
    }

    public void setCarriageFull(BigDecimal carriageFull) {
        this.carriageFull = carriageFull;
    }

    public BigDecimal getCarriageNotFull() {
        return carriageNotFull;
    }

    public void setCarriageNotFull(BigDecimal carriageNotFull) {
        this.carriageNotFull = carriageNotFull;
    }

    public String getDeliverRegion() {
        return deliverRegion;
    }

    public void setDeliverRegion(String deliverRegion) {
        this.deliverRegion = deliverRegion == null ? null : deliverRegion.trim();
    }
    
    public int insert() {
    	
    	return carriageRuleDetailRepository.insertCarriageRuleDetail(this);
	}

	public int update() {
		
		return carriageRuleDetailRepository.updateCarriageRuleDetail(this);
	}
}