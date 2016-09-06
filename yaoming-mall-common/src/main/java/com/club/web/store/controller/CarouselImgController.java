package com.club.web.store.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.store.service.CarouselImgService;

/**
 * 首页轮播图管理-Controller
 * 
 * @author wqh
 * @add by 2016-04-12
 */
@RequestMapping("/carousel")
@Controller
public class CarouselImgController {
	private Logger logger = LoggerFactory.getLogger(CarouselImgController.class);
	@Autowired
	CarouselImgService carousel;
	/**
	 * 操作结果返回信息
	 */
	private Map<String, Object> result;

	/**
	 * 查询轮播图信息-最特色
	 * 
	 * @param page
	 * @param conditionStr
	 * @return Page<Map<String, Object>>
	 * @add by 2016-04-12
	 */
	@RequestMapping("/queryCarouselImg")
	@ResponseBody
	public Page<Map<String, Object>> queryCarouselImgCon(Page<Map<String, Object>> page, String conditionStr) {
		try {
			if (StringUtils.isNotEmpty(conditionStr) && page != null) {
				page.setConditons(JsonUtil.toMap(conditionStr));
			}
			page = carousel.queryCarouselImgSer(page);
		} catch (Exception e) {
			logger.error("查询轮播图信息异常<queryCarouselImgCon>:", e);
		}
		return page;
	}

	/**
	 * 查询轮播图信息-凯渥
	 * 
	 * @param page
	 * @param conditionStr
	 * @return Page<Map<String, Object>>
	 * @add by 2016-04-12
	 */
	@RequestMapping("/queryBannerImg")
	@ResponseBody
	public Page<Map<String, Object>> queryBannerImgCon(Page<Map<String, Object>> page, String conditionStr) {
		try {
			if (StringUtils.isNotEmpty(conditionStr) && page != null) {
				page.setConditons(JsonUtil.toMap(conditionStr));
			}
			page = carousel.queryBannerImgSer(page);
		} catch (Exception e) {
			logger.error("查询轮播图信息异常<queryCarouselImgCon>:", e);
		}
		return page;
	}

	/**
	 * 新增保存轮播图信息
	 * 
	 * @param conditionStr
	 * @return Map<String, Object>
	 * @add by 2016-04-13
	 */
	@RequestMapping("/saveCarouselImg")
	@ResponseBody
	public Map<String, Object> saveCarouselImgCon(String conditionStr) {
		result = new HashMap<String, Object>();
		Map<String, Object> paramMap = null;
		try {
			if (StringUtils.isNotEmpty(conditionStr)) {
				paramMap = JsonUtil.toMap(conditionStr);
				result = carousel.saveCarouselImgSer(paramMap);
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} catch (Exception e) {
			logger.error("新增保存轮播图信息异常<saveCarouselImgCon>:", e);
			result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作失败");
		}
		return result;
	}

	/**
	 * 删除轮播图片对象信息
	 * 
	 * @param conditionStr
	 * @return Map<String, Object>
	 * @add by 2016-04-13
	 */
	@RequestMapping("/delCarouselImg")
	@ResponseBody
	public Map<String, Object> delCarouselImgCon(String conditionStr) {
		result = new HashMap<String, Object>();
		Map<String, Object> paramMap = null;
		try {
			if (StringUtils.isNotEmpty(conditionStr)) {
				paramMap = JsonUtil.toMap(conditionStr);
				result = carousel.delCarouselImgSer(paramMap);
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} catch (Exception e) {
			logger.error("删除轮播图片对象信息异常<delCarouselImgCon>:", e);
			result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作失败");
		}
		return result;
	}

	/**
	 * 修改轮播图片排序
	 * 
	 * @param conditionStr
	 * @return Map<String, Object>
	 * @add by 2016-04-13
	 */
	@RequestMapping("/updateCarouselImg")
	@ResponseBody
	public Map<String, Object> updateCarouselImgCon(String conditionStr) {
		result = new HashMap<String, Object>();
		Map<String, Object> paramMap = null;
		try {
			if (StringUtils.isNotEmpty(conditionStr)) {
				paramMap = JsonUtil.toMap(conditionStr);
				result = carousel.updateCarouselImgSer(paramMap);
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} catch (Exception e) {
			logger.error("修改轮播图片排序异常<updateCarouselImgCon>:", e);
			result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作失败");
		}
		return result;
	}

	/**
	 * 修改轮播图片状态
	 * 
	 * @param conditionStr
	 * @return Map<String, Object>
	 * @add by 2016-04-13
	 */
	@RequestMapping("/updateCarouselImgStatus")
	@ResponseBody
	public Map<String, Object> updateCarouselImgStatusCon(String conditionStr) {
		result = new HashMap<String, Object>();
		Map<String, Object> paramMap = null;
		try {
			if (StringUtils.isNotEmpty(conditionStr)) {
				paramMap = JsonUtil.toMap(conditionStr);
				result = carousel.updateCarouselImgStatusSer(paramMap);
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} catch (Exception e) {
			logger.error("修改轮播图片状态异常<updateCarouselImgStatusCon>:", e);
			result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作失败");
		}
		return result;
	}
}
