package com.club.web.store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.store.service.GoodReceiptAddrService;
import com.club.web.store.vo.GoodReceiptAddrVo;

/**
 * 收货地址管理-Controller
 * 
 * @author wqh
 * @add by 2016-04-18
 */
@RequestMapping("/mobile/addr")
@Controller
public class GoodReceiptAddrController {
	private Logger logger = LoggerFactory.getLogger(GoodReceiptAddrController.class);
	@Autowired
	GoodReceiptAddrService receiptAddr;
	/**
	 * 操作结果返回信息
	 */
	private Map<String, Object> result;

	/**
	 * 新增收货地址信息
	 * 
	 * @param conditionStr
	 * @return Map<String, Object>
	 * @add by 2016-04-18
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> saveReceiptAddrCon(String conditionStr, HttpServletResponse response) {
		result = new HashMap<String, Object>();
		Map<String, Object> paramMap = null;
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/json;charset=utf-8");
			if (StringUtils.isNotEmpty(conditionStr)) {
				paramMap = JsonUtil.toMap(conditionStr);
				result = receiptAddr.saveReceiptAddrSer(paramMap);
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} catch (Exception e) {
			logger.error("新增收货地址信息异常<saveReceiptAddrCon>:", e);
			result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
			result.put(Constants.RESULT_MSG, "保存失败");
		}
		return result;
	}

	/**
	 * 查询收货地址列表
	 * 
	 * @param userId
	 * @return List<GoodReceiptAddrVo>
	 * @add by 2016-04-18
	 */
	@RequestMapping("/query")
	@ResponseBody
	public List<GoodReceiptAddrVo> queryReceiptAddrCon(String userId, HttpServletResponse response) {
		List<GoodReceiptAddrVo> list = null;
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/json;charset=utf-8");
			list = receiptAddr.queryReceiptAddrSer(userId);
		} catch (Exception e) {
			logger.error("查询收货地址列表异常<queryReceiptAddrCon>:", e);
		}
		return list;
	}

	/**
	 * 删除收货地址列表
	 * 
	 * @param userId
	 * @param ids
	 * @return Map<String, Object>
	 * @add by 2016-04-18
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> deleteReceiptAddrCon(String userId, String ids, HttpServletResponse response) {
		result = new HashMap<String, Object>();
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/json;charset=utf-8");
			result = receiptAddr.deleteReceiptAddrSer(userId, ids);
		} catch (Exception e) {
			logger.error("删除收货地址列表异常<deleteReceiptAddrCon>:", e);
			result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
			result.put(Constants.RESULT_MSG, "删除失败");
		}
		return result;
	}

	/**
	 * 修改设置默认地址
	 * 
	 * @param userId
	 * @param id
	 * @return Map<String, Object>
	 * @add by 2016-04-18
	 */
	@RequestMapping("/update_status")
	@ResponseBody
	public Map<String, Object> updateReceiptAddrStatusCon(String userId, String id, HttpServletResponse response) {
		result = new HashMap<String, Object>();
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/json;charset=utf-8");
			result = receiptAddr.updateReceiptAddrStatusSer(userId, id);
		} catch (Exception e) {
			logger.error("修改设置默认地址异常<updateReceiptAddrStatusCon>:", e);
			result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
			result.put(Constants.RESULT_MSG, "更新失败");
		}
		return result;
	}

	/**
	 * 修改地址信息
	 * 
	 * @param conditionStr
	 * @return Map<String, Object>
	 * @add by 2016-04-18
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> updateReceiptAddrCon(String conditionStr, HttpServletResponse response) {
		result = new HashMap<String, Object>();
		Map<String, Object> paramMap = null;
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/json;charset=utf-8");
			if (StringUtils.isNotEmpty(conditionStr)) {
				paramMap = JsonUtil.toMap(conditionStr);
				result = receiptAddr.updateReceiptAddrSer(paramMap);
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} catch (Exception e) {
			logger.error("修改地址信息异常<updateReceiptAddrCon>:", e);
			result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
			result.put(Constants.RESULT_MSG, "更新失败");
		}
		return result;
	}

}
