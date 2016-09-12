package com.compses.model;

/**
 * Created by jocelynsuebb on 16/9/9.
 */


public class PaBankZhuanZhangModel {

    private String userId;

    //转账凭证号-标示交易唯一性，同一客户上送的不可重复，建议格式：yyyymmddHHSS+8位系列
    private String thirdVoucher;

    //货币类型RMB-人民币
    private String ccyCode;

    //付款人账户-扣款账户
    private String outAcctNo;

    //付款人名称
    private String outAcctName;

    //收款人账户
    private String inAcctNo;

    //收款人账户户名
    private String inAcctName;

    //收款人开户行名称-建议格式：xxx银行
    private String inAcctBankName;

    //转出金额-如为XML报文，则直接输入输出以元为单位的浮点数值，如2.50 (两元五角)
    private String tranAmount;

    //行内跨行标志-1：行内转账，0：跨行转账
    private String unionFlag;

    //同城/异地标志-“1”—同城   “2”—异地；若无法区分，可默认送1-同城
    private String addrFlag;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getThirdVoucher() {
        return thirdVoucher;
    }

    public void setThirdVoucher(String thirdVoucher) {
        this.thirdVoucher = thirdVoucher;
    }

    public String getCcyCode() {
        return ccyCode;
    }

    public void setCcyCode(String ccyCode) {
        this.ccyCode = ccyCode;
    }

    public String getOutAcctName() {
        return outAcctName;
    }

    public void setOutAcctName(String outAcctName) {
        this.outAcctName = outAcctName;
    }

    public String getOutAcctNo() {
        return outAcctNo;
    }

    public void setOutAcctNo(String outAcctNo) {
        this.outAcctNo = outAcctNo;
    }

    public String getInAcctBankName() {
        return inAcctBankName;
    }

    public void setInAcctBankName(String inAcctBankName) {
        this.inAcctBankName = inAcctBankName;
    }

    public String getInAcctName() {
        return inAcctName;
    }

    public void setInAcctName(String inAcctName) {
        this.inAcctName = inAcctName;
    }

    public String getInAcctNo() {
        return inAcctNo;
    }

    public void setInAcctNo(String inAcctNo) {
        this.inAcctNo = inAcctNo;
    }

    public String getTranAmount() {
        return tranAmount;
    }

    public void setTranAmount(String tranAmount) {
        this.tranAmount = tranAmount;
    }

    public String getUnionFlag() {
        return unionFlag;
    }

    public void setUnionFlag(String unionFlag) {
        this.unionFlag = unionFlag;
    }

    public String getAddrFlag() {
        return addrFlag;
    }

    public void setAddrFlag(String addrFlag) {
        this.addrFlag = addrFlag;
    }

//    <?xml version="1.0" encoding="GB2312"?>
//      <Result>
//         <ThirdVoucher>20100811153405</ThirdVoucher>
//         <CcyCode>RMB</CcyCode>
//         <OutAcctNo>11002873403401</OutAcctNo>
//         <OutAcctName>ebt</OutAcctName>
//         <OutAcctAddr/>
//         <InAcctBankNode/>
//         <InAcctRecCode/>
//         <InAcctNo>11002873390701</InAcctNo>
//         <InAcctName>EBANK</InAcctName>
//         <InAcctBankName>anything</InAcctBankName>
//         <TranAmount>000.01</TranAmount>
//         <AmountCode/>
//         <UseEx>testreturn</UseEx>
//         <UnionFlag>1</UnionFlag>
//         <SysFlag>2</SysFlag>
//         <AddrFlag>1</AddrFlag>
//         <RealFlag>2</RealFlag>
//         <MainAcctNo/>
//      </Result>
}
