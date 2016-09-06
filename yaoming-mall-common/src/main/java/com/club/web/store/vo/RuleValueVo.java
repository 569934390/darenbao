package com.club.web.store.vo;

import java.util.Date;

/**   
* @Title: RuleValueVo.java
* @Package com.club.web.store.vo 
* @Description: 规则值VO 
* @author hqLin   
* @date 2016/03/30
* @version V1.0   
*/
public class RuleValueVo {
    private String id;

    private String ruleId;

    private String ruleVal;
    
    private String ruleId2;

    private Date ruleStarttime;

    private Date ruleEndtime;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleVal() {
        return ruleVal;
    }

    public void setRuleVal(String ruleVal) {
        this.ruleVal = ruleVal == null ? null : ruleVal.trim();
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