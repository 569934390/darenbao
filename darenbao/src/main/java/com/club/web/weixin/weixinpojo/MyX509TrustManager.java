package com.club.web.weixin.weixinpojo;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * @Title: MyX509TrustManager.java
 * @Package com.club.web.client.pojo
 * @Description: TODO(微信信任管理器)
 * @author 柳伟军
 * @date 2016年4月16日 下午2:56:56
 * @version V1.0
 */
public class MyX509TrustManager implements X509TrustManager {

	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}