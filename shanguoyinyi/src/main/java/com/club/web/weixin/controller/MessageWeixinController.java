package com.club.web.weixin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.framework.util.JsonUtil;
import com.club.web.message.service.MessageContentService;
import com.club.web.message.service.MessageService;
import com.club.web.message.vo.MessageContentPage;
import com.club.web.message.vo.NewsVo;

/**
 * 信息微信控制类
 * 
 * @author zhuzd
 *
 */
@Controller
@RequestMapping("/weixin/message")
public class MessageWeixinController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MessageContentService messageContentService;
	
	@Autowired
	private MessageService messageService;
	
	/**
	 * 获取消息列表
	 * /weixin/message/news/contents.do?id=111&startIndex=0&limit=10
		id  messageId
		startIndex 初始下标  默认为0
		limit   查询个数  默认为10 
		返回  List  消息内容列表
	 * @param id
	 * @param startIndex
	 * @param limit
	 * @param response
	 * @return
	 */
	@RequestMapping("/news/contents")
	@ResponseBody
	public List<MessageContentPage> newsDetail(String id,Integer startIndex,Integer limit, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try {
			Map<String, Object> con = new HashMap<>();
			con.put("messageId", id);
			con.put("startIndex", startIndex != null ? startIndex:0);
			con.put("limit", limit != null?limit:10);
			return messageContentService.queryListByMap(con);
		} catch (Exception e) {
			logger.error("newsDetail error:", e);
		}
		return new ArrayList<>();
	}


	/**
	 * 根据用户id与店铺id获取信息数据，如果没有则新增
	 * /weixin/message/news/queryMessage.do?clientId=111&storeId=111
		clientId  用户id
		storeId  店铺id
		返回message对象 或 null
	 * @param modelJson
	 * @param response
	 * @return
	 */
	@RequestMapping("/news/queryMessage")
	@ResponseBody
	public NewsVo queryMessage(String clientId,String storeId, HttpServletResponse response) {
		logger.debug("message queryMessage ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try {
			return messageService.addNewsMessage(clientId, storeId);
		} catch (Exception e) {
			logger.error("queryMessage error:", e);
		}
		return null;
	}
	
	/**
	 * 根据用户id与店铺id获取已回复信息数量
		clientId  用户id
		storeId  店铺id
	 * @param modelJson
	 * @param response
	 * @return
	 */
	@RequestMapping("/news/count")
	@ResponseBody
	public int newsCount(String clientId,String storeId, HttpServletResponse response) {
		logger.debug("message weixin newsCount");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try {
			return messageService.newsCount(clientId, storeId);
		} catch (Exception e) {
			logger.error("message weixin newsCount error:", e);
		}
		return 0;
	}
	
	@RequestMapping("/news/add")
	@ResponseBody
	public Map<String, Object> newsAdd(String modelJson, HttpServletResponse response) {
		logger.debug("message newsAdd ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String, Object> result = new HashMap<>();
		result.put("code", 1);
		try {
			MessageContentPage content = JsonUtil.toBean(modelJson, MessageContentPage.class);
			result.put("msg", messageService.newsAdd(content));
		} catch (Exception e) {
			logger.error("newsAdd error:", e);
			result.put("code", 0);
			result.put("msg", "回复失败！");
		}
		return result;
	}
}
