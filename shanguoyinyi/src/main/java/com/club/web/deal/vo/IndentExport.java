package com.club.web.deal.vo;

import java.util.List;

import com.club.framework.util.StringUtils;

/**
 * 订单导出Vo
 * 
 * @author zhuzd
 *
 */
public class IndentExport {

	private String name;

	private String receiver;

	private String paymentAmount;

	private String receiverPhone;

	private String province;

	private String city;

	private String town;

	private String address;

	private String buyerRemark;

	private String buyerCarriage;

	private Boolean needInvoice = false;

	private String invoiceName;

	private String invoiceContent;
	
	private String buyType;

	private List<IndentListExport> indentList;

	public String getFullAddress() {
		return (StringUtils.isNotEmpty(this.province) ? this.province : "")
				+ (StringUtils.isNotEmpty(this.city) ? this.city : "")
				+ (StringUtils.isNotEmpty(this.town) ? this.town : "")
				+ (StringUtils.isNotEmpty(this.address) ? this.address : "");
	}

	public String getNeedInvoiceText() {
		return this.needInvoice ? "是" : "否";
	}

	public Boolean getNeedInvoice() {
		return needInvoice;
	}

	public void setNeedInvoice(Boolean needInvoice) {
		this.needInvoice = needInvoice;
	}

	public String getInvoiceText() {
		if (this.needInvoice != null && this.needInvoice) {
			if (!"个人".equals(this.invoiceName)) {
				return this.invoiceContent;
			}
			return this.invoiceName;
		}
		return "";
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName == null ? null : invoiceName.trim();
	}

	public String getInvoiceContent() {
		return invoiceContent;
	}

	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent == null ? null : invoiceContent.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBuyerRemark() {
		return buyerRemark;
	}

	public void setBuyerRemark(String buyerRemark) {
		this.buyerRemark = buyerRemark;
	}

	public String getBuyerCarriage() {
		return buyerCarriage;
	}

	public void setBuyerCarriage(String buyerCarriage) {
		this.buyerCarriage = buyerCarriage;
	}

	public List<IndentListExport> getIndentList() {
		return indentList;
	}

	public void setIndentList(List<IndentListExport> indentList) {
		this.indentList = indentList;
	}
	public String getBuyType() {
		return buyType;
	}

	public void setBuyType(String buyType) {
		this.buyType = buyType;
	}
	
	public String getBuyTypeText() {
		return "s".equalsIgnoreCase(this.buyType)?"送人":"自用";
	}
}