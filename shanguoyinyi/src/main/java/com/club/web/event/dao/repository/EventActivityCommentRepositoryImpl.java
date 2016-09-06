package com.club.web.event.dao.repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.core.common.Page;
import com.club.framework.util.BeanUtils;
import com.club.web.event.dao.base.po.EventActivityComment;
import com.club.web.event.dao.extend.EventActivityCommentExtendMapper;
import com.club.web.event.domain.EventActivityCommentDo;
import com.club.web.event.domain.repository.EventActivityCommentRepository;
import com.club.web.event.vo.EventActivityCommentSaveVo;
import com.club.web.event.vo.EventActivityCommentVo;
import com.club.web.util.IdGenerator;

@Repository
public class EventActivityCommentRepositoryImpl implements EventActivityCommentRepository{
	
	@Autowired
	EventActivityCommentExtendMapper eventActivityCommentExtendMapper;

	@Override
	public EventActivityCommentDo create(EventActivityCommentSaveVo activityCommentVo) {
		EventActivityCommentDo eventActivityCommentDo=new EventActivityCommentDo();
		eventActivityCommentDo.setId(IdGenerator.getDefault().nextId());
		eventActivityCommentDo.setEventActivityId(Long.parseLong(activityCommentVo.getEventActivityId()));
		eventActivityCommentDo.setEventActivityUserinfo(Long.parseLong(activityCommentVo.getEventActivityUserinfo()));
		eventActivityCommentDo.setContent(activityCommentVo.getContent());
		eventActivityCommentDo.setCreateTime(new Date());
		return eventActivityCommentDo;
	}
	
	private EventActivityComment getPoByDo(EventActivityCommentDo eventActivityCommentDo) {
		if(eventActivityCommentDo==null){
			return null;
		}
		EventActivityComment eventActivityComment=new EventActivityComment();
		BeanUtils.copyProperties(eventActivityCommentDo, eventActivityComment);
		return eventActivityComment;
	}
	
	@Override
	public List<Map<String, Object>> queryActivityCommentPage(Page<Map<String, Object>> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (page.getConditons() != null) {
			map.put("eventActivityId", page.getConditons().get("eventActivityId").toString());
		}
		map.put("start", page.getStart());
		map.put("limit", page.getLimit());
		return eventActivityCommentExtendMapper.queryActivityCommentPage(map);
	}

	@Override
	public Long queryActivityCommentCountPage(Page<Map<String, Object>> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (page.getConditons() != null) {
			map.put("eventActivityId", page.getConditons().get("eventActivityId").toString());
		}
		return eventActivityCommentExtendMapper.queryActivityCommentCountPage(map);
	}

	@Override
	public EventActivityCommentVo findEventActivityCommentVoByUserInfoAndEventActivityId(String eventActivityId,
			Long userId) {
		return eventActivityCommentExtendMapper.findEventActivityCommentVoByUserInfoAndEventActivityId(eventActivityId,userId);
	}

	@Override
	public void insert(EventActivityCommentDo eventActivityCommentDo) {
		eventActivityCommentExtendMapper.insert(getPoByDo(eventActivityCommentDo));
	}

	@Override
	public List<EventActivityCommentVo> findByActivityId(Long eventActivityId) {
		return eventActivityCommentExtendMapper.findByActivityId(eventActivityId);
	}

	@Override
	public void delete(EventActivityCommentDo eventActivityCommentDo) {
		eventActivityCommentExtendMapper.deleteByPrimaryKey(eventActivityCommentDo.getId());		
	}


}
