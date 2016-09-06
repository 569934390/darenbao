package com.club.web.message.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.framework.util.ListUtils;
import com.club.framework.util.StringUtils;
import com.club.web.message.constant.MessageStatus;
import com.club.web.message.constant.MessageType;
import com.club.web.message.dao.extend.MessageContentExtendMapper;
import com.club.web.message.dao.extend.MessageExtendMapper;
import com.club.web.message.domain.MessageDo;
import com.club.web.message.service.MessageContentService;
import com.club.web.message.service.MessageService;
import com.club.web.message.vo.MessageContentPage;
import com.club.web.message.vo.MessageNoticePage;
import com.club.web.message.vo.MessagePage;
import com.club.web.message.vo.NewsStat;
import com.club.web.message.vo.NewsVo;
import com.club.web.util.CommonUtil;
import com.club.web.util.IdGenerator;
import com.club.web.webSocket.WebSocketManager;
/**
 * 信息服务接口实现类
 * @author zhuzd
 *
 */
@Service
public class MessageServiceImpl implements MessageService{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired private MessageExtendMapper messageDao;
	
	@Autowired private MessageContentExtendMapper messageContentDao;
	
	@Autowired private MessageContentService messageContentService;
	
	@Autowired private WebSocketManager websocketManager;

	@Override
	public int messageCount() {
		NewsStat newsStat = messageDao.queryNewsStat();
		int newsCount = newsStat.getNewsCount();
		int noticeCount = messageDao.queryNoticeCount(0);
		return newsCount > 0 ?noticeCount+1:noticeCount;
	}

	@Override
	public void updateStatusNoticeList(String messageId) {
		messageDao.updateStatusNoticeListByMessageId(Long.valueOf(messageId));
	}
	
	public Page<Map<String, Object>> newsList(Page<Map<String, Object>> page){
		int startIndex = 0;
		int pageSize = 10;
		int total = 0;
		List<MessagePage> list = null;
		List<Map<String, Object>> listMap = null;
		if (page != null) {
			startIndex = page.getStart();
			pageSize = page.getLimit();
			Map<String, Object> con = page.getConditons();
			con.put("messageType", "1");
			total = messageDao.queryTotalByMap(con);
			page.setTotalRecords(total);
			if(total > 0){
				con.put("startIndex", startIndex);
				con.put("pageSize", pageSize);
				list = messageDao.queryListByMap(con);
				listMap = CommonUtil.listObjTransToListMap(list);
				if (listMap != null && listMap.size() > 0) {
					page.setResultList(listMap);
				}
			}
		}
		return page;
	}

	@Override
	public Page<Map<String, Object>> noticeList(Page<Map<String, Object>> page) {
		int startIndex = 0;
		int pageSize = 10;
		int total = 0;
		int newsCount = 0;
		Date newsTime = null;
		List<MessageNoticePage> list = new ArrayList<>();
		List<Map<String, Object>> listMap = null;
		if (page != null) {
			startIndex = page.getStart();
			pageSize = page.getLimit();
			Map<String, Object> con = new HashMap<>();
			if(startIndex == 0){
				NewsStat newsMap = messageDao.queryNewsStat();
				newsCount = newsMap.getNewsCount();
				newsTime = newsMap.getNewsTime();
			}
			total += newsCount + messageDao.queryNoticeCount(null);
			page.setTotalRecords(total);
			if(total > 0){
				if(newsCount > 0 && startIndex == 0){
					pageSize--;
					MessageNoticePage noticePage = new MessageNoticePage();
					noticePage.setContent("[客服]你有"+newsCount+"条消息未回复，请点击查看！");
					noticePage.setContentTime(newsTime);
					noticePage.setType(MessageType.消息.getDbData());
					list.add(noticePage);
				}
				con.put("startIndex", startIndex);
				con.put("pageSize", pageSize);
				list.addAll(messageDao.queryNoticeList(con));
				listMap = CommonUtil.listObjTransToListMap(list);
				if (listMap != null && listMap.size() > 0) {
					page.setResultList(listMap);
				}
			}
		}
		return page;
	}
	
	@Override
	public void addNoticeMessage(String clientId, String storeId,Integer status, String content) {
		try{
			Map<String, Object> con = new HashMap<>();
			con.put("status", status);
			con.put("clientId", clientId);
			con.put("storeId", storeId);
			int noticeCount = messageDao.queryNoticeCoutByMap(con);
			if(noticeCount  == 0){
				MessageDo result = new MessageDo();
				result.setId(IdGenerator.getDefault().nextId());
				result.setClientId(Long.valueOf(clientId));
				result.setStoreId(Long.valueOf(storeId));
				result.setType(MessageType.通知.getDbData());
				result.setStatus(status);
				result.newsAdd();
				messageContentService.noticeAdd(result.getId(), result.getClientId(), content);
				websocketManager.send(messageCount());
			}
		}catch(Exception e){
			logger.error("addNoticeMessage error:",e);
		}
	}
	
	@Override
	public NewsVo addNewsMessage(String clientId,String storeId) {
		Map<String, Object> con = new HashMap<>();
		con.put("clientId", clientId);
		con.put("storeId", storeId);
		con.put("operateType", "client");
		messageContentDao.updateContentStatus(con);
		NewsVo newsVo = messageDao.queryNewsVoList(con);
		if(newsVo != null){
			if(ListUtils.isNotEmpty(newsVo.getContents()) && StringUtils.isEmpty(newsVo.getContents().get(0).getId())){
				newsVo.setContents(null);
			}
			return newsVo;
		}
		NewsVo result = new NewsVo();
		result.setId(IdGenerator.getDefault().nextId()+"");
		result.setClientId(clientId);
		result.setStoreId(storeId);
		result.setType(MessageType.消息.getDbData());
		result.setStatus(MessageStatus.消息回复.getDbData());
		getDoByVo(result).newsAdd();
		con.put("messageId", result.getId());
		newsVo = messageDao.queryNewsVoList(con);
		if(newsVo != null){
			if(ListUtils.isNotEmpty(newsVo.getContents()) && StringUtils.isEmpty(newsVo.getContents().get(0).getId())){
				newsVo.setContents(null);
			}
			return newsVo;
		}
		return result;
	}

	private MessageDo getDoByVo(NewsVo src) {
		MessageDo target = null;
		if(src != null){
			target = new MessageDo();
			target.setId(Long.valueOf(src.getId()));
			target.setClientId(Long.valueOf(src.getClientId()));
			target.setStatus(src.getStatus());
			target.setStoreId(Long.valueOf(src.getStoreId()));
			target.setType(src.getType());
		}
		return target;
	}

	@Override
	public MessageContentPage newsAdd(MessageContentPage content) {
		MessageContentPage result = messageContentService.newsAdd(content);
		websocketManager.send(messageCount());
		return result;
	}

	@Override
	public int newsCount(String clientId, String storeId) {
		Map<String, Object> con = new HashMap<>();
		con.put("messageType", 1);
		con.put("clientId", clientId);
		con.put("storeId", storeId);	
		con.put("replyStatus", 1);	
		con.put("operateType", 1);	
		return messageDao.queryTotalByMap(con);
	}

	@Override
	public void updateNewsReplay(String replyIds) {
		messageContentDao.updateNewsReplay(ListUtils.strToStrList(replyIds));
	}
}
