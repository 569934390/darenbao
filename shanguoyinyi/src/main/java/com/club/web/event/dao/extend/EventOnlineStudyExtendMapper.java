package com.club.web.event.dao.extend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.club.core.common.Page;
import com.club.web.event.dao.base.EventOnlineStudyMapper;
import com.club.web.event.vo.EventOnlineStudyVo;

public interface EventOnlineStudyExtendMapper extends EventOnlineStudyMapper{

	List<Map<String, Object>> queryOnlineStudyPage(Map<String, Object> map);

	Long queryOnlineStudyCountPage(Map<String, Object> map);

	List<EventOnlineStudyVo> queryOnlineStudyVoByTitle(@Param("title")String title);

	EventOnlineStudyVo getEventOnlineStudyVoById(@Param("id")long id);
}