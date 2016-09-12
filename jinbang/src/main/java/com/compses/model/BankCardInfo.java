package com.compses.model;



/**
 银行卡信息表 Date:2016-08-23 11:07:20
 **/
public class BankCardInfo{

    /**主键id**/
    private String cardId;
    /**用户id**/
    private String userId;
    /**持卡人姓名**/
    private String owner;
    /**身份证**/
    private String idCard;
    /**银行卡号**/
    private String cardNumber;
    /**银行卡类别**/
    private String cardCategory;
    /**手机号码**/
    private String mobile;
    /**每日限额**/
    private double limitAmount;

    public String getCardId(){
        return cardId;
    }
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getOwner(){
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public String getIdCard(){
        return idCard;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    public String getCardNumber(){
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public String getCardCategory(){
        return cardCategory;
    }
    public void setCardCategory(String cardCategory) {
        this.cardCategory = cardCategory;
    }
    public String getMobile(){
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public double getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(double limitAmount) {
        this.limitAmount = limitAmount;
    }
}
