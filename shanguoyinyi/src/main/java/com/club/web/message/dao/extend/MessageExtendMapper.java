package com.club.web.message.dao.extend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.club.web.message.dao.base.MessageMapper;
import com.club.web.message.vo.MessageNoticePage;
import com.club.web.message.vo.MessagePage;
import com.club.web.message.vo.NewsStat;
import com.club.web.message.vo.NewsVo;

public interface MessageExtendMapper extends MessageMapper{

	int queryTotalByMap(Map<String, Object> con);

	List<MessagePage> queryListByMap(Map<String, Object> con);

	NewsStat queryNewsStat();
	
	int queryNoticeCount(@Param("contentStatus")Integer contentStatus);
	
	int queryNoticeCoutByMap(Map<String, Object> con);
	
	List<MessageNoticePage> queryNoticeList(Map<String, Object> con);

	int updateStatusNoticeListByMessageId(@Param("messageId")Long messageId);

	NewsVo queryNewsVoList(Map<String, Object> con);
	
}
