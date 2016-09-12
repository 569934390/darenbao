package com.compses.model;

import java.util.Date;

/**
 用户总账单表 Date:2016-08-23 00:30:18
 **/
public class TotalUserBill{

    /**主键id**/
    private String totalBillId;
    /**用户id**/
    private String userId;
    /**余额**/
    private double balance;
    /**收入总金额**/
    private double totalIncomeMoney;
    /**可转出金额**/
    private double transferableMoney;
    /**已结算金额**/
    private double settlementedMoney;
    /**待结算金额**/
    private double toSettlementMoney;
    /**创建时间**/
    private Date createTime;
    /**最后结算时间**/
    private Date lastSettlementTime;

    public String getTotalBillId(){
        return totalBillId;
    }
    public void setTotalBillId(String totalBillId) {
        this.totalBillId = totalBillId;
    }
    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public double getBalance(){
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public double getTotalIncomeMoney(){
        return totalIncomeMoney;
    }
    public void setTotalIncomeMoney(double totalIncomeMoney) {
        this.totalIncomeMoney = totalIncomeMoney;
    }
    public double getTransferableMoney(){
        return transferableMoney;
    }
    public void setTransferableMoney(double transferableMoney) {
        this.transferableMoney = transferableMoney;
    }
    public double getSettlementedMoney(){
        return settlementedMoney;
    }
    public void setSettlementedMoney(double settlementedMoney) {
        this.settlementedMoney = settlementedMoney;
    }
    public double getToSettlementMoney(){
        return toSettlementMoney;
    }
    public void setToSettlementMoney(double toSettlementMoney) {
        this.toSettlementMoney = toSettlementMoney;
    }
    public Date getCreateTime(){
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getLastSettlementTime(){
        return lastSettlementTime;
    }
    public void setLastSettlementTime(Date lastSettlementTime) {
        this.lastSettlementTime = lastSettlementTime;
    }
}
