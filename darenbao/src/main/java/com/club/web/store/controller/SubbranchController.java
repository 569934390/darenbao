package com.club.web.store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.web.common.Constants;
import com.club.web.store.service.SubbranchService;
import com.club.web.store.vo.SubbranchGoodSoldoutVo;
import com.club.web.store.vo.SubbranchVo;
import com.club.web.weixin.config.WeixinGlobal;

/**
 * 
 * @author linxr
 *
 */

@Controller
@RequestMapping("/Subbranch")
public class SubbranchController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SubbranchService subbranchService;
	
	
	@RequestMapping("getSubbranchQrcode")
	@ResponseBody
	public String getSubbranchQrcode(String id) {
		String qrcode="";
		try {
			if(id!=null&&!"".equals(id)){
				return WeixinGlobal.getQrcodeStoreTemplate().replace("{storeId}", id);
			}
		} catch (Exception e) {
			
		}
		return qrcode;
	}

	
	

	@RequestMapping("deleteSubbranch")
	@ResponseBody
	public Map<String, Object> deleteSubbranch(
			@RequestParam(value = "subbranchIds", required = true) String subbranchIds) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			subbranchService.deleteSubbranch(subbranchIds);
			result.put("msg", "删除成功");
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());

			if (e instanceof DataIntegrityViolationException) {
				result.put("msg", "删除选择项已经被引用，不能删除！");
			}
		}
		return result;
	}

	@RequestMapping("/saveOrUpdateSubbranch")
	@ResponseBody
	public Map<String, Object> saveOrUpdateSubbranch(
			@RequestParam(value = "modelJson", required = true) String modelJson,String provinceName,String cityName,String countryName,
			@RequestParam(value = "levelId", required = true) String levelId, HttpServletRequest request) {
		SubbranchVo subbranchVo = JsonUtil.toBean(modelJson, SubbranchVo.class);
		if(subbranchVo!=null){
			subbranchVo.setProvinceName(provinceName);
			subbranchVo.setCityName(cityName);
			subbranchVo.setCountryName(countryName);
		}
		if (levelId != "" && levelId != null) {
			subbranchVo.setLevelId(levelId);
		}
		Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
		if (loginMap != null && loginMap.get("staffId") != null) {
			long staffId = Long.parseLong(loginMap.get("staffId").toString());
			if (subbranchVo.getCreateBy() == null ||subbranchVo.getCreateBy().equals("null")) {
				subbranchVo.setCreateBy(staffId + "");
			}
		} else {
			return null;
		}

		Map<String, Object> result = new HashMap<String, Object>();
		try {

			result = subbranchService.saveOrUpdateSubbranch(subbranchVo, request);
			result.put("msg", "编辑成功");
			result.put("success", true);

		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());

		}
		return result;
	}

	@RequestMapping("/getSubbranchListForActivity")
	@ResponseBody
	public List<SubbranchVo> getSubbranchListForActivity() {
		List<SubbranchVo> list = null;
		try {
			list = subbranchService.getSubbranchListForActivity();
		} catch (Exception e) {
			logger.error("cargoclassify queryByParentId error:", e);
		}
		return list;
	}
	

	@RequestMapping("/getSubbranchList")
	@ResponseBody
	public Page getSubbranchList(@RequestParam(value = "start", required = true) int start,
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
		page = subbranchService.getSubbranchList(page);

		return page;
	}

	@RequestMapping("/getSubbranceName")
	@ResponseBody
	public Page getSubbranceName(@RequestParam(value = "start", required = true) int start,
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
		page = subbranchService.getSubbranceName(page);

		return page;
	}

	@RequestMapping("/updateSubbranchState")
	@ResponseBody
	public Map<String, Object> updateSubbranchState(
			@RequestParam(value = "SubbranchIds", required = true) String SubbranchIds,
			@RequestParam(value = "action", required = true) String action, HttpServletRequest request) {
		Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
		Map<String, Object> result = new HashMap<String, Object>();
		if (loginMap != null && loginMap.get("staffId") != null) {
			long staffId = Long.parseLong(loginMap.get("staffId").toString());

		} else {
			return null;
		}

		try {
			String[] subbranchId = SubbranchIds.split(",");

			subbranchService.updateSubbranchState(subbranchId, action);
			result.put("msg", "编辑成功");
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
		}

		return result;
	}

	@RequestMapping("saveSubbranchGoodsSoldout")
	@ResponseBody
	public Map<String, Object> saveSubbranchGoodsSoldout(
			@RequestParam(value = "modelJson", required = true) String modelJson, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		SubbranchGoodSoldoutVo subbranchGoodSoldoutVo = JsonUtil.toBean(modelJson, SubbranchGoodSoldoutVo.class);
		Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
		if (loginMap != null && loginMap.get("staffId") != null) {

		} else {

		}
		try {
			result = subbranchService.saveSubbranchGoodsSoldout(subbranchGoodSoldoutVo);
			result.put("msg", "编辑成功");
			result.put("success", true);

		} catch (Exception e) {
			result.put("result", "fail");
			result.put("msg", e.getMessage());

		}

		return result;

	}

	@RequestMapping("/getSubbranchGoodsSoldoutList")
	@ResponseBody
	public Page<Object> getSubbranchGoodsSoldoutList(@RequestParam(value = "start", required = true) int start,
			@RequestParam(value = "limit", required = true) int limit,
			@RequestParam(value = "conditionStr", required = false) String conditionStr) {

		Page<Object> page = new Page<>();
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
		try {
			page = subbranchService.getSubbranchGoodsSoldoutList(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	@RequestMapping("deleteSubbranchGoodsSoldout")
	@ResponseBody
	public Map<String, Object> deleteSubbranchGoodsSoldout(
			@RequestParam(value = "goodIds", required = true) String goodIds,
			@RequestParam(value = "subbranchId", required = true) Long subbranchId) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			subbranchService.deleteSubbranchGoodsSoldout(subbranchId, goodIds);
			result.put("msg", "删除成功");
			result.put("success", true);

		} catch (Exception e) {
			result.put("result", "fail");
			result.put("msg", e.getMessage());

		}

		return result;
	}

}
