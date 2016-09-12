package com.compses.model;



/**
 用户-银行卡密码表 Date:2016-08-23 15:04:41
 **/
public class UserCardPwd{

    /**主键id**/
    private String relaId;
    /**用户id**/
    private String userId;
    /**银行卡密码**/
    private String bankPwd;

    public String getRelaId(){
        return relaId;
    }
    public void setRelaId(String relaId) {
        this.relaId = relaId;
    }
    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getBankPwd(){
        return bankPwd;
    }
    public void setBankPwd(String bankPwd) {
        this.bankPwd = bankPwd;
    }
}
