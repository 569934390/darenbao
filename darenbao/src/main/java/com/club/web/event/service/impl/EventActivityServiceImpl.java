package com.club.web.event.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.club.core.common.Page;
import com.club.web.common.Constants;
import com.club.web.event.domain.EventActivityCommentDo;
import com.club.web.event.domain.EventActivityDo;
import com.club.web.event.domain.EventActivityPointDo;
import com.club.web.event.domain.EventActivitySignUpDo;
import com.club.web.event.domain.EventActivityUserinfoDo;
import com.club.web.event.domain.repository.EventActivityRepository;
import com.club.web.event.service.EventActivityCommentService;
import com.club.web.event.service.EventActivityPointService;
import com.club.web.event.service.EventActivityService;
import com.club.web.event.service.EventActivitySignUpService;
import com.club.web.event.vo.ActivityImageGroupVo;
import com.club.web.event.vo.ActivityImageVo;
import com.club.web.event.vo.EventActivityCommentVo;
import com.club.web.event.vo.EventActivityDetailsVo;
import com.club.web.event.vo.EventActivityMobileVo;
import com.club.web.event.vo.EventActivityPointVo;
import com.club.web.event.vo.EventActivitySaveVo;
import com.club.web.event.vo.EventActivitySignUpVo;
import com.club.web.image.service.ImageService;
import com.club.web.util.CommonUtil;
import com.club.web.util.IdGenerator;

