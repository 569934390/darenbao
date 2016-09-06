package com.club.web.event.dao.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.core.common.Page;
import com.club.framework.util.BeanUtils;
import com.club.web.event.dao.base.po.EventActivityType;
import com.club.web.event.dao.extend.EventActivityTypeExtendMapper;
import com.club.web.event.domain.EventActivityTypeDo;
import com.club.web.event.domain.repository.ActivityTypeRepository;
import com.club.web.event.vo.EventActivityTypeVo;

@Repository
public class ActivityTypeRepositoryImpl implements ActivityTypeRepository{
	
	@Autowired
	EventActivityTypeExtendMapper activityTypeExtendMapper;

	public EventActivityTypeDo create(EventActivityTypeVo eventActivityTypeVo) {
		if(eventActivityTypeVo==null){
			return null;
		}
		EventActivityTypeDo eventActivityTypeDo=new EventActivityTypeDo();
		BeanUtils.copyProperties(eventActivityTypeVo, eventActivityTypeDo);
		eventActivityTypeDo.setId(Long.parseLong(eventActivityTypeVo.getId()));
		return eventActivityTypeDo;
	}
	
	private EventActivityType getPoByDo(EventActivityTypeDo eventActivityTypeDo) {
		if(eventActivityTypeDo==null){
			return null;
		}
		EventActivityType eventActivityType=new EventActivityType();
		BeanUtils.copyProperties(eventActivityTypeDo, eventActivityType);
		return eventActivityType;
	}
	
	private EventActivityTypeDo getDoByPo(EventActivityType eventActivityType) {
		if(eventActivityType==null){
			return null;
		}
		EventActivityTypeDo eventActivityTypeDo=new EventActivityTypeDo();
		BeanUtils.copyProperties(eventActivityType, eventActivityTypeDo);
		return eventActivityTypeDo;
	}
	
	@Override
	public EventActivityTypeDo getEventActivityTypeDoById(long id) {
		return getDoByPo(activityTypeExtendMapper.selectByPrimaryKey(id));
	}

	@Override
	public List<EventActivityTypeVo> queryActivityTypeVoByName(String name) {
		return activityTypeExtendMapper.queryActivityTypeVoByName(name);
	}

	@Override
	public void insert(EventActivityTypeDo eventActivityTypeDo) {
		activityTypeExtendMapper.insertSelective(getPoByDo(eventActivityTypeDo));		
	}
	
	@Override
	public void update(EventActivityTypeDo eventActivityTypeDo) {
		activityTypeExtendMapper.updateByPrimaryKeySelective(getPoByDo(eventActivityTypeDo));
	}

	@Override
	public List<Map<String, Object>> queryActivityTypePage(Page<Map<String, Object>> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", page.getStart());
		map.put("limit", page.getLimit());
		return activityTypeExtendMapper.queryActivityTypePage(map);
	}

	@Override
	public Long queryActivityTypeCountPage(Page<Map<String, Object>> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		return activityTypeExtendMapper.queryActivityTypeCountPage(map);
	}

	@Override
	public void delete(String id) {
		activityTypeExtendMapper.deleteByPrimaryKey(Long.parseLong(id));
	}

	@Override
	public List<EventActivityTypeVo> findListByStatus(Long status) {
		return activityTypeExtendMapper.findListByStatus(status);
	}



}
