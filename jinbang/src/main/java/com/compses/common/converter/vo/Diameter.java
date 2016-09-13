package com.compses.common.converter.vo;


public class Diameter {
	private String version;
	
	private Integer messageLength;

	private String commandFlags;
	
	private String commandCode;
	
	private String applicationId;
	
	private String hopByHopIdentifier;
	
	private String endToEndIdentifier;
	 
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public Integer getMessageLength() {
		return messageLength;
	}
	public void setMessageLength(Integer messageLength) {
		this.messageLength = messageLength;
	}
	
	public String getCommandFlags() {
		return commandFlags;
	}
	public void setCommandFlags(String commandFlags) {
		this.commandFlags = commandFlags;
	}
	public String getCommandCode() {
		return commandCode;
	}
	public void setCommandCode(String commandCode) {
		this.commandCode = commandCode;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getHopByHopIdentifier() {
		return hopByHopIdentifier;
	}
	public void setHopByHopIdentifier(String hopByHopIdentifier) {
		this.hopByHopIdentifier = hopByHopIdentifier;
	}
	public String getEndToEndIdentifier() {
		return endToEndIdentifier;
	}
	public void setEndToEndIdentifier(String endToEndIdentifier) {
		this.endToEndIdentifier = endToEndIdentifier;
	}
}
