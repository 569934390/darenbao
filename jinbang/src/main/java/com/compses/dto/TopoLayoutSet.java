package com.compses.dto;

public class TopoLayoutSet {
	/**
	 * 刷新频率
	 */
	private String refreshTime;
	/**
	 * 告警面板显示状态
	 */
	private boolean alarmPanelState;
	/**
	 * 图形面板显示状态
	 */
	private boolean chartPanelState;
	/**
	 * 属性面板显示状态
	 */
	private boolean propertyPanelState;
	
	public String getRefreshTime() {
		return refreshTime;
	}
	public void setRefreshTime(String refreshTime) {
		this.refreshTime = refreshTime;
	}
	public boolean isAlarmPanelState() {
		return alarmPanelState;
	}
	public void setAlarmPanelState(boolean alarmPanelState) {
		this.alarmPanelState = alarmPanelState;
	}
	public boolean isChartPanelState() {
		return chartPanelState;
	}
	public void setChartPanelState(boolean chartPanelState) {
		this.chartPanelState = chartPanelState;
	}
	public boolean isPropertyPanelState() {
		return propertyPanelState;
	}
	public void setPropertyPanelState(boolean propertyPanelState) {
		this.propertyPanelState = propertyPanelState;
	}
}
