package com.club.web.event.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.club.core.common.Page;
import com.club.web.event.vo.EventActivityCommentSaveVo;
import com.club.web.event.vo.EventActivityCommentVo;

public interface EventActivityCommentService {

	Page<Map<String, Object>> queryActivityCommentPage(Page<Map<String, Object>> page);

	Map<String,Object> Comment(EventActivityCommentSaveVo activityCommentVo,
			HttpServletRequest request);

	List<EventActivityCommentVo> findByActivityId(Long eventActivityId);

}
