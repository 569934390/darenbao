package com.compses.dto;
/**
 * 使用icmp的参数
 * 
 * @author Damen
 * @date 2013-10-23
 *
 */
public class IcmpParameter {
	private long timeout;
	private int repeatTime;
	public long getTimeout() {
		return timeout;
	}
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	public int getRepeatTime() {
		return repeatTime;
	}
	public void setRepeatTime(int repeatTime) {
		this.repeatTime = repeatTime;
	}
	

}
