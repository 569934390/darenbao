package com.compses.dto;

import com.compses.model.CashDetails;

import java.util.List;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class CashDetailsDto {

    /**银行卡号**/
    private String cardNumber;
    /**银行卡类别**/
    private String cardCategory;
    /**取现明细记录**/
    private List<CashDetails> cashDetailsList;
    /**交易金额**/
    private double amountPrice;
    /**交易状态**/
    private String detailsStatus;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardCategory() {
        return cardCategory;
    }

    public void setCardCategory(String cardCategory) {
        this.cardCategory = cardCategory;
    }

    public List<CashDetails> getCashDetailsList() {
        return cashDetailsList;
    }

    public void setCashDetailsList(List<CashDetails> cashDetailsList) {
        this.cashDetailsList = cashDetailsList;
    }

    public double getAmountPrice() {
        return amountPrice;
    }

    public void setAmountPrice(double amountPrice) {
        this.amountPrice = amountPrice;
    }

    public String getDetailsStatus() {
        return detailsStatus;
    }

    public void setDetailsStatus(String detailsStatus) {
        this.detailsStatus = detailsStatus;
    }
}


