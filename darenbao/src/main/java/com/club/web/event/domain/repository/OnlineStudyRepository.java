package com.club.web.event.domain.repository;

import java.util.List;
import java.util.Map;

import com.club.core.common.Page;
import com.club.web.event.domain.EventOnlineStudyDo;
import com.club.web.event.vo.EventOnlineStudyVo;

public interface OnlineStudyRepository {

	EventOnlineStudyDo create(EventOnlineStudyVo eventOnlineStudyVo);
	
	void update(EventOnlineStudyDo eventOnlineStudyDo);

	void insert(EventOnlineStudyDo eventOnlineStudyDo);

	List<Map<String, Object>> queryOnlineStudyPage(Page<Map<String, Object>> page);

	Long queryOnlineStudyCountPage(Page<Map<String, Object>> page);

	List<EventOnlineStudyVo> queryOnlineStudyVoByTitle(String title);

	EventOnlineStudyDo getEventOnlineStudyDoById(long parseLong);

	void delete(EventOnlineStudyDo eventOnlineStudyDo);

	EventOnlineStudyVo getEventOnlineStudyVoById(long parseLong);

	
}
