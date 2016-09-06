package com.club.web.message.dao.extend;

import java.util.List;
import java.util.Map;

import com.club.web.message.dao.base.MessageContentMapper;
import com.club.web.message.vo.MessageContentPage;

public interface MessageContentExtendMapper extends MessageContentMapper{

	List<MessageContentPage> queryListByMap(Map<String,Object> con);
	
	int deleteByBeforeTime(Map<String, Object> con);
	
	int updateContentStatus(Map<String, Object> con);

	void updateNewsReplay(List<String> replyIds);

}
