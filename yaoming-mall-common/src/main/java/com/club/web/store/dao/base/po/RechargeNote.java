package com.club.web.store.dao.base.po;

import java.math.BigDecimal;
import java.util.Date;

public class RechargeNote {
    private Long indentId;

    private BigDecimal paymentAmount;

    private Integer payType;

    private Integer itemCode;

    private String buyerClient;

    private String buyerAccount;

    private Date createDate;

    private String state;

    public Long getIndentId() {
        return indentId;
    }

    public void setIndentId(Long indentId) {
        this.indentId = indentId;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getItemCode() {
        return itemCode;
    }

    public void setItemCode(Integer itemCode) {
        this.itemCode = itemCode;
    }

    public String getBuyerClient() {
        return buyerClient;
    }

    public void setBuyerClient(String buyerClient) {
        this.buyerClient = buyerClient == null ? null : buyerClient.trim();
    }

    public String getBuyerAccount() {
        return buyerAccount;
    }

    public void setBuyerAccount(String buyerAccount) {
        this.buyerAccount = buyerAccount == null ? null : buyerAccount.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}