package com.compses.dto;

import java.util.List;

public class InputXML {
	private List<LinkInfo> linkInfoArray;

	private String preQueryTime;

	private List<DeviceInfo> deviceInfoArray;

	private List<WarningInfo> warningInfoArray;

	public List<LinkInfo> getLinkInfoArray() {
		return linkInfoArray;
	}

	public void setLinkInfoArray(List<LinkInfo> linkInfoArray) {
		this.linkInfoArray = linkInfoArray;
	}

	public List<DeviceInfo> getDeviceInfoArray() {
		return deviceInfoArray;
	}

	public void setDeviceInfoArray(List<DeviceInfo> deviceInfoArray) {
		this.deviceInfoArray = deviceInfoArray;
	}

	public String getPreQueryTime() {
		return preQueryTime;
	}

	public void setPreQueryTime(String preQueryTime) {
		this.preQueryTime = preQueryTime;
	}

	public List<WarningInfo> getWarningInfoArray() {
		return warningInfoArray;
	}

	public void setWarningInfoArray(List<WarningInfo> warningInfoArray) {
		this.warningInfoArray = warningInfoArray;
	}

}
