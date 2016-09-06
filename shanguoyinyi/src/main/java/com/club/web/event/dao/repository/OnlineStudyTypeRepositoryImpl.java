package com.club.web.event.dao.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.core.common.Page;
import com.club.framework.util.BeanUtils;
import com.club.web.event.dao.base.po.EventOnlineStudyType;
import com.club.web.event.dao.extend.EventOnlineStudyTypeExtendMapper;
import com.club.web.event.domain.EventOnlineStudyTypeDo;
import com.club.web.event.domain.repository.OnlineStudyTypeRepository;
import com.club.web.event.vo.EventOnlineStudyTypeVo;

@Repository
public class OnlineStudyTypeRepositoryImpl implements OnlineStudyTypeRepository {

	@Autowired
	EventOnlineStudyTypeExtendMapper eventOnlineStudyTypeExtendMapper;

	@Override
	public EventOnlineStudyTypeDo create(EventOnlineStudyTypeVo eventOnlineStudyTypeVo) {
		if(eventOnlineStudyTypeVo==null){
			return null;
		}
		EventOnlineStudyTypeDo eventOnlineStudyTypeDo = new EventOnlineStudyTypeDo();
		BeanUtils.copyProperties(eventOnlineStudyTypeVo, eventOnlineStudyTypeDo);
		eventOnlineStudyTypeDo.setId(Long.parseLong(eventOnlineStudyTypeVo.getId()));
		if (eventOnlineStudyTypeVo.getParentId() != null && !"".equals(eventOnlineStudyTypeVo.getParentId())) {
			eventOnlineStudyTypeDo.setParentId(Long.parseLong(eventOnlineStudyTypeVo.getParentId()));
		}
		return eventOnlineStudyTypeDo;
	}

	private EventOnlineStudyType getPoByDo(EventOnlineStudyTypeDo eventOnlineStudyTypeDo) {
		if(eventOnlineStudyTypeDo==null){
			return null;
		}
		EventOnlineStudyType eventOnlineStudyType = new EventOnlineStudyType();
		BeanUtils.copyProperties(eventOnlineStudyTypeDo, eventOnlineStudyType);
		return eventOnlineStudyType;
	}

	private EventOnlineStudyTypeDo getDoByPo(EventOnlineStudyType eventOnlineStudyType) {
		if(eventOnlineStudyType==null){
			return null;
		}
		EventOnlineStudyTypeDo eventOnlineStudyTypeDo = new EventOnlineStudyTypeDo();
		BeanUtils.copyProperties(eventOnlineStudyType, eventOnlineStudyTypeDo);
		return eventOnlineStudyTypeDo;
	}
	
	private EventOnlineStudyTypeDo getDoByVo(EventOnlineStudyTypeVo eventOnlineStudyTypeVo) {
		if(eventOnlineStudyTypeVo==null){
			return null;
		}
		EventOnlineStudyTypeDo eventOnlineStudyTypeDo = new EventOnlineStudyTypeDo();
		BeanUtils.copyProperties(eventOnlineStudyTypeVo, eventOnlineStudyTypeDo);
		eventOnlineStudyTypeDo.setId(Long.parseLong(eventOnlineStudyTypeVo.getId()));
		return eventOnlineStudyTypeDo;
	}

	
	private List<EventOnlineStudyTypeDo> getDoListByPoList(
			List<EventOnlineStudyTypeVo> list) {
		List<EventOnlineStudyTypeDo> eventOnlineStudyTypeDoList=new ArrayList<EventOnlineStudyTypeDo>();
		for(EventOnlineStudyTypeVo eventOnlineStudyTypeVo:list){
			EventOnlineStudyTypeDo eventOnlineStudyTypeDo=getDoByVo(eventOnlineStudyTypeVo);
			eventOnlineStudyTypeDoList.add(eventOnlineStudyTypeDo);
		}
		return eventOnlineStudyTypeDoList;
	}


	@Override
	public void insert(EventOnlineStudyTypeDo eventOnlineStudyTypeDo) {
		eventOnlineStudyTypeExtendMapper.insert(getPoByDo(eventOnlineStudyTypeDo));
	}

	@Override
	public EventOnlineStudyTypeDo getEventOnlineStudyTypeDoById(long parseLong) {
		return getDoByPo(eventOnlineStudyTypeExtendMapper.selectByPrimaryKey(parseLong));
	}

	@Override
	public void update(EventOnlineStudyTypeDo eventOnlineStudyTypeDo) {
		eventOnlineStudyTypeExtendMapper.updateByPrimaryKey(getPoByDo(eventOnlineStudyTypeDo));
	}

	@Override
	public List<EventOnlineStudyTypeVo> getVoByEventOnlineStudyTypeVo(EventOnlineStudyTypeVo eventOnlineStudyTypeVo) {
		return eventOnlineStudyTypeExtendMapper.getVoByEventOnlineStudyTypeVo(eventOnlineStudyTypeVo);
	}

	@Override
	public List<EventOnlineStudyTypeDo> getEventOnlineStudyTypeDoByParentId(Long parentId) {
		return getDoListByPoList(eventOnlineStudyTypeExtendMapper.getEventOnlineStudyTypeVoByParentId(parentId));
	}

	@Override
	public void delete(Long id) {
		eventOnlineStudyTypeExtendMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<EventOnlineStudyTypeVo> getVoByName(String name) {
		return eventOnlineStudyTypeExtendMapper.getVoByName(name);
	}



}
