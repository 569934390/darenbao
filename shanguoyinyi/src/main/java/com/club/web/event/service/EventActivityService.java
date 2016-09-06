package com.club.web.event.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.club.core.common.Page;
import com.club.web.event.vo.EventActivitySaveVo;
import com.club.web.event.vo.ActivityImageGroupVo;
import com.club.web.event.vo.EventActivityDetailsVo;
import com.club.web.event.vo.EventActivityMobileVo;

public interface EventActivityService {

	Page<Map<String, Object>> queryEventActivityPage(Page<Map<String, Object>> page);

	Map<String,Object> check(String ids, HttpServletRequest request);

	Map<String,Object> noCheck(String ids, String failure, HttpServletRequest request);

	Map<String,Object> updateStatus(String action, String ids, HttpServletRequest request);

	Map<String,Object> delete(String ids);

	Map<String,Object> saveOrUpdateEventActivity(EventActivitySaveVo activitySaveVo,
			HttpServletRequest request);

	ActivityImageGroupVo getImageGroup(long groupId);

	EventActivityDetailsVo findEventActivityById(String id);

//	List<EventActivityMobileVo> findEventActivityForMobile();

	EventActivityMobileVo findEventActivityByIdForMobile(String id,String userId);

	Page<Map<String, Object>> queryEventActivityFromMobilePage(Page<Map<String, Object>> page);

	Long findRemainingNumberById(long parseLong);

}
