package com.compses.dto;

/**
 * 使用snmp协议的参数
 * 
 * @author Damen
 * @date 2013-10-23
 * 
 */
public class SnmpParameter {
	private int protocolVersion;
	private int port;
	private long timeout;
	private int repeateTime;
	private String readCom;
	private String writeCom;
	private String nodeTypeIds;
	private String systemObjectIds;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public int getRepeateTime() {
		return repeateTime;
	}

	public void setRepeateTime(int repeateTime) {
		this.repeateTime = repeateTime;
	}

	public String getReadCom() {
		return readCom;
	}

	public void setReadCom(String readCom) {
		this.readCom = readCom;
	}

	public String getWriteCom() {
		return writeCom;
	}

	public void setWriteCom(String writeCom) {
		this.writeCom = writeCom;
	}

	public int getProtocolVersion() {
		return protocolVersion;
	}

	public void setProtocolVersion(int protocolVersion) {
		this.protocolVersion = protocolVersion;
	}

	public String getNodeTypeIds() {
		return nodeTypeIds;
	}

	public void setNodeTypeIds(String nodeTypeIds) {
		this.nodeTypeIds = nodeTypeIds;
	}

	public String getSystemObjectIds() {
		return systemObjectIds;
	}

	public void setSystemObjectIds(String systemObjectIds) {
		this.systemObjectIds = systemObjectIds;
	}
}
