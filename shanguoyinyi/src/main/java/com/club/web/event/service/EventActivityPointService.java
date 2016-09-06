package com.club.web.event.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.club.web.event.vo.EventActivityPointSaveVo;
import com.club.web.event.vo.EventActivityPointVo;

public interface EventActivityPointService {

	Map<String,Object> point(EventActivityPointSaveVo activityPointVo, HttpServletRequest request);

	Map<String,Object> cancelPoint(EventActivityPointSaveVo activityPointVo,
			HttpServletRequest request);

	List<EventActivityPointVo> findByActivityId(Long eventActivityId);

}
