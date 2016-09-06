package com.club.web.event.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.web.event.domain.EventActivityCommentDo;
import com.club.web.event.domain.repository.EventActivityCommentRepository;
import com.club.web.event.service.EventActivityCommentService;
import com.club.web.event.vo.EventActivityCommentSaveVo;
import com.club.web.event.vo.EventActivityCommentVo;

@Service("eventActivityCommentService")
public class EventActivityCommentServiceImpl implements EventActivityCommentService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	EventActivityCommentRepository eventActivityCommentRepository;

	@Override
	public Page<Map<String, Object>> queryActivityCommentPage(Page<Map<String, Object>> page) {
		Page<Map<String, Object>> result = new Page<Map<String, Object>>();
		result.setStart(page.getStart());
		result.setLimit(page.getLimit());
		List<Map<String, Object>> list = eventActivityCommentRepository.queryActivityCommentPage(page);
		Long count = eventActivityCommentRepository.queryActivityCommentCountPage(page);
		result.setResultList(list);
		result.setTotalRecords(count.intValue());
		return result;
	}

	/**
	 * 活动点赞
	 */
	public Map<String, Object> Comment(EventActivityCommentSaveVo activityCommentVo, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (activityCommentVo.getContent()== null || "".equals(activityCommentVo.getContent())) {
			result.put("success", false);
			result.put("msg", "评论内容不能为空！");
			return result;
		}
		EventActivityCommentVo oldactivityCommentVo = eventActivityCommentRepository
				.findEventActivityCommentVoByUserInfoAndEventActivityId(activityCommentVo.getEventActivityId(),Long.parseLong(activityCommentVo.getEventActivityUserinfo()));
		if (oldactivityCommentVo != null) {
			result.put("success", false);
			result.put("msg", "您已经评论过！");
			return result;
		} else {
//			activityCommentVo.setEventActivityUserinfo(activityCommentVo.getId());
			EventActivityCommentDo eventActivityCommentDo = eventActivityCommentRepository.create(activityCommentVo);
			eventActivityCommentDo.insert();
			result.put("success", true);
		}
		return result;
	}

	@Override
	public List<EventActivityCommentVo> findByActivityId(Long eventActivityId) {
		return eventActivityCommentRepository.findByActivityId(eventActivityId);
	}

}
