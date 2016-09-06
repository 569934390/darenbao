package com.club.web.deal.vo;

import java.util.Date;

import com.club.web.deal.constant.IndentStatus;

/**
 * 订单Vo
 * 
 * @author zhuzd
 *
 */
public class InvoiceVo {

	private String indentName;

	private String receiver;

	private String receiverPhone;

	private Date createTime;

	private Integer number;

	private String invoiceName;

	private String invoiceContent;
	
	private Integer indentStatus;
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone == null ? null : receiverPhone.trim();
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver == null ? null : receiver.trim();
	}

	public String getInvoiceText() {
		if (!"个人".equals(this.invoiceName)) {
			return this.invoiceContent;
		}
		return this.invoiceName;
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

	public String getIndentName() {
		return indentName;
	}

	public void setIndentName(String indentName) {
		this.indentName = indentName;
	}
	
	public String getStatusName() {
		if (this.indentStatus != null) {
			return IndentStatus.getTextByDbData(this.indentStatus);
		}
		return "";
	}

	public Integer getIndentStatus() {
		return indentStatus;
	}

	public void setIndentStatus(Integer indentStatus) {
		this.indentStatus = indentStatus;
	}
}