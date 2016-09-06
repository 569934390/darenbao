package com.club.web.deal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.deal.exception.IndentException;
import com.club.web.deal.service.IndentService;
import com.club.web.deal.vo.IndentExtendVo;
import com.club.web.deal.vo.IndentVo;

/**
 * 订单控制类
 * 
 * @author zhuzd
 *
 */
@Controller
@RequestMapping("/deal/indent")
public class IndentController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IndentService indentService;

	@RequestMapping("/detail")
	@ResponseBody
	public IndentVo detail(String id, HttpServletResponse response) {
		response.setContentType("text/json;charset=utf-8");
		try {
			if (StringUtils.isNotEmpty(id)) {
				return indentService.findVoById(Long.valueOf(id));
			}
		} catch (Exception e) {
			logger.error("indent detail error:", e);
		}
		return null;
	}

	@RequestMapping("/mobile/detail/{id}")
	@ResponseBody
	public IndentVo mobileDetail(@PathVariable String id, HttpServletResponse response) {
		logger.debug("indent mobileDetail ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try {
			if (StringUtils.isNotEmpty(id)) {
				return indentService.findVoById(Long.valueOf(id));
			}
		} catch (Exception e) {
			logger.error("indent mobileDetail error:", e);
		}
		return null;
	}

	/**
	 * 查询店铺的订单列表
	 * 
	 * @param shopId
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @return List<IndentMobileVo>
	 * @add by 2016-05-13
	 * 
	 */
	@RequestMapping("/mobile/getShopOrderList")
	@ResponseBody
	public List<IndentExtendVo> getShopOrderListCon(String shopId, String status,
			@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "6") int pageSize,
			HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		List<IndentExtendVo> list = null;
		try {
			if (pageNum < 1) {
				pageNum = 1;
			}
			if (pageSize < 1) {
				pageSize = 6;
			}
			list = indentService.getShopOrderListSer(Long.valueOf(shopId),null, status, (pageNum - 1) * pageSize, pageSize);
		} catch (Exception e) {
			logger.error("indent getShopOrderListCon error:", e);
		}
		return list;
	}

	/**
	 * 店铺查询订单详情
	 * @param id
	 * @param response
	 * @return
	 */
	@RequestMapping("/mobile/getOrderDetail")
	@ResponseBody
	public IndentVo getOrderDetailCon(String id, HttpServletResponse response) {
		logger.debug("indent mobileDetail ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try {
			if (StringUtils.isNotEmpty(id)) {
				return indentService.findVoById(Long.valueOf(id));
			}
		} catch (Exception e) {
			logger.error("indent getOrderDetailCon error:", e);
		}
		return null;
	}

	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> list(Page<Map<String, Object>> page, String conditionStr) {
		logger.debug("indent list ");
		Map<String,Object> result = new HashMap<>();
		result.put(Constants.RESULT_CODE, 1);
		try {
			if (StringUtils.isNotEmpty(conditionStr) && page != null) {
				page.setConditons(JsonUtil.toMap(conditionStr));
			}
			result.putAll(indentService.list(page));
		} catch (Exception e) {
			result.put(Constants.RESULT_CODE, 0);
			result.put(Constants.RESULT_MSG, e.getMessage());
			logger.error("indent list error:", e);
		}
		return result;
	}
	
	@RequestMapping("/cargoCheck/list")
	@ResponseBody
	public Page<Map<String, Object>> cargoChecklist(Page<Map<String, Object>> page, String conditionStr) {
		logger.debug("indent cargoCheck list ");
		try {
			if (StringUtils.isNotEmpty(conditionStr) && page != null) {
				page.setConditons(JsonUtil.toMap(conditionStr));
			}
			page = indentService.cargoChecklist(page);
		} catch (Exception e) {
			logger.error("indent cargoCheck list error:", e);
		}
		return page;
	}

	@RequestMapping("/cargoCheck/count")
	@ResponseBody
	public int cargoCheckCount() {
		logger.debug("indent cargoCheck list ");
		int count = 0;
		try {
			count = indentService.cargoCheckCount();
		} catch (Exception e) {
			logger.error("indent cargoCheck list error:", e);
		}
		return count;
	}

	@RequestMapping("/update/{action}")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, Object> updateStatus(@PathVariable String action, String ids, String mapStr,
			HttpServletRequest request) {
		logger.debug("indent updateStatus ");
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constants.RESULT_CODE, 1);
		try {
			Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
			indentService.updateStatus(ids, action, loginMap,
					StringUtils.isNotEmpty(mapStr) ? objectMapper.readValue(mapStr, Map.class)
							: new HashMap<String, Object>());
		} catch (Exception e) {
			result.put(Constants.RESULT_CODE, 0);
			result.put(Constants.RESULT_MSG,  "订单更新状态失败！");
			logger.error("indent updateStatus error:", e);
		}
		return result;
	}

	/**
	 * 手机端更新状态：取消，退款，收货
	 * 
	 * @param action
	 *            取消：cancel，退款申请：refund，收货：received
	 * @param ids
	 * @param mapStr
	 * @param request
	 * @return
	 */
	@RequestMapping("/mobile/update/{action}")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, Object> updateMobileStatus(@PathVariable String action, String ids, String mapStr,
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("indent updateMobileStatus ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constants.RESULT_CODE, 1);
		try {
			Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
			indentService.updateStatus(ids, action, loginMap,
					StringUtils.isNotEmpty(mapStr) ? objectMapper.readValue(mapStr, Map.class)
							: new HashMap<String, Object>());
		} catch (Exception e) {
			result.put(Constants.RESULT_CODE, 0);
			result.put(Constants.RESULT_MSG, "订单更新状态失败！");
			logger.error("indent updateMobileStatus error:", e);
		}
		return result;
	}

	@RequestMapping("/mobile/add")
	@ResponseBody
	public Map<String, Object> mobileAdd(String modelJson, HttpServletResponse response) {
		logger.debug("indent mobileAdd ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constants.RESULT_CODE, 1);
		try {
			IndentVo indentVo = JsonUtil.toBean(modelJson, IndentVo.class);
			indentService.add(indentVo);
			result.put(Constants.RESULT_MSG, indentVo.getId());
		}catch(IndentException e){
			result.put(Constants.RESULT_CODE, 0);
			result.put(Constants.RESULT_MSG, e.getMessage());
			logger.error("indent mobileAdd error:", e);
		}catch(Exception e){
			result.put(Constants.RESULT_CODE, 0);
			result.put(Constants.RESULT_MSG, "订单生成失败！");
			logger.error("indent mobileAdd error:", e);
		}
		return result;
	}
	
	/**
	 * 导出列表
	 * @return
	 */
	@RequestMapping("/exportExcel")
	public void exportExcel(String condition,HttpServletResponse response){
		logger.debug("indent exportExcel");
		try {
			indentService.exportExcel(condition,response);
        } catch (Exception e) {
            logger.error("indent exportExcel",e);
        }
	}

}
