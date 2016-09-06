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

import com.club.core.common.Page;
import com.club.web.common.Constants;
import com.club.web.event.domain.EventActivityTypeDo;
import com.club.web.event.domain.repository.ActivityTypeRepository;
import com.club.web.event.listener.EventActivityTypeListenerManager;
import com.club.web.event.service.ActivityTypeService;
import com.club.web.event.vo.EventActivityTypeVo;
import com.club.web.util.IdGenerator;

/**
 * @Title: ActivityTypeServiceImpl.java
 * @Package com.club.web.event.service.impl
 * @Description: TODO(活动分类Service)
 * @author 柳伟军
 * @date 2016年4月18日 下午2:43:57
 * @version V1.0
 */
@Service("activityTypeService")
@Transactional
public class ActivityTypeServiceImpl implements ActivityTypeService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	EventActivityTypeListenerManager eventActivityTypeListenerManager;
	@Autowired
	ActivityTypeRepository activityTypeRepository;

	public Map<String, Object> saveOrUpdateActivityType(EventActivityTypeVo eventActivityTypeVo,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (eventActivityTypeVo != null) {
			if (null == eventActivityTypeVo.getName() || "".equals(eventActivityTypeVo.getName())) {
				result.put("success", false);
				result.put("msg", "请输入活动分类名称");
				return result;
			}

			List<EventActivityTypeVo> eventActivityTypeVoByName = activityTypeRepository
					.queryActivityTypeVoByName(eventActivityTypeVo.getName());
			/**
			 * 获取当前登录用户的session
			 */
			Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
			Long user_id = null;
			if (loginMap != null && loginMap.get("staffId") != null) {
				user_id = Long.parseLong(loginMap.get("staffId").toString());
			}
			Date nowTime = new Date();

			if (eventActivityTypeVo.getId() == null || "".equals(eventActivityTypeVo.getId())) {
				if (eventActivityTypeVoByName.size() > 0) {
					result.put("success", false);
					result.put("msg", "该活动分类名称已经存在!");
					return result;
				}
				eventActivityTypeVo.setId(IdGenerator.getDefault().nextId() + "");
				eventActivityTypeVo.setStatus(1L);
				eventActivityTypeVo.setCreateTime(nowTime);
				eventActivityTypeVo.setCreateBy(user_id);
				eventActivityTypeVo.setUpdateBy(user_id);
				eventActivityTypeVo.setUpdateTime(nowTime);
				EventActivityTypeDo eventActivityTypeDo = activityTypeRepository.create(eventActivityTypeVo);
				eventActivityTypeDo.insert();
			} else {
				EventActivityTypeDo eventActivityTypeDo = activityTypeRepository
						.getEventActivityTypeDoById(Long.parseLong(eventActivityTypeVo.getId()));
				if (eventActivityTypeVoByName.size() > 0
						&& !eventActivityTypeDo.getName().equalsIgnoreCase(eventActivityTypeVo.getName())) {
					result.put("success", false);
					result.put("msg", "该活动分类名称已经存在!");
					return result;
				}
				eventActivityTypeDo.setName(eventActivityTypeVo.getName());
				eventActivityTypeDo.setUpdateBy(user_id);
				eventActivityTypeDo.setUpdateTime(nowTime);
				eventActivityTypeDo.update();
			}
			result.put("success", true);
		} else {
			result.put("success", false);
			result.put("msg", "在活动分类信息不能为空");
		}
		return result;
	}

	@Override
	public Page<Map<String, Object>> queryActivityTypePage(Page<Map<String, Object>> page) {
		Page<Map<String, Object>> result = new Page<Map<String, Object>>();
		result.setStart(page.getStart());
		result.setLimit(page.getLimit());
		List<Map<String, Object>> list = activityTypeRepository.queryActivityTypePage(page);
		Long count = activityTypeRepository.queryActivityTypeCountPage(page);
		result.setResultList(list);
		result.setTotalRecords(count.intValue());
		return result;
	}

	@Override
	public Map<String, Object> delete(String idStr) {
		String[] Ids = idStr.split(",");
		Map<String, Object> result = new HashMap<String, Object>();
		if (eventActivityTypeListenerManager.delete(Ids)) {
			for (String id : Ids) {
				activityTypeRepository.delete(id);
			}
			result.put("success", true);
		} else {
			result.put("success", false);
			result.put("msg", "选中项有被引用，不能删除！");
		}
		return result;
	}

	@Override
	public Map<String, Object> updateStatus(String IdStr, long status, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] Ids = IdStr.split(",");
		if (status > 0) {
			updateStatusMethod(IdStr, status, request);
			result.put("success", true);
		} else {
			if (eventActivityTypeListenerManager.delete(Ids)) {
				updateStatusMethod(IdStr, status, request);
				result.put("success", true);
			} else {
				result.put("success", false);
				result.put("msg", "选中项有被引用，不能禁用！");
			}
		}
		return result;
	}

	public void updateStatusMethod(String IdStr, long status, HttpServletRequest request) {
		String[] Ids = IdStr.split(",");
		/**
		 * 获取当前登录用户的session
		 */
		Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
		for (String id : Ids) {
			EventActivityTypeDo eventActivityTypeDo = activityTypeRepository
					.getEventActivityTypeDoById(Long.parseLong(id));
			eventActivityTypeDo.setStatus(status);
			eventActivityTypeDo.setUpdateTime(new Date());
			if (loginMap != null && loginMap.get("staffId") != null) {
				eventActivityTypeDo.setUpdateBy(Long.parseLong(loginMap.get("staffId").toString()));
			}
			eventActivityTypeDo.update();
		}
	}

	@Override
	public List<EventActivityTypeVo> findList() {
		Long status = 1L;
		return activityTypeRepository.findListByStatus(status);
	}

}
