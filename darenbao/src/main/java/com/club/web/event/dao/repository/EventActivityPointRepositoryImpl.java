package com.club.web.event.dao.repository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.event.dao.base.po.EventActivityPoint;
import com.club.web.event.dao.extend.EventActivityPointExtendMapper;
import com.club.web.event.domain.EventActivityPointDo;
import com.club.web.event.domain.repository.EventActivityPointRepository;
import com.club.web.event.vo.EventActivityPointSaveVo;
import com.club.web.event.vo.EventActivityPointVo;
import com.club.web.util.IdGenerator;

@Repository
public class EventActivityPointRepositoryImpl implements EventActivityPointRepository{
	
	@Autowired EventActivityPointExtendMapper eventActivityPointExtendMapper;

	@Override
	public EventActivityPointDo create(EventActivityPointSaveVo activityPointVo) {
		EventActivityPointDo eventActivityPointDo=new EventActivityPointDo();
		eventActivityPointDo.setId(IdGenerator.getDefault().nextId());
		eventActivityPointDo.setEventActivityId(Long.parseLong(activityPointVo.getEventActivityId()));
		eventActivityPointDo.setEventActivityUserinfo(Long.parseLong(activityPointVo.getEventActivityUserinfo()));
		eventActivityPointDo.setCreateTime(new Date());
		return eventActivityPointDo;
	}
	
	private EventActivityPoint getPoByDo(EventActivityPointDo eventActivityPointDo) {
		if(eventActivityPointDo==null){
			return null;
		}
		EventActivityPoint eventActivityPoint=new EventActivityPoint();
		BeanUtils.copyProperties(eventActivityPointDo, eventActivityPoint);
		return eventActivityPoint;
	}
	
	private EventActivityPointDo getDoByPo(EventActivityPoint eventActivityPoint) {
		if(eventActivityPoint==null){
			return null;
		}
		EventActivityPointDo activityPointDo=new EventActivityPointDo();
		BeanUtils.copyProperties(eventActivityPoint,activityPointDo);
		return activityPointDo;
	}


	@Override
	public void insert(EventActivityPointDo eventActivityPointDo) {
		eventActivityPointExtendMapper.insert(getPoByDo(eventActivityPointDo));
	}

	@Override
	public EventActivityPointVo findEventActivityPointVoByUserInfoAndEventActivityId(String eventActivityId, Long userId) {
		return eventActivityPointExtendMapper.findEventActivityPointVoByUserInfoAndEventActivityId(eventActivityId,userId);
	}

	public void delete(long id) {
		eventActivityPointExtendMapper.deleteByPrimaryKey(id);
	}

	@Override
	public EventActivityPointDo findById(long id) {
		return getDoByPo(eventActivityPointExtendMapper.selectByPrimaryKey(id));
	}

	@Override
	public List<EventActivityPointVo> findByActivityId(Long eventActivityId) {
		return eventActivityPointExtendMapper.findByActivityId(eventActivityId);
	}

	@Override
	public void delete(EventActivityPointDo eventActivityPointDo) {
		delete(eventActivityPointDo.getId());
	}
}
