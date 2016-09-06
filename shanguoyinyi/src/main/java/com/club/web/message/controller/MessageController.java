package com.club.web.message.controller;

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

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;
import com.club.web.message.service.MessageContentService;
import com.club.web.message.service.MessageService;
import com.club.web.message.vo.MessageContentPage;

/**
 * 信息控制类
 * 
 * @author zhuzd
 *
 */
@Controller
@RequestMapping("/message")
public class MessageController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private MessageContentService messageContentService;
	

	@RequestMapping("/news/list")
	@ResponseBody
	public Page<Map<String, Object>> newsList(Page<Map<String, Object>> page, String conditionStr) {
		logger.debug("newsList list ");
		try {
			if (StringUtils.isNotEmpty(conditionStr) && page != null) {
				page.setConditons(JsonUtil.toMap(conditionStr));
			}
			page = messageService.newsList(page);
		} catch (Exception e) {
			logger.error("newsList list error:", e);
		}
		return page;
	}
	
	@RequestMapping("/count")
	@ResponseBody
	public int messageCount() {
		logger.debug("messageCount");
		try {
			return messageService.messageCount();
		} catch (Exception e) {
			logger.error("messageCount error:", e);
		}
		return 0;
	}
	
	@RequestMapping("/notice/list")
	@ResponseBody
	public Page<Map<String, Object>> noticeList(Page<Map<String, Object>> page) {
		logger.debug("noticeList list ");
		try {
			page = messageService.noticeList(page);
		} catch (Exception e) {
			logger.error("noticeList list error:", e);
		}
		return page;
	}
	
	@RequestMapping("/notice/updateStatus")
	@ResponseBody
	public Map<String, Object> updateStatusNoticeList(String messageId) {
		logger.debug("updateStatusNoticeList ");
		Map<String, Object> result = new HashMap<>();
		result.put("code", 1);
		try {
			messageService.updateStatusNoticeList(messageId);
		} catch (Exception e) {
			result.put("code", 0);
			result.put("msg", "更新通知已读状态失败！");
			logger.error("updateStatusNoticeList error:", e);
		}
		return result;
	}
	
	@RequestMapping("/news/contents")
	@ResponseBody
	public List<MessageContentPage> newsDetail(String messageId,Integer startIndex,Integer limit, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try {
			Map<String, Object> con = new HashMap<>();
			con.put("messageId", messageId);
			con.put("startIndex", startIndex != null ? startIndex:0);
			con.put("limit", limit != null?limit:10);
			con.put("operateType", "store");
			messageContentService.updateContentStatus(con);
			return messageContentService.queryListByMap(con);
		} catch (Exception e) {
			logger.error("newsDetail error:", e);
		}
		return new ArrayList<>();
	}


	/**
	 * 添加消息
	 * 
	 * type：通知:0,消息:1
	 * sendType：发送类型 0:用户 1:客服
	 * @param modelJson={"messageId":"1","senderId":"248474721296064512","content":"66666","type":0,"sendType":0}
	 * @param response
	 * @return
	 */
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
	
	/**
	 * 设置已回复状态
	 * @return
	 */
	@RequestMapping("/news/replay")
	@ResponseBody
	public Map<String, Object> updateNewsReplay(String replyIds, HttpServletResponse response) {
		logger.debug("message newsReplay ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String, Object> result = new HashMap<>();
		result.put("code", 1);
		try {
			messageService.updateNewsReplay(replyIds);
		} catch (Exception e) {
			logger.error("newsAdd error:", e);
			result.put("code", 0);
			result.put("msg", "设置已回复状态失败！");
		}
		return result;
	}
}
