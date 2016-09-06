package com.club.web.event.dao.extend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.club.web.event.dao.base.EventActivityCommentMapper;
import com.club.web.event.dao.base.po.EventActivityComment;
import com.club.web.event.vo.EventActivityCommentVo;

public interface EventActivityCommentExtendMapper extends EventActivityCommentMapper{

	List<Map<String, Object>> queryActivityCommentPage(Map<String, Object> map);

	Long queryActivityCommentCountPage(Map<String, Object> map);

	EventActivityCommentVo findEventActivityCommentVoByUserInfoAndEventActivityId(@Param("eventActivityId")String eventActivityId, @Param("userId")Long userId);

	List<EventActivityCommentVo> findByActivityId(@Param("eventActivityId")Long eventActivityId);
	
}