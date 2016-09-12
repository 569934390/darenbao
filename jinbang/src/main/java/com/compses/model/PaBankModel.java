package com.compses.model;

/**
 * Created by jocelynsuebb on 16/9/5.
 */
public class PaBankModel {

    private String userId;

    private String userName;

    private String cardNumber;

    private String mobile;

    private String channelType;

    private String umCode;

    private String returnUrl;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getUmCode() {
        return umCode;
    }

    public void setUmCode(String umCode) {
        this.umCode = umCode;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
