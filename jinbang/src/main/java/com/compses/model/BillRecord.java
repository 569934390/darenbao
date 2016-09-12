package com.compses.model;

import java.util.Date;

/**
 账单记录表 Date:2016-08-23 00:31:26
 **/
public class BillRecord{

    /**主键id**/
    private String recordId;
        /**用户id**/
        private String payUserId;
        /**接收方用户角色   0-运营商，1-用户**/
        private String roleType;
        /**接收方用户id**/
        private String receivingUserId;
        /**订单id**/
        private String orderCode;
        /**交易金额**/
        private double tradingAmount;
        /**交易时间**/
        private Date tradingDate;
        /**结算时间**/
        private Date settlementDate;
        /**结算金额**/
        private double settlementMoney;
        /**备注**/
        private String remark;
        /**操作类型  1-交易，2-返佣，3-提现，-1-退款**/
        private String operationCategory;
        /**入账卡号**/
        private String enterCardId;
        /**操作状态**/
        private String operationStatus;
        /**提现时间**/
        private Date cashDate;
        /**余额宝到账时间**/
        private Date balanceDate;
        /**到账时间**/
        private Date cardDate;
        /**取现状态  0-申请中，1-到余额宝，2-往银行卡中，3-到账，-1-到余额宝失败，-2-银行卡转账失败**/
        private String cashStatus;
        /**结算状态   0-未结算，1-待结算，2-已结算**/
        private String settlementStatus;

    /*=================================================*/

    private String userName;

    public String getRecordId(){
        return recordId;
    }
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
    public String getPayUserId(){
        return payUserId;
    }
    public void setPayUserId(String payUserId) {
        this.payUserId = payUserId;
    }
    public String getRoleType(){
        return roleType;
    }
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
    public String getReceivingUserId(){
        return receivingUserId;
    }
    public void setReceivingUserId(String receivingUserId) {
        this.receivingUserId = receivingUserId;
    }
    public double getTradingAmount(){
        return tradingAmount;
    }
    public void setTradingAmount(double tradingAmount) {
        this.tradingAmount = tradingAmount;
    }
    public Date getTradingDate(){
        return tradingDate;
    }
    public void setTradingDate(Date tradingDate) {
        this.tradingDate = tradingDate;
    }
    public Date getSettlementDate(){
        return settlementDate;
    }
    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }
    public double getSettlementMoney(){
        return settlementMoney;
    }
    public void setSettlementMoney(double settlementMoney) {
        this.settlementMoney = settlementMoney;
    }
    public String getRemark(){
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOperationCategory() {
        return operationCategory;
    }

    public void setOperationCategory(String operationCategory) {
        this.operationCategory = operationCategory;
    }

    public String getEnterCardId() {
        return enterCardId;
    }

    public void setEnterCardId(String enterCardId) {
        this.enterCardId = enterCardId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(String operationStatus) {
        this.operationStatus = operationStatus;
    }

    public Date getCashDate() {
        return cashDate;
    }

    public void setCashDate(Date cashDate) {
        this.cashDate = cashDate;
    }

    public Date getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(Date balanceDate) {
        this.balanceDate = balanceDate;
    }

    public Date getCardDate() {
        return cardDate;
    }

    public void setCardDate(Date cardDate) {
        this.cardDate = cardDate;
    }

    public String getCashStatus() {
        return cashStatus;
    }

    public void setCashStatus(String cashStatus) {
        this.cashStatus = cashStatus;
    }

    public String getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(String settlementStatus) {
        this.settlementStatus = settlementStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
