package com.compses.dto;

import java.util.Date;

public class DccMessageQuery {
	private String faultScenarios;
	private String billingNumber;
	private String messageQueryAmount;
	private Date startTime;
	private Date endTime;

	public String getFaultScenarios() {
		return faultScenarios;
	}

	public void setFaultScenarios(String faultScenarios) {
		this.faultScenarios = faultScenarios;
	}

	public String getBillingNumber() {
		return billingNumber;
	}

	public void setBillingNumber(String billingNumber) {
		this.billingNumber = billingNumber;
	}

	public String getMessageQueryAmount() {
		return messageQueryAmount;
	}

	public void setMessageQueryAmount(String messageQueryAmount) {
		this.messageQueryAmount = messageQueryAmount;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
