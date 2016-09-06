package com.club.web.store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.web.common.Constants;
import com.club.web.store.service.TradeEexpressageService;
import com.club.web.store.vo.TradeEexpressageVo;

@Controller
@RequestMapping("Expressage")
public class TradeExpressageControl {

	@Autowired
	TradeEexpressageService tradeEexpressageService;

	@RequestMapping("saveOrUpdateExpressage")
	@ResponseBody
	public Map<String, Object> saveTradeExpressage(
			@RequestParam(value = "modelJson", required = true) String modelJson) {

		TradeEexpressageVo tradeEexpressageVo = JsonUtil.toBean(modelJson, TradeEexpressageVo.class);
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			result = tradeEexpressageService.saveOrUpdateExpressage(tradeEexpressageVo);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());

		}
		return result;
	}

	@RequestMapping("/getExpressageList")
	@ResponseBody
	public Page getExpressageList(@RequestParam(value = "start", required = true) int start,
			@RequestParam(value = "limit", required = true) int limit,
			@RequestParam(value = "conditionStr", required = false) String conditionStr) {

		Map<String, Object> result = new HashMap<String, Object>();
		Page page = new Page<>();
		if (conditionStr != null) {
			Map<String, Object> conditionMap = JsonUtil.toMap(conditionStr);

			page.setConditons(conditionMap);
		}

		if (limit >= start) {
			page.setLimit(limit);
			page.setStart(start);
		} else {
			page.setLimit(start);
			page.setStart(limit);
		}
		page = tradeEexpressageService.getExpressageList(page);

		return page;
	}
	
	@RequestMapping("/getExpressageUseList")
	@ResponseBody
	public List<TradeEexpressageVo> getExpressageUseList(@RequestParam(value = "start", required = true) int start,
			@RequestParam(value = "limit", required = true) int limit) {

		Map<String, Object> result = new HashMap<String, Object>();
		Page page = new Page<>();

		if (limit >= start) {
			page.setLimit(limit);
			page.setStart(start);
		} else {
			page.setLimit(start);
			page.setStart(limit);
		}
		List<TradeEexpressageVo> tradeEexpressageVo= tradeEexpressageService.getExpressageUseList(page);

		return tradeEexpressageVo;
	}

	@RequestMapping("deleteExpressage")
	@ResponseBody
	public Map<String, Object> deleteExpressage(
			@RequestParam(value = "expressageIds", required = true) String expressageIds) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String[] expressageId = expressageIds.split(",");
			tradeEexpressageService.deleteExpressage(expressageId);
			result.put("msg", "删除成功");
			result.put("success", true);

		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());

		}

		return result;
	}

	@RequestMapping("/updateExpressageState")
	@ResponseBody
	public Map<String, Object> updateExpressageState(
			@RequestParam(value = "expressageIds", required = true) String expressageIds,
			@RequestParam(value = "action", required = true) String action, HttpServletRequest request) {
		Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
		Map<String, Object> result = new HashMap<String, Object>();
		if (loginMap != null && loginMap.get("staffId") != null) {
			long staffId = Long.parseLong(loginMap.get("staffId").toString());

		} else {
			return null;
		}

		try {
			String[] expressageId = expressageIds.split(",");

			tradeEexpressageService.updateExpressageState(expressageId, action);
			result.put("success", true);
			result.put("msg", "修改成功");
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
		}

		return result;
	}

}
