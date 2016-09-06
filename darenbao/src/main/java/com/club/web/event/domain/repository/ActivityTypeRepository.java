package com.club.web.event.domain.repository;

import java.util.List;
import java.util.Map;

import com.club.core.common.Page;
import com.club.web.event.domain.EventActivityTypeDo;
import com.club.web.event.vo.EventActivityTypeVo;

public interface ActivityTypeRepository {

	EventActivityTypeDo getEventActivityTypeDoById(long parseLong);

	EventActivityTypeDo create(EventActivityTypeVo eventActivityTypeVo);

	void insert(EventActivityTypeDo eventActivityTypeDo);

	void update(EventActivityTypeDo eventActivityTypeDo);

	List<EventActivityTypeVo> queryActivityTypeVoByName(String name);

	List<Map<String, Object>> queryActivityTypePage(Page<Map<String, Object>> page);

	Long queryActivityTypeCountPage(Page<Map<String, Object>> page);

	void delete(String id);

	List<EventActivityTypeVo> findListByStatus(Long status);

}
