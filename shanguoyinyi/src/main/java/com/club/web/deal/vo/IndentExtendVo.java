package com.club.web.deal.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单Vo
 * 
 * @author wangqh
 *
 */
public class IndentExtendVo {
	private String id;
	private String name;
	private String shopId;
	private BigDecimal totalAmount;
	private BigDecimal paymentAmount;
	private BigDecimal carriage = new BigDecimal(0.00);
	private Date createTime;
	private int count;
	private String type;
	private String receiver;
	private String status;
	private String picUrl;
	private String buyerCarriage;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public BigDecimal getCarriage() {
		return carriage;
	}

	public void setCarriage(BigDecimal carriage) {
		this.carriage = carriage;
	}

	public String getBuyerCarriage() {
		return buyerCarriage;
	}

	public void setBuyerCarriage(String buyerCarriage) {
		this.buyerCarriage = buyerCarriage;
	}

}