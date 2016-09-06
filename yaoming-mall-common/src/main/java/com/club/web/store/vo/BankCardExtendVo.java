package com.club.web.store.vo;

import com.club.web.util.CommonUtil;

public class BankCardExtendVo {

	private String id;
	private String name;
	private String card;
	private String bankName;
	private String bankCard;
	private String mobile;
	private String shopId;
	private String bankAddress;
	private String cardShow;

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

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getCardShow() {
		return CommonUtil.getHideIdCardVal(getCard(), 5, 4, 4);
	}

	public void setCardShow(String cardShow) {
		this.cardShow = cardShow;
	}
}