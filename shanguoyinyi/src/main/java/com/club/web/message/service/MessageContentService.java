package com.club.web.message.service;

import java.util.List;
import java.util.Map;

import com.club.web.message.vo.MessageContentPage;

public interface MessageContentService {

	List<MessageContentPage> queryListByMap(Map<String,Object> con);

	MessageContentPage newsAdd(MessageContentPage content);
	
	void noticeAdd(Long messageId,Long senderId,String content);

	void updateContentStatus(Map<String, Object> con);

}
