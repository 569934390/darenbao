package com.club.web.store.domain;

import org.springframework.beans.factory.annotation.Configurable;

/**   
* @Title: RuleSourceDo.java
* @Package com.club.web.store.domain 
* @Description: 规则来源domain类 
* @author hqLin   
* @date 2016/03/30
* @version V1.0   
*/

@Configurable
public class RuleSourceDo {
    private Long id;

    private Integer ruleNumber;

    private String ruleName;

    private Integer ruleType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        this.ruleName = ruleName == null ? null : ruleName.trim();
    }

    public Integer getRuleType() {
        return ruleType;
    }

    public void setRuleType(Integer ruleType) {
        this.ruleType = ruleType;
    }
}