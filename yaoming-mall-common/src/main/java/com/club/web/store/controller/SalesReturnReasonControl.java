package com.club.web.store.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.store.service.SalesReturnReasonService;
import com.club.web.store.vo.SalesReturnReasonVo;

@Controller
@RequestMapping("SalesReturnReason")
public class SalesReturnReasonControl {
	@Autowired
	SalesReturnReasonService salesReturnReasonService;

	@RequestMapping("saveOrUpdateSalesReturnReason")
	@ResponseBody
	public Map<String, Object> saveOrUpdateSalesReturnReason(
			@RequestParam(value = "modelJson", required = true) String modelJson) {
		SalesReturnReasonVo salesReturnReasonVo = JsonUtil.toBean(modelJson, SalesReturnReasonVo.class);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (salesReturnReasonVo != null) {
				result = salesReturnReasonService.saveOrUpdateSalesReturnReason(salesReturnReasonVo);
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());

		}
		return result;
	}

	@RequestMapping("getSalesReturnReason")
	@ResponseBody
	public Page getSalesReturnReason(@RequestParam(value = "limit", required = true) int limit,
			@RequestParam(value = "start", required = true) int start, HttpServletRequest request) {
		Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
		
		Page page = new Page();
		if (loginMap != null && loginMap.get("staffId") != null) {
			long staffId = Long.parseLong(loginMap.get("staffId").toString());

		} else {
			return null;
		}
		page.setLimit(limit);
		page.setStart(start);
		try {
			page = salesReturnReasonService.getSalesReturnReason(page);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	@RequestMapping("deletSalesReturnReason")
	@ResponseBody
	public Map<String, Object> deletSalesReturnReason(
			@RequestParam(value = "ids", required = true) String ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (!StringUtils.isEmpty(ids)) {
				result = salesReturnReasonService.deletSalesReturnReason(ids);
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());

		}
		
		return result;
	}
}
