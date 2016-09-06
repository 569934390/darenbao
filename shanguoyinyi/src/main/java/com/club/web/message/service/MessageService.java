package com.club.web.message.service;

import java.util.Map;

import com.club.core.common.Page;
import com.club.web.message.vo.MessageContentPage;
import com.club.web.message.vo.NewsVo;

public interface MessageService {

	Page<Map<String, Object>> newsList(Page<Map<String, Object>> page);

	NewsVo addNewsMessage(String clientId,String storeId);
	
	void addNoticeMessage(String clientId, String storeId,Integer status, String content);

	Page<Map<String, Object>> noticeList(Page<Map<String, Object>> page);

	int messageCount();

	void updateStatusNoticeList(String messageId);

	MessageContentPage newsAdd(MessageContentPage content);

	int newsCount(String clientId, String storeId);

	void updateNewsReplay(String replyIds);

}
