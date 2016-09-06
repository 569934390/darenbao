package com.club.web.store.vo;

/**   
* @Title: RuleSourceVo.java
* @Package com.club.web.store.vo 
* @Description: 规则来源VO 
* @author hqLin   
* @date 2016/03/30
* @version V1.0   
*/
public class RuleSourceVo {
    private String id;

    private Integer ruleNumber;

    private String ruleName;

    private Integer ruleType;

    public String getId() {
		return id;
	}

	public void setId(String id) {
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