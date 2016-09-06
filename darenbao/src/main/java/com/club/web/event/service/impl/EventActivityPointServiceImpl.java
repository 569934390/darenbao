package com.club.web.event.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.web.event.domain.EventActivityPointDo;
import com.club.web.event.domain.repository.EventActivityPointRepository;
import com.club.web.event.service.EventActivityPointService;
import com.club.web.event.vo.EventActivityPointSaveVo;
import com.club.web.event.vo.EventActivityPointVo;

@Service("eventActivityPointService")
public class EventActivityPointServiceImpl implements EventActivityPointService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	EventActivityPointRepository eventActivityPointRepository;

	/**
	 * 活动点赞
	 */
	public Map<String, Object> point(EventActivityPointSaveVo activityPointVo, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		EventActivityPointVo oldactivityPointVo = eventActivityPointRepository
				.findEventActivityPointVoByUserInfoAndEventActivityId(activityPointVo.getEventActivityId(),Long.parseLong(activityPointVo.getEventActivityUserinfo()));
		if (oldactivityPointVo != null) {
			result.put("success", false);
			result.put("msg", "您已经点赞过！");
			return result;
		} else {
//			activityPointVo.setEventActivityUserinfo(activityPointVo.getId());
			EventActivityPointDo eventActivityPointDo = eventActivityPointRepository.create(activityPointVo);
			eventActivityPointDo.insert();
			result.put("success", true);
			result.put("msg", "点赞成功！");
		}
		return result;
	}

	@Override
	public Map<String, Object> cancelPoint(EventActivityPointSaveVo activityPointVo, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		EventActivityPointVo oldactivityPointVo = eventActivityPointRepository
				.findEventActivityPointVoByUserInfoAndEventActivityId(activityPointVo.getEventActivityId(),Long.parseLong(activityPointVo.getEventActivityUserinfo()));
		if (oldactivityPointVo == null) {
			result.put("success", false);
			result.put("msg", "您还没有点赞过！");
			return result;
		} else {
			eventActivityPointRepository.delete(Long.parseLong(oldactivityPointVo.getId()));
			result.put("success", true);
			result.put("msg", "取消点赞成功！");
		}
		return result;
	}

	@Override
	public List<EventActivityPointVo> findByActivityId(Long eventActivityId) {
		return eventActivityPointRepository.findByActivityId(eventActivityId);
	}

}
