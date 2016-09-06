package com.club.web.weixin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.framework.util.JsonUtil;
import com.club.web.event.vo.EventActivityUserinfoVo;
import com.club.web.weixin.config.Constants;
import com.club.web.weixin.config.WeixinGlobal;
import com.club.web.weixin.service.WeixinUserInfoService;
import com.club.web.weixin.util.AESUtil;
import com.club.web.weixin.vo.WeixinUserInfoVo;

/**
 * @Title: WeixinClientController.java
 * @Package com.club.web.client.controller
 * @Description: TODO(微信授权)
 * @author 柳伟军
 * @date 2016年4月16日 下午2:43:47
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/weixin/weixinClient")
public class WeixinClientController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WeixinUserInfoService weixinUserInfoService;

	// http://jansonweixin.ngrok.cc/shanguoyinyi/weixin/weixinClient/index.do?from=weixinfront/index.html&storeId=232547447116828672&type=weixinIndex

	/**
	 * 微信首页
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/index")
	public String index(String fromshanguo, String storeId, String type, String userId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if(storeId==null||"".equals(storeId)){
			storeId="0";
		}
		if ("weixinIndex".equals(type)) {
			WeixinUserInfoVo weixinUserInfoVo = (WeixinUserInfoVo) request.getSession()
					.getAttribute(Constants.WEIXIN_USERINFO_SESSION);
			if (weixinUserInfoVo != null)
				userId = weixinUserInfoVo.getId();
		} else if ("weixinEvent".equals(type)) {
			EventActivityUserinfoVo eventActivityUserinfoVo = (EventActivityUserinfoVo) request.getSession()
					.getAttribute(Constants.EVENT_ACTIVITY_USERINFO_SESSION);
			if (eventActivityUserinfoVo != null)
				userId = eventActivityUserinfoVo.getId();
		}
		if (fromshanguo.indexOf("?") > -1) {
			return "redirect:/" + fromshanguo + "&storeId=" + storeId + "&type=" + type + "&userId=" + userId;
		} else {
			return "redirect:/" + fromshanguo + "?storeId=" + storeId + "&type=" + type + "&userId=" + userId;
		}
	}

	/**
	 * 微信授权
	 */
	@RequestMapping(value = "/weixinIndex")
	public String weixinIndex(HttpServletRequest request, String code, String paramshanguo) {
		Map<String, Object> resule = null;
		String fromshanguo = "";
		String storeId = "";
		String type = "";
		String userId = "";
		try {
//			System.out.println(AESUtil.aesDecrypt(paramshanguo));
			resule = JsonUtil.toMap(AESUtil.aesDecrypt(paramshanguo));
			fromshanguo = resule.get("fromshanguo").toString();
			storeId = resule.get("storeId").toString();
			type = resule.get("type").toString();
			userId = weixinUserInfoService.weixinIndex(code, storeId, request);
		} catch (Exception e) {
			logger.error("WeixinClientController weixinIndex error : ", e.getMessage());
		}
		return "redirect:/weixin/weixinClient/index.do?fromshanguo=" + fromshanguo + "&storeId=" + storeId + "&type=" + type
				+ "&userId=" + userId;
	}

	/**
	 * 微信活动授权
	 */
	@RequestMapping(value = "/weixinEventIndex")
	public String weixinEventIndex(HttpServletRequest request, String activityId,String storeId) {
		Map<String, Object> resule = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("activityId", activityId);
			map.put("storeId", storeId);
			String param = JsonUtil.toJson(map).toString();
			System.out.println(param);
			param = AESUtil.aesEncrypt(param);
			String urlbegin = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeixinGlobal.getAppid()
					+ "&redirect_uri=" + WeixinGlobal.getWeixinUrl();
			String urlcontent = "/weixin/weixinClient/weixinEvent.do?param="+param;
			String urlend = "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
			System.out.println(urlbegin + urlcontent + urlend);
			return "redirect:"+urlbegin + urlcontent + urlend;
		} catch (Exception e) {
			logger.error("WeixinClientController weixinEventIndex error : ", e.getMessage());
		}
		return null;
	}

	/**
	 * 微信活动跳回前台
	 */
	@RequestMapping(value = "/weixinEvent")
	public String weixinEvent(HttpServletRequest request, String code,String param) {
		Map<String, Object> resule = null;
		String activityId = "";
		String storeId = "";
		String userId = "";
		try {
//			logger.error("WeixinClientController weixinEvent success param: {}",AESUtil.aesDecrypt(param));
			System.out.println(AESUtil.aesDecrypt(param));
			resule = JsonUtil.toMap(AESUtil.aesDecrypt(param));
			activityId = resule.get("activityId").toString();
			storeId = resule.get("storeId").toString();
			userId = weixinUserInfoService.weixinEvent(code, storeId, request);
			logger.error("WeixinClientController weixinEvent success userId: {}",userId);
			return "redirect:/www/index.html#/weixinActivityDetail/"+activityId+"/"+storeId+"/"+userId;
		} catch (Exception e) {
			logger.error("WeixinClientController weixinEvent error : {}", e.getMessage());
			return "redirect:/www/index.html#/weixinActivityDetail/"+activityId+"/"+storeId+"/"+userId;
		}
	}

	/**
	 * 微信端修改会员信息
	 * 
	 * @return
	 */
	@RequestMapping("/updateMyInfo")
	@ResponseBody
	public Map<String, Object> updateMyInfo(String modelJson, HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		logger.debug("weixin/updateMyInfo ");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (modelJson != null && !"".equals(modelJson)) {
				WeixinUserInfoVo weixinUserInfoVo = JsonUtil.toBean(modelJson, WeixinUserInfoVo.class);
				if (weixinUserInfoVo == null) {
					result.put("success", false);
					result.put("msg", "个人信息不能为空！");
				} else {
					result.putAll(weixinUserInfoService.updateMyInfo(weixinUserInfoVo, request));
				}
			} else {
				result.put("success", false);
				result.put("msg", "个人信息为空，请确认modelJson参数不为空，且为json格式");
			}
		} catch (Exception e) {
			logger.error("修改个人信息异常<updateMyInfo WeixinClientController>:{}", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
	}

	/**
	 * 微信端获取会员信息
	 * 
	 * @return
	 */
	@RequestMapping("/loadMyInfo")
	@ResponseBody
	public Map<String, Object> loadMyInfo(String id, HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		logger.debug("weixin/loadMyInfo ");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (id != null && !"".equals(id)) {
				result.put("success", true);
				result.put("msg", weixinUserInfoService.loadClientInfo(Long.parseLong(id)));
			} else {
				result.put("success", false);
				result.put("msg", "个人信息获取失败！");
			}
		} catch (Exception e) {
			logger.error("获取个人信息异常<loadMyInfo WeixinClientController>:{}", e);
			result.put("success", false);
			result.put("msg", "个人信息获取失败！");
		}
		return result;
	}
}
