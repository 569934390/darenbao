package com.club.web.store.service.impl;

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
import com.club.web.store.domain.StoreLevelDo;
import com.club.web.store.domain.repository.StoreLevelRepository;
import com.club.web.store.domain.repository.TradeHeadStoreRepository;
import com.club.web.store.service.StoreLevelService;
import com.club.web.store.vo.StoreLevelVo;
import com.club.web.store.vo.TradeHeadStoreVo;
import com.club.web.util.CommonUtil;
import com.club.web.util.IdGenerator;

@Service("StoreLevelService")
@Transactional
public class StoreLevelServiceImpl implements StoreLevelService {

	private Logger logger = LoggerFactory.getLogger(StoreLevelServiceImpl.class);

	@Autowired
	private StoreLevelRepository storeLevelRepository;

	@Autowired
	private TradeHeadStoreRepository tradeHeadStoreRepository;

	/**
	 * 新增修改店铺等级信息
	 */
	public Map<String, Object> saveOrUpdateStoreLevel(StoreLevelVo storeLevelVo, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (storeLevelVo != null) {
			if (null == storeLevelVo.getName() || "".equals(storeLevelVo.getName())) {
				result.put("success", false);
				result.put("msg", "请输入店铺等级名称");
				return result;
			}

			// 判断是否有总店
			/**
			 * 获取当前登录用户的session
			 */
			Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);

			// 根据名称查询等级信息
			List<StoreLevelVo> storeLevelByName = storeLevelRepository.queryStoreLevelByName(storeLevelVo.getName());
			int count = storeLevelByName.size();
			if (storeLevelVo.getLevelId() == null || "".equals(storeLevelVo.getLevelId())) {

				if (count > 0) {
					result.put("success", false);
					result.put("msg", "该店铺等级名称已经存在!");
					return result;
				}
				storeLevelVo.setLevelId(IdGenerator.getDefault().nextId() + "");
				// 获取当前登录用的所绑定的总店Id
				storeLevelVo.setCreateTime(new Date());
				if (loginMap != null && loginMap.get("staffId") != null) {
					storeLevelVo.setCreateBy(Long.parseLong(loginMap.get("staffId").toString()));
				}
				storeLevelVo.setUpdateTime(new Date());
				if (loginMap != null && loginMap.get("staffId") != null) {
					storeLevelVo.setUpdateBy(Long.parseLong(loginMap.get("staffId").toString()));
				}
				storeLevelVo.setStatue(1L);
				StoreLevelDo storeLevelDo = storeLevelRepository.create(storeLevelVo);
				storeLevelDo.insert();

			} else {
				StoreLevelDo storeLevelDo = storeLevelRepository
						.getStoreLevelDoByLevelId(Long.parseLong(storeLevelVo.getLevelId()));
				if (count > 0 && !storeLevelDo.getName().equalsIgnoreCase(storeLevelVo.getName())) {
					result.put("success", false);
					result.put("msg", "该店铺等级名称已经存在!");
					return result;
				}
				storeLevelDo.setName(storeLevelVo.getName());
				storeLevelDo.setStorePro(storeLevelVo.getStorePro());
				storeLevelDo.setUpdateTime(new Date());
				if (loginMap != null && loginMap.get("staffId") != null) {
					storeLevelDo.setUpdateBy(Long.parseLong(loginMap.get("staffId").toString()));
				}
				storeLevelDo.update();
			}
			result.put("success", true);
		} else {
			result.put("success", false);
			result.put("msg", "店铺等级不能为空");
		}
		return result;
	}

	/**
	 * 查询等级分页信息
	 */
	@Override
	public Page<Map<String, Object>> queryStoreLevelPage(Page<Map<String, Object>> page, HttpServletRequest request) {
		Page<Map<String, Object>> result = new Page<Map<String, Object>>();
		result.setStart(page.getStart());
		result.setLimit(page.getLimit());
		List<Map<String, Object>> list = storeLevelRepository.queryStoreLevelPage(page, request);
		Long count = storeLevelRepository.queryStoreLevelCountPage(page, request);
		result.setResultList(list);
		result.setTotalRecords(count.intValue());
		return result;
	}

	/**
	 * 更新店铺等级状态
	 */
	public Map<String, Object> updateStoreLevelStatue(String idStr, Long statue, HttpServletRequest request) {
		String[] Ids = idStr.split(",");
		Map<String, Object> result = new HashMap<String, Object>();
		/**
		 * 获取当前登录用户的session
		 */
		Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
		for (String id : Ids) {
			StoreLevelDo storeLevelDo = storeLevelRepository.getStoreLevelDoById(Long.parseLong(id));
			storeLevelDo.setStatue(statue);
			storeLevelDo.setUpdateTime(new Date());
			if (loginMap != null && loginMap.get("staffId") != null) {
				storeLevelDo.setUpdateBy(Long.parseLong(loginMap.get("staffId").toString()));
			}
			storeLevelDo.update();
		}
		result.put("success", true);
		return result;
	}

	/**
	 * 删除店铺等级
	 */
	public Map<String, Object> deleteStoreLevel(String idStr) {
		String[] Ids = idStr.split(",");
		Map<String, Object> result = new HashMap<String, Object>();
		for (String id : Ids) {
			storeLevelRepository.deleteByPrimaryKey(Long.parseLong(id));
		}
		result.put("success", true);
		return result;
	}

	/**
	 * 获取当前用户绑定总店有效的店铺等级
	 * 
	 * @return
	 */
	public List<StoreLevelVo> findAllStoreLevel(HttpServletRequest request) {
		List<StoreLevelVo> levelVoList = null;
		levelVoList = storeLevelRepository.findAllStoreLevel(request);
		return levelVoList;
	}

}
