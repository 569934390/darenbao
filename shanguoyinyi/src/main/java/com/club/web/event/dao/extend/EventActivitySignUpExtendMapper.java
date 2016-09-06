package com.club.web.event.dao.extend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.club.web.event.dao.base.EventActivitySignUpMapper;
import com.club.web.event.vo.EventActivitySignUpExportVo;
import com.club.web.event.vo.EventActivitySignUpVo;

public interface EventActivitySignUpExtendMapper extends EventActivitySignUpMapper{

	List<Map<String, Object>> queryActivityUserPage(Map<String, Object> map);

	Long queryActivityUserCountPage(Map<String, Object> map);

	EventActivitySignUpVo findEventActivitySignUpVoByUserInfoAndEventActivityId(@Param("activityId")String activityId, @Param("userId")Long userId);

	List<EventActivitySignUpVo> findByActivityId(@Param("activityId")Long eventActivityId);

	List<EventActivitySignUpExportVo> selectExportData(Map map);
	
}