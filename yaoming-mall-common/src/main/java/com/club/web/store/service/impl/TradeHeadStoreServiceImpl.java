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
import com.club.framework.util.JsonUtil;
import com.club.web.common.Constants;
import com.club.web.stock.domain.CargoSupplierDo;
import com.club.web.stock.vo.CargoBrandVo;
import com.club.web.stock.vo.CargoSupplierVo;
import com.club.web.store.domain.TradeHeadStoreDo;
import com.club.web.store.domain.repository.TradeHeadStoreRepository;
import com.club.web.store.service.TradeHeadStoreService;
import com.club.web.store.vo.TradeHeadStoreVo;
import com.club.web.util.CommonUtil;
import com.club.web.util.IdGenerator;

/**
 * @Title: TradeHeadStoreServiceImpl.java
 * @Package com.club.web.store.service.impl
 * @Description: 总店ServiceImpl
 * @author 柳伟军
 * @date 2016年3月26日 上午9:28:10
 * @version V1.0
 */
@Service("tradeHeadStoreService")
@Transactional
public class TradeHeadStoreServiceImpl implements TradeHeadStoreService {

	private Logger logger = LoggerFactory.getLogger(TradeHeadStoreServiceImpl.class);

	@Autowired
	private TradeHeadStoreRepository tradeHeadStoreRepository;

	/**
	 * 根据Id获取总店信息
	 */
	public TradeHeadStoreVo getTradeHeadStoreVoById(Long id) {
		TradeHeadStoreVo headStoreVo = null;
		headStoreVo = tradeHeadStoreRepository.getTradeHeadStoreVoById(id);
		return headStoreVo;
	}

	/**
	 * 保存或者更新总店信息
	 */
	public Map<String, Object> saveOrUpdateTradeHeadStore(TradeHeadStoreVo tradeHeadStoreVo,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (tradeHeadStoreVo != null) {
			if (null == tradeHeadStoreVo.getName() || "".equals(tradeHeadStoreVo.getName())) {
				result.put("success", false);
				result.put("msg", "请输入总店名称");
				return result;
			}
			if (!"".equals(tradeHeadStoreVo.getTel()) && (!(CommonUtil.isPhone(tradeHeadStoreVo.getTel())
					|| CommonUtil.isMobile(tradeHeadStoreVo.getTel())))) {
				result.put("success", false);
				result.put("msg", "请输入正确的联系方式");
				return result;
			}
			// 根据拥有者查询
			List<TradeHeadStoreVo> tradeHeadStoreByOwner = tradeHeadStoreRepository
					.queryTradeHeadStoreByOwner(tradeHeadStoreVo.getOwner());
			int conutOwner = tradeHeadStoreByOwner.size();
			List<TradeHeadStoreVo> tradeHeadStoreByName = tradeHeadStoreRepository
					.queryTradeHeadStoreByName(tradeHeadStoreVo.getName());
			int conutName = tradeHeadStoreByName.size();
			/**
			 * 获取当前登录用户的session
			 */
			Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
			if (tradeHeadStoreVo.getId() == null || "".equals(tradeHeadStoreVo.getId())) {
				if (conutName > 0) {
					result.put("success", false);
					result.put("msg", "该总店名称已经存在!");
					return result;
				}

				if (conutOwner > 0) {
					result.put("success", false);
					result.put("msg", "该店主已经绑定了总店,不能重复绑定！");
					return result;
				}

				tradeHeadStoreVo.setId(IdGenerator.getDefault().nextId() + "");
				tradeHeadStoreVo.setCreateTime(new Date());
				if (loginMap != null && loginMap.get("staffId") != null) {
					tradeHeadStoreVo.setCreateBy(Long.parseLong(loginMap.get("staffId").toString()));
				}
				tradeHeadStoreVo.setUpdateTime(new Date());
				if (loginMap != null && loginMap.get("staffId") != null) {
					tradeHeadStoreVo.setUpdateBy(Long.parseLong(loginMap.get("staffId").toString()));
				}
				tradeHeadStoreVo.setStatue(1L);
				TradeHeadStoreDo tradeHeadStoreDo = tradeHeadStoreRepository.create(tradeHeadStoreVo);
				tradeHeadStoreDo.insert();
			} else {
				TradeHeadStoreDo tradeHeadStoreDo = tradeHeadStoreRepository
						.getTradeHeadStoreDoById(Long.parseLong(tradeHeadStoreVo.getId()));
				if (conutName > 0 && !tradeHeadStoreDo.getName().equalsIgnoreCase(tradeHeadStoreVo.getName())) {
					result.put("success", false);
					result.put("msg", "该总店名称已经存在!");
					return result;
				}

				if (conutOwner > 0 && tradeHeadStoreDo.getOwner() != tradeHeadStoreVo.getOwner()) {
					result.put("success", false);
					result.put("msg", "该拥有着已经绑定了总店,不能重复绑定！");
					return result;
				}
				tradeHeadStoreDo.setName(tradeHeadStoreVo.getName());
				tradeHeadStoreDo.setTel(tradeHeadStoreVo.getTel());
				tradeHeadStoreDo.setOwner(tradeHeadStoreVo.getOwner());
				tradeHeadStoreDo.setUpdateTime(new Date());
				if (loginMap != null && loginMap.get("staffId") != null) {
					tradeHeadStoreDo.setUpdateBy(Long.parseLong(loginMap.get("staffId").toString()));
				}
				tradeHeadStoreDo.update();
			}
			result.put("success", true);
		} else {
			result.put("success", false);
			result.put("msg", "总店信息不能为空");
		}

		return result;
	}

