package com.compses.dto;

/**
 * 自动发现的参数
 * 
 * @author Damen
 * @date 2013-10-23
 * 
 */
public class DetectParameter {
	private int protocol;
	// 扫描范围：1-起始Ip，结束ip，2-子网范围，3-单个ip
	private int scanArea;
	private String beginIpAddress;
	private String endIpAddress;
	private String ipAddress;
	private String subMask;

	private SnmpParameter snmpParameter;
	private IcmpParameter icmpParameter;

	public int getProtocol() {
		return protocol;
	}

	public void setProtocol(int protocol) {
		this.protocol = protocol;
	}

	public int getScanArea() {
		return scanArea;
	}

	public void setScanArea(int scanArea) {
		this.scanArea = scanArea;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getBeginIpAddress() {
		return beginIpAddress;
	}

	public void setBeginIpAddress(String beginIpAddress) {
		this.beginIpAddress = beginIpAddress;
	}

	public String getEndIpAddress() {
		return endIpAddress;
	}

	public void setEndIpAddress(String endIpAddress) {
		this.endIpAddress = endIpAddress;
	}

	public String getSubMask() {
		return subMask;
	}

	public void setSubMask(String subMask) {
		this.subMask = subMask;
	}

	public SnmpParameter getSnmpParameter() {
		return snmpParameter;
	}

	public void setSnmpParameter(SnmpParameter snmpParameter) {
		this.snmpParameter = snmpParameter;
	}

	public IcmpParameter getIcmpParameter() {
		return icmpParameter;
	}

	public void setIcmpParameter(IcmpParameter icmpParameter) {
		this.icmpParameter = icmpParameter;
	}

}
