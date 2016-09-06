package com.club.web.event.dao.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.core.common.Page;
import com.club.framework.util.BeanUtils;
import com.club.web.event.dao.base.po.EventOnlineStudy;
import com.club.web.event.dao.extend.EventOnlineStudyExtendMapper;
import com.club.web.event.domain.EventOnlineStudyDo;
import com.club.web.event.domain.repository.OnlineStudyRepository;
import com.club.web.event.vo.EventOnlineStudyVo;

@Repository
public class OnlineStudyRepositoryImpl implements OnlineStudyRepository {

	@Autowired
	EventOnlineStudyExtendMapper eventOnlineStudyExtendMapper;

	@Override
	public EventOnlineStudyDo create(EventOnlineStudyVo eventOnlineStudyVo) {
		if(eventOnlineStudyVo==null){
			return null;
		}
		EventOnlineStudyDo eventOnlineStudyDo = new EventOnlineStudyDo();
		BeanUtils.copyProperties(eventOnlineStudyVo, eventOnlineStudyDo);
		eventOnlineStudyDo.setId(Long.parseLong(eventOnlineStudyVo.getId()));
		eventOnlineStudyDo.setStudyType(Long.parseLong(eventOnlineStudyVo.getStudyType()));
		eventOnlineStudyDo.setStudyChildType(Long.parseLong(eventOnlineStudyVo.getStudyChildType()));
		return eventOnlineStudyDo;
	}

	private EventOnlineStudy getPoByDo(EventOnlineStudyDo eventOnlineStudyDo) {
		if(eventOnlineStudyDo==null){
			return null;
		}
		EventOnlineStudy eventOnlineStudy = new EventOnlineStudy();
		BeanUtils.copyProperties(eventOnlineStudyDo, eventOnlineStudy);
		return eventOnlineStudy;
	}

	private EventOnlineStudyDo getDoByPo(EventOnlineStudy eventOnlineStudy) {
		if(eventOnlineStudy==null){
			return null;
		}
		EventOnlineStudyDo eventOnlineStudyDo = new EventOnlineStudyDo();
		BeanUtils.copyProperties(eventOnlineStudy, eventOnlineStudyDo);
		return eventOnlineStudyDo;
	}

	@Override
	public void update(EventOnlineStudyDo eventOnlineStudyDo) {
		eventOnlineStudyExtendMapper.updateByPrimaryKeyWithBLOBs(getPoByDo(eventOnlineStudyDo));
	}

	@Override
	public void insert(EventOnlineStudyDo eventOnlineStudyDo) {
		eventOnlineStudyExtendMapper.insert(getPoByDo(eventOnlineStudyDo));
	}

	@Override
	public List<Map<String, Object>> queryOnlineStudyPage(Page<Map<String, Object>> page) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (page.getConditons() != null) {
			map.put("conditions", page.getConditons().get("conditions").toString());
			map.put("studyType", page.getConditons().get("studyType").toString());
			map.put("studyChildType", page.getConditons().get("studyChildType").toString());
			if (page.getConditons().get("type") != null) {
				map.put("type", page.getConditons().get("type").toString());
			}
			if (page.getConditons().get("createTimeOrder") != null&&!"".equals(page.getConditons().get("createTimeOrder").toString())) {
				map.put("createTimeOrder", page.getConditons().get("createTimeOrder").toString());
			}
			if (page.getConditons().get("readNumOrder") != null&&!"".equals(page.getConditons().get("readNumOrder").toString())) {
				map.put("readNumOrder", page.getConditons().get("readNumOrder").toString());
			}
		}
		map.put("start", page.getStart());
		map.put("limit", page.getLimit());
		return eventOnlineStudyExtendMapper.queryOnlineStudyPage(map);
	}

	@Override
	public Long queryOnlineStudyCountPage(Page<Map<String, Object>> page) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (page.getConditons() != null) {
			map.put("conditions", page.getConditons().get("conditions").toString());
			map.put("studyType", page.getConditons().get("studyType").toString());
			map.put("studyChildType", page.getConditons().get("studyChildType").toString());
			if (page.getConditons().get("type") != null) {
				map.put("type", page.getConditons().get("type").toString());
			}
		}
		return eventOnlineStudyExtendMapper.queryOnlineStudyCountPage(map);
	}

	@Override
	public List<EventOnlineStudyVo> queryOnlineStudyVoByTitle(String title) {
		return eventOnlineStudyExtendMapper.queryOnlineStudyVoByTitle(title);
	}

	@Override
	public EventOnlineStudyDo getEventOnlineStudyDoById(long id) {
		return getDoByPo(eventOnlineStudyExtendMapper.selectByPrimaryKey(id));
	}

	@Override
	public void delete(EventOnlineStudyDo eventOnlineStudyDo) {
		eventOnlineStudyExtendMapper.deleteByPrimaryKey(eventOnlineStudyDo.getId());
	}

	@Override
	public EventOnlineStudyVo getEventOnlineStudyVoById(long id) {
		return eventOnlineStudyExtendMapper.getEventOnlineStudyVoById(id);
	}

}
