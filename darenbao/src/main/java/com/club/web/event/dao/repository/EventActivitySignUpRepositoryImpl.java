package com.club.web.event.dao.repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.core.common.Page;
import com.club.framework.util.BeanUtils;
import com.club.web.event.dao.base.po.EventActivitySignUp;
import com.club.web.event.dao.extend.EventActivitySignUpExtendMapper;
import com.club.web.event.domain.EventActivitySignUpDo;
import com.club.web.event.domain.repository.EventActivitySignUpRepository;
import com.club.web.event.vo.EventActivitySignUpExportVo;
import com.club.web.event.vo.EventActivitySignUpSaveVo;
import com.club.web.event.vo.EventActivitySignUpVo;
import com.club.web.util.IdGenerator;

@Repository
public class EventActivitySignUpRepositoryImpl implements EventActivitySignUpRepository {

	@Autowired
	EventActivitySignUpExtendMapper eventActivityUserExtendMapper;

	@Override
	public EventActivitySignUpDo create(EventActivitySignUpSaveVo activitySignUpVo) {
		EventActivitySignUpDo eventActivitySignUpDo = new EventActivitySignUpDo();
		eventActivitySignUpDo.setId(IdGenerator.getDefault().nextId());
		eventActivitySignUpDo.setEventActivityId(Long.parseLong(activitySignUpVo.getEventActivityId()));
		eventActivitySignUpDo.setEventActivityUserinfo(Long.parseLong(activitySignUpVo.getEventActivityUserinfo()));
		eventActivitySignUpDo.setNote(activitySignUpVo.getNote());
		eventActivitySignUpDo.setTel(activitySignUpVo.getTel());
		eventActivitySignUpDo.setCreateTime(new Date());
		return eventActivitySignUpDo;
	}

	private EventActivitySignUp getPoByDo(EventActivitySignUpDo eventActivitySignUpDo) {
		if (eventActivitySignUpDo == null) {
			return null;
		}
		EventActivitySignUp eventActivitySignUp = new EventActivitySignUp();
		BeanUtils.copyProperties(eventActivitySignUpDo, eventActivitySignUp);
		return eventActivitySignUp;
	}

	@Override
	public List<Map<String, Object>> queryActivityUserPage(Page<Map<String, Object>> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (page.getConditons() != null) {
			if (page.getConditons().get("eventActivityId") != null)
				map.put("eventActivityId", page.getConditons().get("eventActivityId").toString());
			if (page.getConditons().get("nickname") != null) {
				if (page.getConditons().get("nickname").toString().contains("%"))
					map.put("nickname", page.getConditons().get("nickname").toString());
				else
					map.put("nickname", "%"+page.getConditons().get("nickname").toString()+"%");

			}
		}
		map.put("start", page.getStart());
		map.put("limit", page.getLimit());
		return eventActivityUserExtendMapper.queryActivityUserPage(map);
	}

	@Override
	public Long queryActivityUserCountPage(Page<Map<String, Object>> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (page.getConditons() != null) {
			if (page.getConditons().get("eventActivityId") != null)
				map.put("eventActivityId", page.getConditons().get("eventActivityId").toString());
			if (page.getConditons().get("nickname") != null) {
				if (page.getConditons().get("nickname").toString().contains("%"))
					map.put("nickname", page.getConditons().get("nickname").toString());
				else
					map.put("nickname", "%"+page.getConditons().get("nickname").toString()+"%");

			}
		}
		return eventActivityUserExtendMapper.queryActivityUserCountPage(map);
	}

	@Override
	public void insert(EventActivitySignUpDo eventActivitySignUpDo) {
		eventActivityUserExtendMapper.insert(getPoByDo(eventActivitySignUpDo));
	}

	@Override
	public EventActivitySignUpVo findEventActivitySignUpVoByUserInfoAndEventActivityId(String activityId, Long userId) {
		return eventActivityUserExtendMapper.findEventActivitySignUpVoByUserInfoAndEventActivityId(activityId, userId);
	}

	@Override
	public List<EventActivitySignUpVo> findByActivityId(Long eventActivityId) {
		return eventActivityUserExtendMapper.findByActivityId(eventActivityId);
	}

	@Override
	public void delete(EventActivitySignUpDo eventActivitySignUpDo) {
		eventActivityUserExtendMapper.deleteByPrimaryKey(eventActivitySignUpDo.getId());
	}

	@Override
	public List<EventActivitySignUpExportVo> selectExportData(Map map) {
		return eventActivityUserExtendMapper.selectExportData(map);
	}

}
