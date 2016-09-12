package com.compses.common.converter.vo;


public class Dcc {
	private String key;
	private String id;
	private String name;
	private String connSend;
	private String autoSend;
	private String timerSend;
	private String disconnSend;
	private String disableLog;
	private Request request;
	private Response response;
	private String parentId;
	
	
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
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
	public String getConnSend() {
		return connSend;
	}
	public void setConnSend(String connSend) {
		this.connSend = connSend;
	}
	
	public String getAutoSend() {
		return autoSend;
	}
	public void setAutoSend(String autoSend) {
		this.autoSend = autoSend;
	}
	public String getTimerSend() {
		return timerSend;
	}
	public void setTimerSend(String timerSend) {
		this.timerSend = timerSend;
	}
	
	public String getDisconnSend() {
		return disconnSend;
	}
	public void setDisconnSend(String disconnSend) {
		this.disconnSend = disconnSend;
	}
	public String getDisableLog() {
		return disableLog;
	}
	public void setDisableLog(String disableLog) {
		this.disableLog = disableLog;
	}
	public Request getRequest() {
		return request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
	public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}
	 
}
