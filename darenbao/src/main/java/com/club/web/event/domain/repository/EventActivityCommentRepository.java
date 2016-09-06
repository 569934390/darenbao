package com.club.web.event.domain.repository;

import java.util.List;
import java.util.Map;

import com.club.core.common.Page;
import com.club.web.event.dao.base.po.EventActivityComment;
import com.club.web.event.domain.EventActivityCommentDo;
import com.club.web.event.vo.EventActivityCommentSaveVo;
import com.club.web.event.vo.EventActivityCommentVo;

public interface EventActivityCommentRepository {

	List<Map<String, Object>> queryActivityCommentPage(Page<Map<String, Object>> page);

	Long queryActivityCommentCountPage(Page<Map<String, Object>> page);

	EventActivityCommentVo findEventActivityCommentVoByUserInfoAndEventActivityId(String eventActivityId, Long id);

	EventActivityCommentDo create(EventActivityCommentSaveVo activityCommentVo);

	void insert(EventActivityCommentDo eventActivityCommentDo);

	List<EventActivityCommentVo> findByActivityId(Long eventActivityId);

	void delete(EventActivityCommentDo eventActivityCommentDo);

}
