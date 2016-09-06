package com.club.web.event.dao.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.core.common.Page;
import com.club.framework.util.BeanUtils;
import com.club.web.event.dao.base.po.EventActivity;
import com.club.web.event.dao.extend.EventActivityExtendMapper;
import com.club.web.event.domain.EventActivityDo;
import com.club.web.event.domain.repository.EventActivityRepository;
import com.club.web.event.vo.EventActivityDetailsVo;
import com.club.web.event.vo.EventActivityMobileVo;
import com.club.web.event.vo.EventActivitySaveVo;

@Repository
public class EventActivityRepositoryImpl implements EventActivityRepository{
	
	@Autowired EventActivityExtendMapper eventActivityExtendMapper;
	
	private EventActivityDo getDoByPo(EventActivity eventActivity) {
		if(eventActivity==null){
			return null;
		}
		EventActivityDo eventActivityDo=new EventActivityDo();
		BeanUtils.copyProperties(eventActivity, eventActivityDo);
		return eventActivityDo;
	}
	
	private EventActivity getPoByDo(EventActivityDo eventActivityDo) {
		if(eventActivityDo==null){
			return null;
		}
		EventActivity eventActivity=new EventActivity();
		BeanUtils.copyProperties(eventActivityDo, eventActivity);
		return eventActivity;
	}

	@Override
	public List<Map<String, Object>> queryEventActivityPage(Page<Map<String, Object>> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (page.getConditons() != null) {
			map.put("title", page.getConditons().get("title").toString());
			map.put("activityTypeId", page.getConditons().get("activityTypeId").toString());
			map.put("beginTime", page.getConditons().get("beginTime").toString());
			map.put("activityStatus", page.getConditons().get("activityStatus").toString());
		}
		map.put("start", page.getStart());
		map.put("limit", page.getLimit());
		return eventActivityExtendMapper.queryEventActivityPage(map);
	}

	@Override
	public Long queryEventActivityCountPage(Page<Map<String, Object>> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (page.getConditons() != null) {
			map.put("title", page.getConditons().get("title").toString());
			map.put("activityTypeId", page.getConditons().get("activityTypeId").toString());
			map.put("beginTime", page.getConditons().get("beginTime").toString());
			map.put("activityStatus", page.getConditons().get("activityStatus").toString());
		}
		return eventActivityExtendMapper.queryEventActivityCountPage(map);
	}

	@Override
	public EventActivityDo findById(Long id) {
		return getDoByPo(eventActivityExtendMapper.selectByPrimaryKey(id));
	}

	@Override
	public void update(EventActivityDo eventActivityDo) {
		eventActivityExtendMapper.updateByPrimaryKeySelective(getPoByDo(eventActivityDo));
	}

	@Override
	public void delete(Long id) {
		eventActivityExtendMapper.deleteByPrimaryKey(id);
	}

	@Override
	public EventActivityDo create(EventActivitySaveVo activitySaveVo) {
		if(activitySaveVo==null){
			return null;
		}
		EventActivityDo eventActivityDo=new EventActivityDo();
		BeanUtils.copyProperties(activitySaveVo, eventActivityDo);
		eventActivityDo.setId(Long.parseLong(activitySaveVo.getId()));
		eventActivityDo.setActivityTypeId(Long.parseLong(activitySaveVo.getActivityTypeId()));
		if (activitySaveVo.getSubbranchId() == null || "".equals(activitySaveVo.getSubbranchId())|| "0".equals(activitySaveVo.getSubbranchId())) {
			eventActivityDo.setSubbranchId(null);
		}else{
			eventActivityDo.setSubbranchId(Long.parseLong(activitySaveVo.getSubbranchId()));
		}
		return eventActivityDo;
	}

	@Override
	public EventActivityDo getEventActivityDoById(long id) {
		return getDoByPo(eventActivityExtendMapper.selectByPrimaryKey(id));
	}

	@Override
	public void insert(EventActivityDo eventActivityDo) {
		eventActivityExtendMapper.insertSelective(getPoByDo(eventActivityDo));
	}

	@Override
	public EventActivityDetailsVo findEventActivityById(String id) {
		return eventActivityExtendMapper.findEventActivityDetailVoById(id);
	}

	@Override
	public void updateActivityStatusByTime() {
		eventActivityExtendMapper.updateActivityStatusByTime();
	}

	@Override
	public Long queryEventActivityByTypeIds(String[] ids) {
		return eventActivityExtendMapper.queryEventActivityByTypeIds(ids);
	}

//	@Override
//	public List<EventActivityMobileVo> findEventActivityForMobile() {
//		return eventActivityExtendMapper.findEventActivityForMobile();
//	}

	@Override
	public EventActivityMobileVo findEventActivityByIdForMobile(String id,String userId) {
		return eventActivityExtendMapper.findEventActivityByIdForMobile(id,userId);
	}

	@Override
	public List<Map<String, Object>> queryEventActivityFromMobilePage(Page<Map<String, Object>> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (page.getConditons() != null&&page.getConditons().size()!=0) {
			if(page.getConditons().get("subbranchId")!=null){
				map.put("subbranchId", page.getConditons().get("subbranchId").toString());
			}
			if(page.getConditons().get("userId")!=null){
				map.put("userId", page.getConditons().get("userId").toString());
			}
			if(page.getConditons().get("activityStatus")!=null){
				map.put("activityStatus", page.getConditons().get("activityStatus").toString());
			}
			if(page.getConditons().get("personal")!=null){
				map.put("personal", page.getConditons().get("personal").toString());
			}
		}
		map.put("start", page.getStart());
		map.put("limit", page.getLimit());
		return eventActivityExtendMapper.queryEventActivityFromMobilePage(map);
	}

	@Override
	public Long queryEventActivityCountFromMobilePage(Page<Map<String, Object>> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (page.getConditons() != null&&page.getConditons().size()!=0) {
			if(page.getConditons().get("subbranchId")!=null){
				map.put("subbranchId", page.getConditons().get("subbranchId").toString());
			}
			if(page.getConditons().get("activityStatus")!=null){
				map.put("activityStatus", page.getConditons().get("activityStatus").toString());
			}
			if(page.getConditons().get("personal")!=null){
				map.put("personal", page.getConditons().get("personal").toString());
			}
		}
		return eventActivityExtendMapper.queryEventActivityCountFromMobilePage(map);
	}

	@Override
	public Long findRemainingNumberById(long id) {
		return eventActivityExtendMapper.findRemainingNumberById(id);
	}

}
