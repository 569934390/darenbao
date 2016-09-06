package com.club.web.store.vo;

import java.math.BigDecimal;
import java.util.Date;

/**   
* @Title: MerchantBillVo.java
* @Package com.club.web.store.vo
* @Description: 结算账单VO 
* @author hqLin 
* @date 2016/05/04
* @version V1.0   
*/
public class MerchantBillVo {
	
    private String id;	//ID
    private String indentName;	//订单号
    private String shopName;//店铺名称
    private Date finishTime;	//订单完成时间
    private String cycle;	//结算周期
    private Date settlementTime;//可结算时间
    private BigDecimal paymentAmount;//支付金额
    private BigDecimal supplyPrice;//结算金额
    private int status;	//结算单状态

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getIndentName() {
		return indentName;
	}

	public void setIndentName(String indentName) {
		this.indentName = indentName;
	}
}