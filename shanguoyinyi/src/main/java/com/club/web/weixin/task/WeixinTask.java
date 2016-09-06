package com.club.web.weixin.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.club.web.weixin.config.WeixinGlobal;
import com.club.web.weixin.util.AdvancedUtil;
import com.club.web.weixin.weixinpojo.AccessToken;
import com.club.web.weixin.weixinpojo.JsapiTicket;

/**
* @Title: WeixinTask.java 
* @Package com.club.web.weixin.task 
* @Description: TODO(微信定时器) 
* @author 柳伟军   
* @date 2016年5月12日 下午2:11:46 
* @version V1.0
 */
@Component 
public class WeixinTask {

	/**
	 * 日志对象
	 */
	protected Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * 核心处理流程[定时器] 每隔2小时执行执行 更新Token
	 */
	@Scheduled(fixedRate = 2*60*60*1000)
	public void updateToken() {

		AccessToken accessToken = AdvancedUtil.getAccessToken(WeixinGlobal.getAppid(), WeixinGlobal.getAppsecret());
		JsapiTicket jsapiTicket = AdvancedUtil.getJsapiTicket(accessToken);
		if (null != accessToken) {
			log.info(" 获取access_token成功，有效时长{}秒 token:{}", accessToken.getExpiresIn(), accessToken.getToken());
			log.info(" 获取jsapiTicket成功，有效时长{}秒 Ticket:{}", jsapiTicket.getExpiresIn(), jsapiTicket.getTicket());
			WeixinGlobal.accessToken = accessToken.getToken();
			WeixinGlobal.jsapiTicket = jsapiTicket.getTicket();
		}
	}
}
