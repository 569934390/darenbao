package com.club.web.event.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.club.core.common.Page;
import com.club.web.common.Constants;
import com.club.web.event.domain.EventOnlineStudyTypeDo;
import com.club.web.event.domain.repository.OnlineStudyTypeRepository;
import com.club.web.event.service.OnlineStudyTypeService;
import com.club.web.event.vo.EventOnlineStudyTypeVo;
import com.club.web.stock.domain.CargoClassifyDo;
import com.club.web.stock.domain.CargoSupplierDo;
import com.club.web.store.domain.TradeHeadStoreDo;
import com.club.web.util.IdGenerator;

/**
 * @Title: OnlineStudyTypeServiceImpl.java
 * @Package com.club.web.event.service.impl
 * @Description: TODO(学习分类Service)
 * @author 柳伟军
 * @date 2016年4月11日 下午5:27:53
 * @version V1.0
 */
@Service("onlineStudyTypeService")
@Transactional
public class OnlineStudyTypeServiceImpl implements OnlineStudyTypeService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	OnlineStudyTypeRepository onlineStudyTypeRepository;

	/**
	 * 新增編輯
	 */
	public Map<String, Object> saveOrUpdateOnlineStudyType(EventOnlineStudyTypeVo eventOnlineStudyTypeVo,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (eventOnlineStudyTypeVo != null) {
			if (null == eventOnlineStudyTypeVo.getName() || "".equals(eventOnlineStudyTypeVo.getName())) {
				result.put("success", false);
				result.put("msg", "请输入学习分类名称");
				return result;
			}

			/**
			 * 根据名称查询
			 */
			List<EventOnlineStudyTypeVo> eventOnlineStudyTypeName = onlineStudyTypeRepository
					.getVoByName(eventOnlineStudyTypeVo.getName());
			/**
			 * 获取当前登录用户的session
			 */
			Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);

			Long user_id = 0L;
			if (loginMap != null && loginMap.get("staffId") != null) {
				user_id = Long.parseLong(loginMap.get("staffId").toString());
			}
			Date nowtime = new Date();

			if (eventOnlineStudyTypeVo.getId() == null || "".equals(eventOnlineStudyTypeVo.getId())) {
				if (eventOnlineStudyTypeName.size() > 0) {
					result.put("success", false);
					result.put("msg", "该学习分类名称已经存在!");
					return result;
				}
				eventOnlineStudyTypeVo.setId(IdGenerator.getDefault().nextId() + "");
				if (eventOnlineStudyTypeVo.getParentId() == null || "".equals(eventOnlineStudyTypeVo.getParentId())) {
					eventOnlineStudyTypeVo.setGrade(0L);
				} else {
					eventOnlineStudyTypeVo.setGrade(1L);
				}
				eventOnlineStudyTypeVo.setCreateTime(nowtime);
				eventOnlineStudyTypeVo.setCreateBy(user_id);
				eventOnlineStudyTypeVo.setUpdateTime(nowtime);
				eventOnlineStudyTypeVo.setUpdateBy(user_id);
				EventOnlineStudyTypeDo eventOnlineStudyTypeDo = onlineStudyTypeRepository
						.create(eventOnlineStudyTypeVo);
				eventOnlineStudyTypeDo.insert();

				// 启用上级
				if (eventOnlineStudyTypeDo.getParentId() != null) {
					this.updateStart(eventOnlineStudyTypeDo.getParentId(), user_id, nowtime);
				}
			} else {
				EventOnlineStudyTypeDo eventOnlineStudyTypeDo = onlineStudyTypeRepository
						.getEventOnlineStudyTypeDoById(Long.parseLong(eventOnlineStudyTypeVo.getId()));
				if (eventOnlineStudyTypeName.size() > 0
						&& !eventOnlineStudyTypeDo.getName().equalsIgnoreCase(eventOnlineStudyTypeVo.getName())) {
					result.put("success", false);
					result.put("msg", "该学习分类名称已经存在!");
					return result;
				}
				eventOnlineStudyTypeDo.setName(eventOnlineStudyTypeVo.getName());
				eventOnlineStudyTypeDo.setUpdateTime(nowtime);
				eventOnlineStudyTypeDo.setUpdateBy(user_id);
				eventOnlineStudyTypeDo.update();
			}
			result.put("success", true);
		} else {
			result.put("success", false);
			result.put("msg", "学习分类信息不能为空");
		}
		return result;
	}

	/**
	 * 根据父亲ID获取孩子
	 */
	@Override
	public List<EventOnlineStudyTypeVo> getVoByParentId(String parentId) {
		EventOnlineStudyTypeVo eventOnlineStudyTypeVo = new EventOnlineStudyTypeVo();
		eventOnlineStudyTypeVo.setParentId(parentId);
		return onlineStudyTypeRepository.getVoByEventOnlineStudyTypeVo(eventOnlineStudyTypeVo);
	}

	/**
	 * 获取一级分类
	 */
	@Override
	public Page<EventOnlineStudyTypeVo> getParents() {
		Page<EventOnlineStudyTypeVo> result = new Page<EventOnlineStudyTypeVo>();
		EventOnlineStudyTypeVo eventOnlineStudyTypeVo = new EventOnlineStudyTypeVo();
		result.setResultList(onlineStudyTypeRepository.getVoByEventOnlineStudyTypeVo(eventOnlineStudyTypeVo));
		return result;
	}

	public List<EventOnlineStudyTypeVo> getParentList() {
		EventOnlineStudyTypeVo eventOnlineStudyTypeVo = new EventOnlineStudyTypeVo();
		eventOnlineStudyTypeVo.setStatus(1);
		return onlineStudyTypeRepository.getVoByEventOnlineStudyTypeVo(eventOnlineStudyTypeVo);
	}

	@Override
	public List<EventOnlineStudyTypeVo> getListByParentId(String parentId) {
		EventOnlineStudyTypeVo eventOnlineStudyTypeVo = new EventOnlineStudyTypeVo();
		eventOnlineStudyTypeVo.setParentId(parentId);
		eventOnlineStudyTypeVo.setStatus(1);
		return onlineStudyTypeRepository.getVoByEventOnlineStudyTypeVo(eventOnlineStudyTypeVo);
	}

	/**
	 * 更新状态
	 */
	@Override
	public Map<String, Object> updateStatus(String action, String ids, HttpServletRequest request) {
		String[] Ids = ids.split(",");
		Map<String, Object> result = new HashMap<String, Object>();

		/**
		 * 获取当前登录用户的session
		 */
		Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
		Long userId = 0L;
		if (loginMap != null && loginMap.get("staffId") != null) {
			userId = Long.parseLong(loginMap.get("staffId").toString());
		}
		Date updateTime = new Date();
		for (String idStr : Ids) {
			Long id = Long.parseLong(idStr);
			if (action.equals("start"))
				updateStart(id, userId, updateTime);
			else
				updateStop(id, userId, updateTime);
		}
		result.put("success", true);
		return result;
	}

	/**
	 * 启用
	 * 
	 * @param id
	 * @param userId
	 * @param updateTime
	 */
	private void updateStart(Long id, Long userId, Date updateTime) {

		// 获取本身
		EventOnlineStudyTypeDo eventOnlineStudyTypeDo = onlineStudyTypeRepository.getEventOnlineStudyTypeDoById(id);

		// 修改父亲
		if (eventOnlineStudyTypeDo.getParentId() != null) {
			EventOnlineStudyTypeDo parent = onlineStudyTypeRepository
					.getEventOnlineStudyTypeDoById(eventOnlineStudyTypeDo.getParentId());
			updateStart(parent.getId(), userId, updateTime);
		}

		eventOnlineStudyTypeDo.setStatus(1);
		eventOnlineStudyTypeDo.setUpdateTime(updateTime);
		eventOnlineStudyTypeDo.setUpdateBy(userId);
		eventOnlineStudyTypeDo.update();
	}

	/**
	 * 禁用
	 * 
	 * @param id
	 * @param userId
	 * @param updateTime
	 */
	private void updateStop(Long id, Long userId, Date updateTime) {

		// 获取本身
		EventOnlineStudyTypeDo eventOnlineStudyTypeDo = onlineStudyTypeRepository.getEventOnlineStudyTypeDoById(id);

		// 修改孩子
		List<EventOnlineStudyTypeDo> eventOnlineStudyTypeChildren = onlineStudyTypeRepository
				.getEventOnlineStudyTypeDoByParentId(eventOnlineStudyTypeDo.getId());
		for (EventOnlineStudyTypeDo eventOnlineStudyTypechild : eventOnlineStudyTypeChildren) {
			updateStop(eventOnlineStudyTypechild.getId(), userId, updateTime);
		}

		eventOnlineStudyTypeDo.setStatus(0);
		eventOnlineStudyTypeDo.setUpdateTime(updateTime);
		eventOnlineStudyTypeDo.setUpdateBy(userId);
		eventOnlineStudyTypeDo.update();
	}

	/**
	 * 删除
	 */
	public Map<String, Object> delete(String ids) {
		String[] Ids = ids.split(",");
		Map<String, Object> result = new HashMap<String, Object>();
		for (String idStr : Ids) {
			Long id = Long.parseLong(idStr);
			deleteById(id);
		}
		result.put("success", true);
		return result;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	private void deleteById(Long id) {
		// 修改孩子
		List<EventOnlineStudyTypeDo> eventOnlineStudyTypeChildren = onlineStudyTypeRepository
				.getEventOnlineStudyTypeDoByParentId(id);
		for (EventOnlineStudyTypeDo eventOnlineStudyTypechild : eventOnlineStudyTypeChildren) {
			deleteById(eventOnlineStudyTypechild.getId());
		}
		onlineStudyTypeRepository.delete(id);
	}

}
