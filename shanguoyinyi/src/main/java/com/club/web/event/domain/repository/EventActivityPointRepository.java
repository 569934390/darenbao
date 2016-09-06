package com.club.web.event.domain.repository;

import java.util.List;

import com.club.web.event.domain.EventActivityPointDo;
import com.club.web.event.vo.EventActivityPointSaveVo;
import com.club.web.event.vo.EventActivityPointVo;

public interface EventActivityPointRepository {

	EventActivityPointDo create(EventActivityPointSaveVo activityPointVo);

	void insert(EventActivityPointDo eventActivityPointDo);

	EventActivityPointVo findEventActivityPointVoByUserInfoAndEventActivityId(String eventActivityId, Long id);

	void delete(EventActivityPointDo eventActivityPointDo);

	EventActivityPointDo findById(long id);

	List<EventActivityPointVo> findByActivityId(Long eventActivityId);

	void delete(long id);

}
