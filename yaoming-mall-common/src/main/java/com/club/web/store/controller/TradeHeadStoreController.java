package com.club.web.store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.exception.BaseAppException;
import com.club.framework.exception.SystemErrorCode;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.MessageResourceUtils;
import com.club.web.common.Constants.Status;
import com.club.web.common.service.IBaseService;
import com.club.web.stock.vo.CargoBrandVo;
import com.club.web.store.service.TradeHeadStoreService;
import com.club.web.store.vo.TradeHeadStoreVo;

/**
 * 
 * @Title: TradeHeadStoreController.java
 * @Package com.club.web.store.controller
 * @Description: 总店信息管理
 * @author 柳伟军
 * @date 2016年3月26日 上午9:26:41
 * @version V1.0
 */
@Controller
@RequestMapping("/trade/headStore")
public class TradeHeadStoreController {

	private Logger logger = LoggerFactory.getLogger(TradeHeadStoreController.class);

	@Autowired
	private TradeHeadStoreService tradeHeadStoreService;

	@Autowired
	private IBaseService baseService;

	/**
	 * 新增修改总店信息
	 * 
	 * @return
	 */
	@RequestMapping("/saveOrUpdateTradeHeadStore")
	@ResponseBody
	public Map<String, Object> saveOrUpdateTradeHeadStore(@RequestParam(value = "modelJson",required = true)String modelJson, HttpServletRequest request) {
		logger.debug("saveOrUpdateTradeHeadStore ");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (modelJson != null&&!"".equals(modelJson)) {
				TradeHeadStoreVo tradeHeadStoreVo = JsonUtil.toBean(modelJson, TradeHeadStoreVo.class);
				if (tradeHeadStoreVo == null) {
					result.put("success", false);
					result.put("msg", "总店信息不能为空！");
				} else {
					result.putAll(tradeHeadStoreService.saveOrUpdateTradeHeadStore(tradeHeadStoreVo, request));
				}
			} else {
				result.put("success", false);
				result.put("msg", "总店信息为空，请确认modelJson参数不为空，且为json格式");
			}
		} catch (Exception e) {
			logger.error("编辑总店异常<saveOrUpdateTradeHeadStoreCon>:", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
	}

	/**
	 * 删除总店信息
	 */
	@RequestMapping("/deleteTradeHeadStore")
	@ResponseBody
	public Map<String, Object> deleteTradeHeadStore(@RequestParam(value = "IdStr", required = true) String IdStr) {
		logger.debug("deleteTradeHeadStore ");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.putAll(tradeHeadStoreService.deleteTradeHeadStore(IdStr));
		} catch (Exception e) {
			logger.error("删除总店信息异常<deleteTradeHeadStoreCon>:", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
			
		    if (e instanceof DataIntegrityViolationException) {
		    	result.put("msg", "删除选项存在已经被引用，不能删除！");
	        }
			
		}
		return result;
	}

	/**
	 * 根据名称分页查询总店信息
	 * 
	 * @param page
	 *            前台的分页信息
	 * @param conditionStr
	 *            查询条件（json格式）
	 * @return
	 */
	@RequestMapping("/tradeHeadStorePage")
	@ResponseBody
	public Page<Map<String, Object>> tradeHeadStorePage(Page<Map<String, Object>> page, String conditionStr,
			 HttpServletRequest request) {

		// 如果查询条件不为空则添加查询条件
		if (conditionStr != null) {
			page.setConditons(JsonUtil.toMap(conditionStr));
		}
		// 根据查询分页信息和查询查询分页结果
		page = tradeHeadStoreService.queryTradeHeadStorePage(page,request);
		return page;
	}

	/**
	 * 更新总店状态
	 */
	@RequestMapping("/updateTradeHeadStoreStatue")
	@ResponseBody
	public Map<String, Object> updateTradeHeadStoreStatue(@RequestParam(value = "IdStr", required = true) String IdStr,
			@RequestParam(value = "statue", required = true) Long statue, HttpServletRequest request) {
		logger.debug("updateTradeHeadStoreStatue ");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.putAll(tradeHeadStoreService.updateTradeHeadStoreStatue(IdStr, statue, request));
		} catch (Exception e) {
			logger.error("更新总店状态异常<updateTradeHeadStoreStatue>:", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
	}

	/**
	 * 获取所有会员信息
	 */
	@RequestMapping("/getClient")
	@ResponseBody
	public Map<String, Object> getClient() {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> clientMap = new HashMap<String, Object>();
			Map<String, Object> conditions = new HashMap<String, Object>(2);
			conditions.put("staffState", Status.PUBLISH.toString());
			List<Map<String, Object>> list = baseService.selectList("staff_t", conditions);
			result.put("success", true);
			result.put("list", list);
		} catch (BaseAppException e) {
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
	}

}
