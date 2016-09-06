package com.club.framework.vo;

/**
 * 防暴力攻击的过滤器VO
 * @author WUWY
 *
 */
public class SecurityFilterVo {
	//请求URL后缀
	private String urlPostfix;
	//有限时间:-1:不限制
	private int limitTime = -1;
	//限制次数:99999999:不限制
	private int limitCount = 99999999;
	
	public String getUrlPostfix() {
		return urlPostfix;
	}
	public void setUrlPostfix(String urlPostfix) {
		this.urlPostfix = urlPostfix;
	}
	public int getLimitTime() {
		return limitTime;
	}
	public void setLimitTime(int limitTime) {
		this.limitTime = limitTime;
	}
	public int getLimitCount() {
		return limitCount;
	}
	public void setLimitCount(int limitCount) {
		this.limitCount = limitCount;
	}
}
