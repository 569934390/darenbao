package com.club.web.event.domain.repository;

import java.util.List;
import java.util.Map;

import com.club.core.common.Page;
import com.club.web.event.domain.EventActivityDo;
import com.club.web.event.vo.EventActivityDetailsVo;
import com.club.web.event.vo.EventActivityMobileVo;
import com.club.web.event.vo.EventActivitySaveVo;

public interface EventActivityRepository {

	List<Map<String, Object>> queryEventActivityPage(Page<Map<String, Object>> page);

	Long queryEventActivityCountPage(Page<Map<String, Object>> page);

	EventActivityDo findById(Long id);

	void update(EventActivityDo eventActivityDo);

	void delete(Long id);

	EventActivityDo create(EventActivitySaveVo activitySaveVo);

	EventActivityDo getEventActivityDoById(long parseLong);

	void insert(EventActivityDo eventActivityDo);

	EventActivityDetailsVo findEventActivityById(String id);

	void updateActivityStatusByTime();

	Long queryEventActivityByTypeIds(String[] ids);

	EventActivityMobileVo findEventActivityByIdForMobile(String id,String userId);

//	List<EventActivityMobileVo> findEventActivityForMobile();

	List<Map<String, Object>> queryEventActivityFromMobilePage(Page<Map<String, Object>> page);

	Long queryEventActivityCountFromMobilePage(Page<Map<String, Object>> page);

	Long findRemainingNumberById(long id);

}
