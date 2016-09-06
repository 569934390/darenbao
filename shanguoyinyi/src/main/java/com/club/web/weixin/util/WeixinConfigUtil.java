package com.club.web.weixin.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.club.web.weixin.config.WeixinGlobal;
import com.club.web.weixin.weixinpojo.AccessToken;

/**
 * @Title: WeixinShareUtil.java
 * @Package com.club.web.weixin.util
 * @Description: TODO(微信Config Url验证)
 * @author 柳伟军
 * @date 2016年5月13日 上午9:56:03
 * @version V1.0
 */
@Component
@PropertySource("classpath:/config/weixin.properties")
public class WeixinConfigUtil {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${appid}")
	private String appId;
	@Value("${appsecret}")
	private String appsecret;
	@Value("${weixinUrl}")
	private String weixinUrl;

	/**
	 * 验证
	 * 
	 * @param url
	 * @return
	 */
	public Map<String, Object> verificationUrl(String url) {
		logger.error("WeixinConfigUtil verificationUrl url {}",url);
		Map<String, Object> ret = Sha1Util.checkSignatureforurl(WeixinGlobal.jsapiTicket, url,
				Sha1Util.getNonceStr(), Sha1Util.getTimeStamp());
		ret.put("appId", appId);
		return ret;
	}
	
	/**
	 * 验证
	 * 
	 * @param url
	 * @return
	 */
	public Map<String, Object> verification(String url) {
		logger.error("WeixinConfigUtil verification url {}",url);
		Map<String, Object> ret = Sha1Util.checkSignatureforurl(WeixinGlobal.jsapiTicket, weixinUrl + "/" + url,
				Sha1Util.getNonceStr(), Sha1Util.getTimeStamp());
		ret.put("appId", appId);
		return ret;
	}

	public Map<String, Object> getToken() {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			AccessToken token = AdvancedUtil.getAccessToken(appId, appsecret);
			result.put("success", true);
			result.put("msg", token);
			return result;
		} catch (Exception e) {
			logger.error("WeixinConfigUtil getToken error {}",e.getMessage());
			result.put("success", false);
			result.put("msg", "获取失败！");
			return result;
		}
	}
}
