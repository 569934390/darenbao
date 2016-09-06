package com.club.web.store.dao.base.po;

import java.util.Date;

public class RuleValue {
    private Long id;

    private Long ruleId;

    private String ruleVal;

    private Long ruleId2;

    private Date ruleStarttime;

    private Date ruleEndtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleVal() {
        return ruleVal;
    }

    public void setRuleVal(String ruleVal) {
        this.ruleVal = ruleVal == null ? null : ruleVal.trim();
    }

    public Long getRuleId2() {
        return ruleId2;
    }

    public void setRuleId2(Long ruleId2) {
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