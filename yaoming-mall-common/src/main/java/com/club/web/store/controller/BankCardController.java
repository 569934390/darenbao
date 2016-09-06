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
import com.club.web.common.Constants;
import com.club.web.store.domain.emu.BankCardEMU;
import com.club.web.store.service.BankCardService;
import com.club.web.store.vo.BankCardVo;

/**
 * 
 * @author linxr
 *
 */
@Controller
@RequestMapping("/BankCard")
public class BankCardController {

	@Autowired
	private BankCardService bankCardService;

	@RequestMapping("/saveOrUpdateBankCard")
	@ResponseBody
	public Map<String, Object> saveOrUpdateBankCard(
			@RequestParam(value = "modelJson", required = true) String modelJson, HttpServletRequest request) {
		BankCardVo bankCardVo = JsonUtil.toBean(modelJson, BankCardVo.class);
		Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
		if (loginMap != null && loginMap.get("staffId") != null) {
			long staffId = Long.parseLong(loginMap.get("staffId").toString());
			if (bankCardVo.getConnectId() == null) {
				bankCardVo.setConnectId(staffId);
			}
		} else {
			return null;
		}

		if (bankCardVo != null && bankCardVo.getBankCardIdString() != "" && bankCardVo.getBankCardIdString() != null) {
			bankCardVo.setBankCardId(Long.parseLong(bankCardVo.getBankCardIdString()));
		}
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			result.put("result", bankCardService.saveOrUpdateBankCard(bankCardVo));
			result.put("msg", "编辑成功");
			result.put("success", true);

		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());

		}
		return result;
	}

	@RequestMapping("/getBankCardList")
	@ResponseBody
	public Page getBankCardList(@RequestParam(value = "start", required = true) int start,
			@RequestParam(value = "limit", required = true) int limit,
			@RequestParam(value = "conditionStr", required = false) String conditionStr,
			@RequestParam(value = "name", required = false) String name) {

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
		page = bankCardService.getBankCardList(page);
		return page;
	}

	@RequestMapping("/updateBankCardState")
	@ResponseBody
	public Map<String, Object> updateBankCardState(
			@RequestParam(value = "bankCardIds", required = true) String bankCardIds,
			@RequestParam(value = "action", required = true) String action, HttpServletRequest request) {
		Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
		Map<String, Object> result = new HashMap<String, Object>();
		if (loginMap != null && loginMap.get("staffId") != null) {
			long staffId = Long.parseLong(loginMap.get("staffId").toString());

		} else {
			return null;
		}

		try {
			String[] bankCardId = bankCardIds.split(",");

			bankCardService.updateBankCardState(bankCardId, action);
			result.put("msg", "修改成功");
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
		}

		return result;
	}

	@RequestMapping("/getBankCardTypeList")
	@ResponseBody
	public BankCardEMU[] getBankCardTypeList() {
		BankCardEMU[] bankCardType = bankCardService.getBankCardTypeList();
		return bankCardType;
	}

}
