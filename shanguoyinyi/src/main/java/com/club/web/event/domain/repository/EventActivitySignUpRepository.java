package com.club.web.event.domain.repository;

import java.util.List;
import java.util.Map;

import com.club.core.common.Page;
import com.club.web.event.domain.EventActivitySignUpDo;
import com.club.web.event.vo.EventActivitySignUpExportVo;
import com.club.web.event.vo.EventActivitySignUpSaveVo;
import com.club.web.event.vo.EventActivitySignUpVo;

public interface EventActivitySignUpRepository {

	List<Map<String, Object>> queryActivityUserPage(Page<Map<String, Object>> page);

	Long queryActivityUserCountPage(Page<Map<String, Object>> page);

	EventActivitySignUpDo create(EventActivitySignUpSaveVo activitySignUpVo);

	void insert(EventActivitySignUpDo eventActivitySignUpDo);

	EventActivitySignUpVo findEventActivitySignUpVoByUserInfoAndEventActivityId(String activityId,Long userId);

	List<EventActivitySignUpVo> findByActivityId(Long eventActivityId);

	void delete(EventActivitySignUpDo eventActivitySignUpDo);

	List<EventActivitySignUpExportVo> selectExportData(Map map);

}
