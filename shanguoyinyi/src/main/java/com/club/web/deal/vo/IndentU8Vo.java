package com.club.web.deal.vo;

import java.math.BigDecimal;

/**
 * 订单U8Vo
 *
 */
public class IndentU8Vo extends IndentVo{
	
	String shopCode;
	
	String shopName;
	
	String departmentCode;
	
	BigDecimal amount;

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}