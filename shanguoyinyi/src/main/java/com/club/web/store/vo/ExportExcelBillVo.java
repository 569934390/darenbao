package com.club.web.store.vo;

import java.math.BigDecimal;
import java.util.Date;

/**   
* @Title: ExportExcelBillVo.java
* @Package com.club.web.store.vo
* @Description: 导出Excel账单VO,导出结算账单excel专用，非excel的内容，请勿增删属性
* @author hqLin 
* @date 2016/05/09
* @version V1.0   
*/
public class ExportExcelBillVo {
	
	private String shopName;//店铺名称
	private String name;//姓名
	private String bankCard;//银行卡号
	private String bankName;//银行名称
	private String bankAddress;//开户行
	private String goodName;//商品名称
	private int number;//商品个数
    private Date payTime;	//支付时间
    private String cycle;	//结算周期
    private Date settlementTime;//可结算时间
    private BigDecimal paymentAmount;//支付金额
    private BigDecimal carriage;//运费
    private BigDecimal supplyPrice;//结算金额
    private String payStatus;	//付款状态

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public Date getSettlementTime() {
		return settlementTime;
	}

	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public BigDecimal getSupplyPrice() {
		return supplyPrice;
	}

	public void setSupplyPrice(BigDecimal supplyPrice) {
		this.supplyPrice = supplyPrice;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public BigDecimal getCarriage() {
		return carriage;
	}

	public void setCarriage(BigDecimal carriage) {
		this.carriage = carriage;
	}
}