package com.club.web.weixin.controller;

import java.math.BigDecimal;
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
import com.club.web.store.service.CarouselImgService;
import com.club.web.store.service.CarriageRuleService;
import com.club.web.store.service.DeliveryAddressService;
import com.club.web.store.service.SalesReturnReasonService;
import com.club.web.store.service.ThemeManagerService;
import com.club.web.store.vo.CarouselImgPrevVo;
import com.club.web.store.vo.DeliveryAddressVo;
import com.club.web.store.vo.SalesReturnReasonVo;
import com.club.web.store.vo.ShopThemeExtendVo;

/**
 * 广告管理-Controller
 * 
 * @author wqh
 * @add by 2016-04-15
 */
@RequestMapping("/weixin/common")
@Controller
public class CommonController {
	private Logger logger = LoggerFactory.getLogger(CommonController.class);
	@Autowired
	CarouselImgService carousel;
	@Autowired
	ThemeManagerService theme;
	@Autowired
	SalesReturnReasonService salesReturnReasonService;
	@Autowired
	DeliveryAddressService deliveryAddressService;
	@Autowired
	private CarriageRuleService carriageRuleService;

	/**
	 * 查询主题列表
	 * 
	 * @param category
	 * @param response
	 * @return List<CarouselImgPrevVo>
	 * @add by 2016-04-15
	 */
	@RequestMapping("/getTheme")
	@ResponseBody
	public List<ShopThemeExtendVo> getThemeCon(String category, HttpServletResponse response) {
		List<ShopThemeExtendVo> list = null;
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/json;charset=utf-8");
			list = theme.queryPrevThemeSer();
		} catch (Exception e) {
			logger.error("查询主题列表异常<getThemeCon>:", e);
		}
		return list;
	}

	/**
	 * 查询轮播图列表
	 * 
	 * @param category
	 * @param response
	 * @return List<CarouselImgPrevVo>
	 * @add by 2016-04-15
	 */
	@RequestMapping("/getImg")
	@ResponseBody
	public List<CarouselImgPrevVo> getCarouselImgCon(String category, HttpServletResponse response) {
		List<CarouselImgPrevVo> list = null;
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/json;charset=utf-8");
			list = carousel.getCarouselByCatory(category);
		} catch (Exception e) {
			logger.error("查询轮播图列表异常<getCarouselImgCon>:", e);
		}
		return list;
	}

	/**
	 * 根据Id查询轮播图富文本
	 * 
	 * @param category
	 * @param response
	 * @return String
	 * @add by 2016-04-15
	 */
	@RequestMapping("/getImgRichText")
	@ResponseBody
	public Map<String, Object> getImgRichTextCon(String id, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/json;charset=utf-8");
			result = carousel.getRichTextById(id);
		} catch (Exception e) {
			result.put("title", "");
			result.put("content", "");
			logger.error("根据Id查询轮播图富文本异常<getImgRichTextCon>:", e);
		}
		return result;
	}

	/**
	 * 获取退货原因
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping("/returnReason/findAll")
	@ResponseBody
	public List<SalesReturnReasonVo> findReturnReasonAll(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try {
			return salesReturnReasonService.findAll();
		} catch (Exception e) {
			logger.error("根据获取退货原因异常<findReturnReasonAll>:", e);
		}
		return null;
	}

	/**
	 * 获取退货地址
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping("/deliveryAddress/loadInfo")
	@ResponseBody
	public DeliveryAddressVo finddDeliveryAddress(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try {
			return deliveryAddressService.getDeliveryAddress();
		} catch (Exception e) {
			logger.error("根据获取退货地址异常<finddDeliveryAddress>:", e);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/getCarriageByRegionId")
	@ResponseBody
	public BigDecimal getCarriageByRegionId(String indentInfo, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		BigDecimal carriage = BigDecimal.ZERO;
		List<Map> list = JsonUtil.toList(indentInfo, Map.class);
		carriage = carriageRuleService.getCarriageByRegionId(list);
		
		if(carriage == null){
			return BigDecimal.ZERO;
		}
		return carriage;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getCarriageByRegionIdAndGoodId")
	@ResponseBody
	public BigDecimal getCarriageByRegionIdAndGoodId(String indentInfo, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		BigDecimal carriage = BigDecimal.ZERO;
		List<Map> list = JsonUtil.toList(indentInfo, Map.class);
		carriage = carriageRuleService.getCarriageByRegionIdAndGoodId(list);
		
		if(carriage == null){
			return BigDecimal.ZERO;
		}
		return carriage;
	}
	
	@RequestMapping("/getCarriageById")
	@ResponseBody
	public Map<String, Object> getCarriageById(String carriageId, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String, Object> carriage = carriageRuleService.queryCarriageRuleDetail(carriageId);
		
		return carriage;
	}
}
