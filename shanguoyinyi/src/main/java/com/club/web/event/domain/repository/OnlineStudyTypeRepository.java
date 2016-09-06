package com.club.web.event.domain.repository;

import java.util.List;
import java.util.Map;

import com.club.core.common.Page;
import com.club.web.event.domain.EventOnlineStudyTypeDo;
import com.club.web.event.vo.EventOnlineStudyTypeVo;

public interface OnlineStudyTypeRepository {

	List<EventOnlineStudyTypeVo> getVoByEventOnlineStudyTypeVo(EventOnlineStudyTypeVo eventOnlineStudyTypeVo);

	EventOnlineStudyTypeDo create(EventOnlineStudyTypeVo eventOnlineStudyTypeVo);

	void insert(EventOnlineStudyTypeDo eventOnlineStudyTypeDo);

	EventOnlineStudyTypeDo getEventOnlineStudyTypeDoById(long parseLong);

	void update(EventOnlineStudyTypeDo eventOnlineStudyTypeDo);

	List<EventOnlineStudyTypeDo> getEventOnlineStudyTypeDoByParentId(Long parentId);

	void delete(Long id);

	List<EventOnlineStudyTypeVo> getVoByName(String name);

}