@Service("eventActivityService")
@Transactional
public class EventActivityServiceImpl implements EventActivityService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	EventActivityRepository eventActivityRepository;
	@Autowired
	EventActivityCommentService eventActivityCommentService;
	@Autowired
	EventActivityPointService eventActivityPointService;
	@Autowired
	EventActivitySignUpService eventActivitySignUpService;

	@Autowired
	ImageService imageService;

	public ActivityImageGroupVo getImageGroup(long groupId) {
		List<com.club.web.image.service.vo.ImageVo> list = imageService.selectImagesByGroupId(groupId);
		Assert.notNull(list, "图片组获取失败");
		ActivityImageGroupVo result = new ActivityImageGroupVo();
		result.setGroupId(groupId);
		ActivityImageVo[] ivos = new ActivityImageVo[list.size()];
		result.setImages(ivos);
		for (int i = 0; i < list.size(); i++) {
			com.club.web.image.service.vo.ImageVo ivo = list.get(i);
			Assert.notNull(ivo, "图片组获取失败, 图片错误");
			ActivityImageVo vo = new ActivityImageVo();
			vo.setId(ivo.getId() + "");
			vo.setUrl(ivo.getPicUrl());
			ivos[i] = vo;
		}
		return result;
	}

	public Map<String, Object> saveOrUpdateEventActivity(EventActivitySaveVo activitySaveVo,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (activitySaveVo != null) {
			if(activitySaveVo.getTitle()==null||"".equals(activitySaveVo.getTitle())){
				result.put("success", false);
				result.put("msg", "活动标题不能为空！");
				return result;
			}
			if(activitySaveVo.getActivityTypeId()==null||"".equals(activitySaveVo.getActivityTypeId())){
				result.put("success", false);
				result.put("msg", "活动分类不能为空！");
				return result;
			}
			if(activitySaveVo.getSponsorName()==null||"".equals(activitySaveVo.getSponsorName())){
				result.put("success", false);
				result.put("msg", "活动主办方 不能为空！");
				return result;
			}
			if(activitySaveVo.getSponsorTel()==null||"".equals(activitySaveVo.getSponsorTel())){
				result.put("success", false);
				result.put("msg", "活动主办方联系方式不能为空！");
				return result;
			}else{
				if(!CommonUtil.isMobile(activitySaveVo.getSponsorTel())){
					result.put("success", false);
					result.put("msg", "请输入正确的手机方式！");
					return result;
				}
			}
			if(activitySaveVo.getBeginTime()==null||"".equals(activitySaveVo.getBeginTime())){
				result.put("success", false);
				result.put("msg", "活动开始时间不能为空！");
				return result;
			}
			if(activitySaveVo.getEndTime()==null||"".equals(activitySaveVo.getEndTime())){
				result.put("success", false);
				result.put("msg", "活动结束时间不能为空！");
				return result;
			}
			if(activitySaveVo.getRegStartTime()==null||"".equals(activitySaveVo.getRegStartTime())){
				result.put("success", false);
				result.put("msg", "活动报名开始时间不能为空！");
				return result;
			}
			if(activitySaveVo.getRegEndTime()==null||"".equals(activitySaveVo.getRegEndTime())){
				result.put("success", false);
				result.put("msg", "活动报名截止时间不能为空！");
				return result;
			}
			if(activitySaveVo.getNumberLimit()==null){
				result.put("success", false);
				result.put("msg", "活动人数限制不能为空！");
				return result;
			}
			if(activitySaveVo.getActivityAddress()==null||"".equals(activitySaveVo.getActivityAddress())){
				result.put("success", false);
				result.put("msg", "活动地址不能为空！");
				return result;
			}
			/**
			 * 获取当前登录用户的session
			 */
			Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
			Long user_id = null;
			if (loginMap != null && loginMap.get("staffId") != null) {
				user_id = Long.parseLong(loginMap.get("staffId").toString());
			}
			Date nowTime = new Date();

			if (activitySaveVo.getId() == null || "".equals(activitySaveVo.getId())) {
				long groupId = IdGenerator.getDefault().nextId();
				activitySaveVo.setId(IdGenerator.getDefault().nextId() + "");
				// 如果传过来的图片不为空。则保存记录
				if (activitySaveVo.getActivityPic() != null && activitySaveVo.getActivityPic().size() > 0) {
					List<ActivityImageVo> activityImageVoList = activitySaveVo.getActivityPic();
					String[] imageUrl = new String[activityImageVoList.size()];
					for (int i = 0; i < activityImageVoList.size(); i++) {
						imageUrl[i] = activityImageVoList.get(i).getUrl();
					}
					imageService.saveOrUpdateByGroupId(groupId, user_id, imageUrl);
				}
				activitySaveVo.setStatus(1L);
				// activitySaveVo.setActivityStatus(0L);
				activitySaveVo.setActivityStatus(1L);// 三国饮艺专用
				activitySaveVo.setPointNum(0L);
				activitySaveVo.setCreateTime(nowTime);
				activitySaveVo.setCreateBy(user_id);
				activitySaveVo.setUpdateBy(user_id);
				activitySaveVo.setUpdateTime(nowTime);
				EventActivityDo eventActivityDo = eventActivityRepository.create(activitySaveVo);
				// 如果传过来的图片不为空。则保存记录
				if (activitySaveVo.getActivityPic() != null && activitySaveVo.getActivityPic().size() > 0) {
					eventActivityDo.setActivityPic(groupId + "");
				}
				eventActivityDo.insert();
			} else {
				EventActivityDo eventActivityDo = eventActivityRepository
						.getEventActivityDoById(Long.parseLong(activitySaveVo.getId()));
				if (activitySaveVo.getSubbranchId() == null || "".equals(activitySaveVo.getSubbranchId())|| "0".equals(activitySaveVo.getSubbranchId())) {
					eventActivityDo.setSubbranchId(null);
				}else{
					eventActivityDo.setSubbranchId(Long.parseLong(activitySaveVo.getSubbranchId()));
				}
				eventActivityDo.setActivityTypeId(Long.parseLong(activitySaveVo.getActivityTypeId()));
				eventActivityDo.setType(activitySaveVo.getType());
				eventActivityDo.setSponsorName(activitySaveVo.getSponsorName());
				eventActivityDo.setSponsorTel(activitySaveVo.getSponsorTel());
				eventActivityDo.setBeginTime(activitySaveVo.getBeginTime());
				eventActivityDo.setEndTime(activitySaveVo.getEndTime());
				eventActivityDo.setRegStartTime(activitySaveVo.getRegStartTime());
				eventActivityDo.setRegEndTime(activitySaveVo.getEndTime());
				eventActivityDo.setNumberLimit(activitySaveVo.getNumberLimit());
				eventActivityDo.setActivityAddress(activitySaveVo.getActivityAddress());
				eventActivityDo.setActivityLatitude(activitySaveVo.getActivityLatitude());
				eventActivityDo.setActivityLongitude(activitySaveVo.getActivityLongitude());
				eventActivityDo.setCreateAddress(activitySaveVo.getCreateAddress());
				eventActivityDo.setCreateLatitude(activitySaveVo.getCreateLatitude());
				eventActivityDo.setCreateLongitude(activitySaveVo.getCreateLongitude());
				if (activitySaveVo.getActivityPic() != null && activitySaveVo.getActivityPic().size() > 0) {
					List<ActivityImageVo> activityImageVoList = activitySaveVo.getActivityPic();
					String[] imageUrl = new String[activityImageVoList.size()];
					for (int i = 0; i < activityImageVoList.size(); i++) {
						imageUrl[i] = activityImageVoList.get(i).getUrl();
					}
					long groupId = 0L;
					if (eventActivityDo.getActivityPic() != null && !"".equals(eventActivityDo.getActivityPic())) {
						groupId = Long.parseLong(eventActivityDo.getActivityPic());
					} else {
						groupId = IdGenerator.getDefault().nextId();
					}
					imageService.saveOrUpdateByGroupId(groupId, user_id, imageUrl);
					eventActivityDo.setActivityPic(groupId + "");
				} else {
					if (eventActivityDo.getActivityPic() != null && !"".equals(eventActivityDo.getActivityPic())) {
						long groupId = Long.parseLong(eventActivityDo.getActivityPic());
						imageService.saveOrUpdateByGroupId(groupId, user_id, null);
					}
					eventActivityDo.setActivityPic(null);
				}
				eventActivityDo.setContent(activitySaveVo.getContent());
				eventActivityDo.setUpdateBy(user_id);
				eventActivityDo.setUpdateTime(nowTime);
				eventActivityDo.update();
			}
			result.put("success", true);
		} else {
			result.put("success", false);
			result.put("msg", "活动信息不能为空");
		}
		return result;
	}

	@Override
	public Page<Map<String, Object>> queryEventActivityPage(Page<Map<String, Object>> page) {
		Page<Map<String, Object>> result = new Page<Map<String, Object>>();
		// 修改状态
		updateActivityStatusByTime();

		// 查询分页信息
		result.setStart(page.getStart());
		result.setLimit(page.getLimit());
		List<Map<String, Object>> list = eventActivityRepository.queryEventActivityPage(page);
		Long count = eventActivityRepository.queryEventActivityCountPage(page);
		result.setResultList(list);
		result.setTotalRecords(count.intValue());

		return result;
	}

	/**
	 * 修改状态
	 */
	public void updateActivityStatusByTime() {
		eventActivityRepository.updateActivityStatusByTime();
	}

	@Override
	public Map<String, Object> check(String ids, HttpServletRequest request) {
		String[] Ids = ids.split(",");
		Map<String, Object> result = new HashMap<String, Object>();
		/**
		 * 获取当前登录用户的session
		 */
		Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
		Long user_id = null;
		if (loginMap != null && loginMap.get("staffId") != null) {
			user_id = Long.parseLong(loginMap.get("staffId").toString());
		}
		Date nowTime = new Date();
		for (String idStr : Ids) {
			Long id = Long.parseLong(idStr);
			EventActivityDo eventActivityDo = eventActivityRepository.findById(id);
			eventActivityDo.setActivityStatus(1L);
			eventActivityDo.setUpdateBy(user_id);
			eventActivityDo.setUpdateTime(nowTime);
			eventActivityDo.update();
		}
		result.put("success", true);
		return result;
	}

	@Override
	public Map<String, Object> noCheck(String ids, String failure, HttpServletRequest request) {
		String[] Ids = ids.split(",");
		Map<String, Object> result = new HashMap<String, Object>();
		/**
		 * 获取当前登录用户的session
		 */
		Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
		Long user_id = null;
		if (loginMap != null && loginMap.get("staffId") != null) {
			user_id = Long.parseLong(loginMap.get("staffId").toString());
		}
		Date nowTime = new Date();
		for (String idStr : Ids) {
			Long id = Long.parseLong(idStr);
			EventActivityDo eventActivityDo = eventActivityRepository.findById(id);
			eventActivityDo.setFailure(failure);
			eventActivityDo.setUpdateBy(user_id);
			eventActivityDo.setUpdateTime(nowTime);
			eventActivityDo.update();
		}
		result.put("success", true);
		return result;
	}

	@Override
	public Map<String, Object> updateStatus(String action, String ids, HttpServletRequest request) {
		String[] Ids = ids.split(",");
		Map<String, Object> result = new HashMap<String, Object>();
		/**
		 * 获取当前登录用户的session
		 */
		Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
		Long user_id = null;
		if (loginMap != null && loginMap.get("staffId") != null) {
			user_id = Long.parseLong(loginMap.get("staffId").toString());
		}
		Date nowTime = new Date();
		for (String idStr : Ids) {
			Long id = Long.parseLong(idStr);
			EventActivityDo eventActivityDo = eventActivityRepository.findById(id);
			eventActivityDo.setStatus("start".equals(action) ? 1L : 0L);
			eventActivityDo.setUpdateBy(user_id);
			eventActivityDo.setUpdateTime(nowTime);
			eventActivityDo.update();
		}
		result.put("success", true);
		return result;
	}

	@Override
	public Map<String, Object> delete(String ids) {
		String[] Ids = ids.split(",");
		Map<String, Object> result = new HashMap<String, Object>();
		for (String idStr : Ids) {
			Long id = Long.parseLong(idStr);
			Long eventActivityId = id;
			List<EventActivityCommentVo> eventActivityCommentVos = eventActivityCommentService
					.findByActivityId(eventActivityId);
			List<EventActivityPointVo> eventActivityPointVos = eventActivityPointService
					.findByActivityId(eventActivityId);
			List<EventActivitySignUpVo> eventActivitySignUpVos = eventActivitySignUpService
					.findByActivityId(eventActivityId);
			for (EventActivityCommentVo activityCommentVo : eventActivityCommentVos) {
				EventActivityCommentDo eventActivityCommentDo = new EventActivityCommentDo();
				eventActivityCommentDo.setId(activityCommentVo.getId());
				eventActivityCommentDo.delete();

				// EventActivityUserinfoDo eventActivityUserinfoDo=new
				// EventActivityUserinfoDo();
				// eventActivityUserinfoDo.setId(activityCommentVo.getId());
				// eventActivityUserinfoDo.delete();
			}
			for (EventActivityPointVo eventActivityPointVo : eventActivityPointVos) {
				EventActivityPointDo eventActivityPointDo = new EventActivityPointDo();
				eventActivityPointDo.setId(Long.parseLong(eventActivityPointVo.getId()));
				eventActivityPointDo.delete();

				// EventActivityUserinfoDo eventActivityUserinfoDo=new
				// EventActivityUserinfoDo();
				// eventActivityUserinfoDo.setId(Long.parseLong(eventActivityPointVo.getId()));
				// eventActivityUserinfoDo.delete();
			}
			for (EventActivitySignUpVo eventActivitySignUpVo : eventActivitySignUpVos) {
				EventActivitySignUpDo eventActivitySignUpDo = new EventActivitySignUpDo();
				eventActivitySignUpDo.setId(eventActivitySignUpVo.getId());
				eventActivitySignUpDo.delete();

				// EventActivityUserinfoDo eventActivityUserinfoDo=new
				// EventActivityUserinfoDo();
				// eventActivityUserinfoDo.setId(eventActivitySignUpVo.getId());
				// eventActivityUserinfoDo.delete();
			}

			EventActivityDo eventActivityDo = eventActivityRepository.findById(id);
			if (eventActivityDo.getActivityPic() != null && "".equals(eventActivityDo.getActivityPic())) {
				long activityPic = 0;
				activityPic = Long.parseLong(eventActivityDo.getActivityPic());
				imageService.saveOrUpdateByGroupId(activityPic, 0L, null);
			}

			eventActivityDo.delete();
		}
		result.put("success", true);
		return result;
	}

	/**
	 * 根据ID查询
	 */
	public EventActivityDetailsVo findEventActivityById(String id) {
		return eventActivityRepository.findEventActivityById(id);
	}

	// @Override
	// public List<EventActivityMobileVo> findEventActivityForMobile() {
	// return eventActivityRepository.findEventActivityForMobile();
	// }

	@Override
	public EventActivityMobileVo findEventActivityByIdForMobile(String id,String userId) {
		// 修改状态
		updateActivityStatusByTime();
		
		return eventActivityRepository.findEventActivityByIdForMobile(id,userId);
	}

	@Override
	public Page<Map<String, Object>> queryEventActivityFromMobilePage(Page<Map<String, Object>> page) {
		Page<Map<String, Object>> result = new Page<Map<String, Object>>();
		// 修改状态
		updateActivityStatusByTime();

		// 查询分页信息
		result.setStart(page.getStart());
		result.setLimit(page.getLimit());
		List<Map<String, Object>> list = eventActivityRepository.queryEventActivityFromMobilePage(page);
		Long count = eventActivityRepository.queryEventActivityCountFromMobilePage(page);
		result.setResultList(list);
		result.setTotalRecords(count.intValue());

		return result;
	}

	/**
	 * 查询生育人数
	 */
	public Long findRemainingNumberById(long id) {
		return eventActivityRepository.findRemainingNumberById(id);
	}

}
