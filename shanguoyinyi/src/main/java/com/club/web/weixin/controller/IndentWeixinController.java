package com.club.web.weixin.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.deal.exception.IndentException;
import com.club.web.deal.service.IndentService;
import com.club.web.deal.vo.IndentMobileVo;
import com.club.web.deal.vo.IndentVo;

/**
 * @Title: IndentWeixinController.java
 * @Package com.club.web.weixin.controller
 * @Description: TODO(微信端订单接口)
 * @author 柳伟军
 * @date 2016年5月20日 上午9:49:09
 * @version V1.0
 */
@RequestMapping("/weixin")
@Controller
public class IndentWeixinController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	IndentService indentService;

	/**
	 * 手机端查询用户个人非删除订单
	 * @param storeId 店铺ID
	 * @param buyerId 用户ID
	 * @param status 状态
	 * @param startIndex 
	 * @param pageSize
	 * @param response
	 * @return
	 */
	@RequestMapping("/indent/listByBuyerIdAndStoreId")
	@ResponseBody
	public List<IndentMobileVo> listByBuyerIdAndStoreId(String storeId,String buyerId, String status, Integer startIndex, Integer pageSize,
			HttpServletResponse response) {
		logger.debug("indent listByBuyerId ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try {
			if (StringUtils.isNotEmpty(buyerId)&&StringUtils.isNotEmpty(storeId)) {
				pageSize = pageSize == null ? 6 : pageSize;
				startIndex = startIndex != null && pageSize != null ? startIndex * pageSize : 0;
				return indentService.findVoMobileListByBuyerId(Long.valueOf(storeId),Long.valueOf(buyerId), status, startIndex, pageSize);
			}
		} catch (Exception e) {
			logger.error("indent listByBuyerId error:", e);
		}
		return null;
	}

	/**
	 * 手机端订单详情
	 * @param id
	 * @param response
	 * @return
	 */
	@RequestMapping("/indent/shipNotice")
	@ResponseBody
	public Map<String,Object> shipNotice(String id, HttpServletResponse response) {
		logger.debug("indent weiixn shipNotice ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String,Object> result = new HashMap<>();
		result.put("code", 1);
		result.put("msg", "发货提醒成功！");
		try {
			if (StringUtils.isNotEmpty(id)) {
				indentService.shipNotice(Long.valueOf(id));
			}
		} catch (IndentException e) {
			logger.error("indent weiixn shipNotice  error:", e);
			result.put("code", 1);
			result.put("msg", e.getMessage());
		}catch (Exception e) {
			logger.error("indent weiixn shipNotice  error:", e);
			result.put("code", 1);
			result.put("msg", "发货提醒失败！");
		}
		return result;
	}
	
	/**
	 * 手机端订单详情
	 * @param id
	 * @param response
	 * @return
	 */
	@RequestMapping("/indent/getOrderDetail")
	@ResponseBody
	public IndentVo getOrderDetailCon(String id, HttpServletResponse response) {
		logger.debug("indent weiixnDetail ");
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

	/**
	 * 手机端更新状态：取消，退款，收货
	 * 
	 * @param action
	 *            取消：cancel，退款申请：refund，收货：returned
	 * @param ids
	 * @param mapStr
	 * @param request
	 * @return
	 */
	@RequestMapping("/indent/update/{action}")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, Object> updateWeixinStatus(@PathVariable String action, String ids, String mapStr,
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("indent updateWeixinStatus ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constants.RESULT_CODE, 1);
		try {
			Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
			indentService.updateStatus(ids, action, loginMap, StringUtils.isNotEmpty(mapStr)
					? objectMapper.readValue(mapStr, Map.class) : new HashMap<String, Object>());
		}catch (IndentException e) {
			result.put(Constants.RESULT_CODE, 0);
			result.put(Constants.RESULT_MSG, e.getMessage());
			logger.error("indent updateWeixinStatus error:", e);
		}catch (Exception e) {
			result.put(Constants.RESULT_CODE, 0);
			result.put(Constants.RESULT_MSG, "订单更新状态失败！");
			logger.error("indent updateWeixinStatus error:", e);
		}
		return result;
	}

	/**
	 * 添加订单
	 * 
	 * @param modelJson
	 * @param response
	 * @return
	 */
	@RequestMapping("/indent/add")
	@ResponseBody
	public Map<String, Object> weixinAdd(String modelJson, HttpServletResponse response) {
		logger.debug("indent weixinAdd ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constants.RESULT_CODE, 1);
		try {
			IndentVo indentVo = JsonUtil.toBean(modelJson, IndentVo.class);
			if(indentVo != null && indentVo.getBuyType() == null){
				indentVo.setBuyType("z");
			}
			indentService.add(indentVo);
			result.put(Constants.RESULT_MSG, indentVo.getId());
		}catch(IndentException e){
			result.put(Constants.RESULT_CODE, 0);
			result.put(Constants.RESULT_MSG, e.getMessage());
			logger.error("indent weixinAdd error:", e);
		}catch(Exception e){
			result.put(Constants.RESULT_CODE, 0);
			result.put(Constants.RESULT_MSG, "订单生成失败！");
			logger.error("indent weixinAdd error:", e);
		}
		return result;
	}
	
	
	/**
	 * 未付款订单执行物理删除，已付款订单为逻辑删除
	 * @param ids
	 * @param response
	 * @return
	 */
	@RequestMapping("/indent/delete")
	@ResponseBody
	public Map<String, Object> weixinDelete(String ids, HttpServletResponse response) {
		logger.debug("indent weixinDelete ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constants.RESULT_CODE, 1);
		try {
			indentService.delete(ids);
			result.put(Constants.RESULT_MSG, "删除成功！");
		} catch (Exception e) {
			result.put(Constants.RESULT_CODE, 0);
			result.put(Constants.RESULT_MSG, e.getMessage());
			logger.error("indent mobileAdd error:", e);
		}
		return result;
	}
}
