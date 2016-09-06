package com.club.web.weixin.interceptors;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.club.framework.util.JsonUtil;
import com.club.web.event.vo.EventActivityUserinfoVo;
import com.club.web.weixin.config.Constants;
import com.club.web.weixin.config.WeixinGlobal;
import com.club.web.weixin.util.AESUtil;
import com.club.web.weixin.vo.WeixinUserInfoVo;

/**
 * @Title: WeixinInterceptor.java
 * @Package com.club.web.mobile.interceptors
 * @Description: TODO(微信拦截器)
 * @author 柳伟军
 * @date 2016年4月23日 上午11:34:06
 * @version V1.0
 */
public class WeixinInterceptor implements HandlerInterceptor {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		logger.info("微信地址拦截 url : {}", request.getRequestURI());
		logger.info("微信地址拦截 model : {}", request.getMethod());

		String fromshanguo = request.getParameter("fromshanguo");
		String storeId = request.getParameter("storeId");
		if(storeId==null||"".equals(storeId)){
			storeId="0";
		}
		String type = request.getParameter("type");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fromshanguo", fromshanguo);
		map.put("storeId", storeId);
		map.put("type", type);
		
		logger.info("微信地址拦截 参数 : {}", JsonUtil.toJson(map));
		if ("weixinIndex".equals(type)) {
			WeixinUserInfoVo weixinUserInfoVo = (WeixinUserInfoVo) request.getSession()
					.getAttribute(Constants.WEIXIN_USERINFO_SESSION);
			if (weixinUserInfoVo == null) {
				logger.info("推广入口，还没登录，被拦截！");
				String param = JsonUtil.toJson(map).toString();
//				System.out.println(param);
				param = AESUtil.aesEncrypt(param);
				String urlbegin = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeixinGlobal.getAppid()
						+ "&redirect_uri=" + WeixinGlobal.getWeixinUrl();
				String urlcontent = "/weixin/weixinClient/" + type + ".do?paramshanguo=" + param;
				String urlend = "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
//				System.out.println(urlbegin + urlcontent + urlend);
				response.sendRedirect(urlbegin + urlcontent + urlend);
				return false;
			}
		} 
//		else if ("weixinEvent".equals(type)) {
//			EventActivityUserinfoVo eventActivityUserinfoVo = (EventActivityUserinfoVo) request.getSession()
//					.getAttribute(Constants.EVENT_ACTIVITY_USERINFO_SESSION);
//			if (eventActivityUserinfoVo == null) {
//				logger.info("活动入口，还没登录，被拦截！");
//				String param = JsonUtil.toJson(map).toString();
//				System.out.println(param);
//				param = AESUtil.aesEncrypt(param);
//				String urlbegin = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeixinGlobal.getAppid()
//						+ "&redirect_uri=" + WeixinGlobal.getWeixinUrl();
//				String urlcontent = "/weixin/weixinClient/" + type + ".do?param=" + param;
//				String urlend = "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
//				System.out.println(urlbegin + urlcontent + urlend);
//				response.sendRedirect(urlbegin + urlcontent + urlend);
//				return false;
//			}
//		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
