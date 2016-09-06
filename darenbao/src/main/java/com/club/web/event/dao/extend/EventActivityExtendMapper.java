package com.club.web.event.dao.extend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.club.web.event.dao.base.EventActivityMapper;
import com.club.web.event.vo.EventActivityDetailsVo;
import com.club.web.event.vo.EventActivityMobileVo;

public interface EventActivityExtendMapper extends EventActivityMapper{

	List<Map<String, Object>> queryEventActivityPage(Map<String, Object> map);

	Long queryEventActivityCountPage(Map<String, Object> map);

	EventActivityDetailsVo findEventActivityDetailVoById(@Param("id")String id);

	void updateActivityStatusByTime();

	Long queryEventActivityByTypeIds(@Param("activityTypeIds")String[] ids);

//	List<EventActivityMobileVo> findEventActivityForMobile();

	EventActivityMobileVo findEventActivityByIdForMobile(@Param("id")String id, @Param("userId")String userId);

	List<Map<String, Object>> queryEventActivityFromMobilePage(Map<String, Object> map);

	Long queryEventActivityCountFromMobilePage(Map<String, Object> map);

	Long findRemainingNumberById(@Param("id")long id);
   
}