package com.club.web.client.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.web.weixin.service.WeixinUserInfoService;

/**
* @Title: WeixinFansController.java 
* @Package com.club.web.client.controller 
* @Description: TODO(微信粉丝管理) 
* @author 柳伟军   
* @date 2016年4月18日 上午10:07:15 
* @version V1.0
 */
@Controller
@RequestMapping(value = "/client/weixinFans")
public class WeixinFansController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WeixinUserInfoService weixinUserInfoService;
	
	/**
	 * @param page
	 *            前台的分页信息
	 * @param conditionStr
	 *            查询条件（json格式）
	 * @return
	 */
	@RequestMapping("/weixinFansPage")
	@ResponseBody
	public Page<Map<String, Object>> weixinFansPage(Page<Map<String, Object>> page, String conditionStr,
			HttpServletResponse response) {

		// 如果查询条件不为空则添加查询条件
		if (conditionStr != null) {
			page.setConditons(JsonUtil.toMap(conditionStr));
		}
		// 根据查询分页信息和查询查询分页结果
		page = weixinUserInfoService.queryWeixinFansPage(page);
		return page;
	}
}
