package com.club.web.store.dao.base.po;

import java.util.Date;

public class BankCard {
	private Long bankCardId;

	private Integer state;

	private String name;

	private String bankName;

	private String mobile;

	private Date updateTime;

	private Date createTime;

	private Integer delet;

	private Long connectId;

	private Integer connectType;

	private String bankAddress;

	private String idCard;

	private String bankCard;

	public Long getBankCardId() {
		return bankCardId;
	}

	public void setBankCardId(Long bankCardId) {
		this.bankCardId = bankCardId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard == null ? null : idCard.trim();
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName == null ? null : bankName.trim();
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard == null ? null : bankCard.trim();
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDelet() {
		return delet;
	}

	public void setDelet(Integer delet) {
		this.delet = delet;
	}

	public Long getConnectId() {
		return connectId;
	}

	public void setConnectId(Long connectId) {
		this.connectId = connectId;
	}

	public Integer getConnectType() {
		return connectType;
	}

	public void setConnectType(Integer connectType) {
		this.connectType = connectType;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress == null ? null : bankAddress.trim();
	}
}