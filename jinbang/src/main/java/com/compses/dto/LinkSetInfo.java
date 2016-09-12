package com.compses.dto;

import java.util.List;

public class LinkSetInfo {
	private String deviceIDA;
	private String deviceTermA;
	private String deviceIDB;
	private String deviceTermB;
	private Long linkOutID;
	private List<PassDeviceTermList> passDeviceTermListArray;

	public String getDeviceIDA() {
		return deviceIDA;
	}

	public void setDeviceIDA(String deviceIDA) {
		this.deviceIDA = deviceIDA;
	}

	public String getDeviceTermA() {
		return deviceTermA;
	}

	public void setDeviceTermA(String deviceTermA) {
		this.deviceTermA = deviceTermA;
	}

	public String getDeviceIDB() {
		return deviceIDB;
	}

	public void setDeviceIDB(String deviceIDB) {
		this.deviceIDB = deviceIDB;
	}

	public String getDeviceTermB() {
		return deviceTermB;
	}

	public void setDeviceTermB(String deviceTermB) {
		this.deviceTermB = deviceTermB;
	}

	public Long getLinkOutID() {
		return linkOutID;
	}

	public void setLinkOutID(Long linkOutID) {
		this.linkOutID = linkOutID;
	}

	public List<PassDeviceTermList> getPassDeviceTermListArray() {
		return passDeviceTermListArray;
	}

	public void setPassDeviceTermListArray(
			List<PassDeviceTermList> passDeviceTermListArray) {
		this.passDeviceTermListArray = passDeviceTermListArray;
	}

}
