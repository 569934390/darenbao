package com.compses.dto;

import java.util.List;

public class OutputXML {
	private String result;
	private List<LinkInfo> linkInfoArray;
	private List<CertificateResult> certificateResultArray;
	private List<Station> stationArray;
	private List<LinkSetInfo> linkSetInfoArray;
	private WarnCableArray warnCableArray;
	private List<WarningInfo> warningInfoArray;

	private String errorMessage;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<LinkInfo> getLinkInfoArray() {
		return linkInfoArray;
	}

	public void setLinkInfoArray(List<LinkInfo> linkInfoArray) {
		this.linkInfoArray = linkInfoArray;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<CertificateResult> getCertificateResultArray() {
		return certificateResultArray;
	}

	public void setCertificateResultArray(
			List<CertificateResult> certificateResultArray) {
		this.certificateResultArray = certificateResultArray;
	}

	public List<Station> getStationArray() {
		return stationArray;
	}

	public void setStationArray(List<Station> stationArray) {
		this.stationArray = stationArray;
	}

	public List<LinkSetInfo> getLinkSetInfoArray() {
		return linkSetInfoArray;
	}

	public void setLinkSetInfoArray(List<LinkSetInfo> linkSetInfoArray) {
		this.linkSetInfoArray = linkSetInfoArray;
	}

	public WarnCableArray getWarnCableArray() {
		return warnCableArray;
	}

	public void setWarnCableArray(WarnCableArray warnCableArray) {
		this.warnCableArray = warnCableArray;
	}

	public List<WarningInfo> getWarningInfoArray() {
		return warningInfoArray;
	}

	public void setWarningInfoArray(List<WarningInfo> warningInfoArray) {
		this.warningInfoArray = warningInfoArray;
	}

}
