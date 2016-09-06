package com.club.web.weixin.weixinpojo;

/**
* @Title: AccessToken.java 
* @Package com.club.web.client.pojo 
* @Description: TODO(微信AccessToken) 
* @author 柳伟军   
* @date 2016年4月16日 下午2:56:28 
* @version V1.0
 */
public class AccessToken {
	private String token;
	private int expiresIn;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
}