	/**
	 * 删除总店信息
	 */
	public Map<String, Object> deleteTradeHeadStore(String idStr) {
		String[] Ids = idStr.split(",");
		Map<String, Object> result = new HashMap<String, Object>();
		for (String id : Ids) {
			tradeHeadStoreRepository.deleteByPrimaryKey(Long.parseLong(id));
		}
		result.put("success", true);
		return result;
	}

	/**
	 * 分页查询总店基础信息
	 */
	public Page<Map<String, Object>> queryTradeHeadStorePage(Page<Map<String, Object>> page,
			HttpServletRequest request) {
		Page<Map<String, Object>> result = new Page<Map<String, Object>>();
		result.setStart(page.getStart());
		result.setLimit(page.getLimit());
		List<Map<String, Object>> list = tradeHeadStoreRepository.queryTradeHeadStorePage(page, request);
		Long count = tradeHeadStoreRepository.queryTradeHeadStoreCountPage(page, request);
		result.setResultList(list);
		result.setTotalRecords(count.intValue());
		return result;
	}

	/**
	 * 更新总店状态
	 */
	public Map<String, Object> updateTradeHeadStoreStatue(String idStr, Long statue, HttpServletRequest request) {
		String[] Ids = idStr.split(",");
		Map<String, Object> result = new HashMap<String, Object>();
		/**
		 * 获取当前登录用户的session
		 */
		Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
		for (String id : Ids) {
			TradeHeadStoreDo tradeHeadStoreDo = tradeHeadStoreRepository.getTradeHeadStoreDoById(Long.parseLong(id));
			tradeHeadStoreDo.setStatue(statue);
			tradeHeadStoreDo.setUpdateTime(new Date());
			if (loginMap != null && loginMap.get("staffId") != null) {
				tradeHeadStoreDo.setUpdateBy(Long.parseLong(loginMap.get("staffId").toString()));
			}
			tradeHeadStoreDo.update();
		}
		result.put("success", true);
		return result;
	}

	/**
	 * 获取当前用户绑定的总店
	 * 
	 * @param request
	 * @return
	 */
	public Long getStaffHeadStoreId(HttpServletRequest request) {
		Long id = null;
		/**
		 * 获取当前登录用户的session
		 */
		Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);

		// 判断当前用户是否绑定总店
		List<TradeHeadStoreVo> tradeHeadStoreByOwner = null;
		if (loginMap != null && loginMap.get("staffId") != null) {
			tradeHeadStoreByOwner = tradeHeadStoreRepository
					.queryTradeHeadStoreByOwner(Long.parseLong(loginMap.get("staffId").toString()));
		}
		if (tradeHeadStoreByOwner == null || tradeHeadStoreByOwner.size() <= 0)
			id = null;
		else
			id = Long.parseLong(tradeHeadStoreByOwner.get(0).getId());
		return id;
	}

}
