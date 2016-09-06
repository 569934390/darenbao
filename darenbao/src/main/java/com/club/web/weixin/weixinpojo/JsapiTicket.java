package com.club.web.weixin.weixinpojo;

/**
* @Title: JsapiTicket.java 
* @Package com.club.web.client.pojo 
* @Description: TODO(微信JsapiTicket) 
* @author 柳伟军   
* @date 2016年4月16日 下午2:56:41 
* @version V1.0
 */
public class JsapiTicket {

	private String ticket;
	private int expires_in;
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public int getExpiresIn() {
		return expires_in;
	}
	
	public void setExpiresIn(int expires_in) {
		this.expires_in = expires_in;
	}
	
}
