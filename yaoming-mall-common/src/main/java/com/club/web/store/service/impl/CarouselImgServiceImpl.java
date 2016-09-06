/**
 * 
 */
package com.club.web.store.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.club.core.common.Page;
import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.image.service.ImageService;
import com.club.web.image.service.vo.ImageVo;
import com.club.web.store.domain.CarouselImgDo;
import com.club.web.store.domain.repository.CarouselImgRepository;
import com.club.web.store.service.CarouselImgService;
import com.club.web.store.vo.CarouselImgPrevVo;
import com.club.web.util.CommonUtil;

/**
 * 轮播图-service接口
 * 
 * @author wqh
 * @add by 2016-04-12
 */
@Service
@Transactional
public class CarouselImgServiceImpl implements CarouselImgService {
	/**
	 * 引入日志对象
	 */
	private Logger logger = LoggerFactory.getLogger(CarouselImgServiceImpl.class);
	@Autowired
	CarouselImgRepository repository;
	@Autowired
	ImageService image;
	/**
	 * 操作结果返回信息
	 */
	private Map<String, Object> result;

	/**
	 * 根据参数查询轮播图信息-最特色
	 * 
	 * @param page
	 * @add by 2016-04-12
	 */
	@Override
	public Page<Map<String, Object>> queryCarouselImgSer(Page<Map<String, Object>> page) {
		int startIndex = 0;
		int pageSize = 10;
		List<Map<String, Object>> listMap = null;
		int total = 0;
		int status = -1;
		String matchParam = StringUtils.EMPTY;
		try {
			if (page != null) {
				startIndex = page.getStart();
				pageSize = page.getLimit();
				Map<String, Object> con = page.getConditons();
				if (con != null) {
					if (con.containsKey("status")) {
						status = con.get("status") != null ? Integer.valueOf(con.get("status").toString()) : 0;
					}
					if (con.containsKey("matchParam")) {
						matchParam = con.get("matchParam") != null ? con.get("matchParam").toString()
								: StringUtils.EMPTY;
					}
				}
				total = repository.queryCarouselImgListTotal(status, matchParam);
				page.setTotalRecords(total);
				if (total > 0) {
					listMap = repository.queryCarouselImgList(status, matchParam, startIndex, pageSize);
					if (listMap != null && listMap.stream().count() > 0) {
						page.setResultList(listMap);
					}
				}
			}
		} catch (Exception e) {
			logger.error("根据参数查询轮播图信息异常<queryCarouselImgSer>:", e);
		}
		return page;
	}

	/**
	 * 根据参数查询轮播图信息-凯渥
	 * 
	 * @param page
	 * @add by 2016-04-12
	 */
	@Override
	public Page<Map<String, Object>> queryBannerImgSer(Page<Map<String, Object>> page) {
		int startIndex = 0;
		int pageSize = 10;
		List<Map<String, Object>> listMap = null;
		int total = 0;
		int status = -1;
		String matchParam = StringUtils.EMPTY;
		try {
			if (page != null) {
				startIndex = page.getStart();
				pageSize = page.getLimit();
				Map<String, Object> con = page.getConditons();
				if (con != null) {
					if (con.containsKey("status")) {
						status = con.get("status") != null ? Integer.valueOf(con.get("status").toString()) : 0;
					}
					if (con.containsKey("matchParam")) {
						matchParam = con.get("matchParam") != null ? con.get("matchParam").toString()
								: StringUtils.EMPTY;
					}
				}
				total = repository.queryBannerImgListTotal(status, matchParam);
				page.setTotalRecords(total);
				if (total > 0) {
					listMap = repository.queryBannerImgList(status, matchParam, startIndex, pageSize);
					if (listMap != null && listMap.stream().count() > 0) {
						page.setResultList(listMap);
					}
				}
			}
		} catch (Exception e) {
			logger.error("根据参数查询轮播图信息异常<queryCarouselImgSer>:", e);
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
	@Override
	public Map<String, Object> saveCarouselImgSer(Map<String, Object> paramMap) throws Exception {
		result = new HashMap<>();
		CarouselImgDo carousel = null;
		ImageVo imgObj = null;
		if (paramMap != null) {
			carousel = repository.createCarouseObj(paramMap);
			if (carousel != null) {
				if (carousel.getFlag() == 0) {
					imgObj = new ImageVo();
					imgObj.setId(StringUtils.isNotEmpty(carousel.getExtendId()) ? Long.valueOf(carousel.getExtendId())
							: 0);
					imgObj.setPicUrl(carousel.getPicUrl());
					image.updateImage(imgObj);
					carousel.setPicUrl(String.valueOf(imgObj.getId()));
					carousel.update();
				} else {
					imgObj = image.saveImage(carousel.getPicUrl());
					carousel.setPicUrl(String.valueOf(imgObj.getId()));
					carousel.save();
				}
				result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
				result.put(Constants.RESULT_MSG, "保存成功");
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} else {
			result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
			result.put(Constants.RESULT_MSG, "参数为空");
		}
		return result;
	}

	/**
	 * 删除轮播图信息
	 * 
	 * @param paramMap
	 * @return Map<String, Object>
	 * @add by 2016-04-13
	 */
	@Override
	public Map<String, Object> delCarouselImgSer(Map<String, Object> paramMap) {
		result = new HashMap<String, Object>();
		List<Long> ids = null;
		String id = StringUtils.EMPTY;
		if (paramMap != null) {
			if (paramMap.containsKey("ids")) {
				id = paramMap.get("ids") != null ? paramMap.get("ids").toString() : StringUtils.EMPTY;
			}
			if (StringUtils.isNotEmpty(id)) {
				String[] arr = id.split(",");
				if (arr != null && arr.length > 0) {
					ids = new ArrayList<>();
					for (String str : arr) {
						ids.add(Long.valueOf(str));
					}
				}
			}
			if (ids != null && ids.stream().count() > 0) {
				repository.delCarouselImg(ids);
				result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
				result.put(Constants.RESULT_MSG, "操作成功");
			} else {
				result.put(Constants.RESULT_CODE, Constants.HANDLER_FAIL_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}

		} else {
			result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
			result.put(Constants.RESULT_MSG, "参数为空");
		}
		return result;
	}

	/**
	 * 修改轮播图信息排序
	 * 
	 * @param paramMap
	 * @return Map<String, Object>
	 * @add by 2016-04-13
	 */
	@Override
	public Map<String, Object> updateCarouselImgSer(Map<String, Object> paramMap) throws Exception {
		result = new HashMap<String, Object>();
		long id = 0;
		int count = 0;
		CarouselImgDo carousel = null;
		String updateCount = StringUtils.EMPTY;
		if (paramMap != null) {
			if (paramMap.containsKey("id")) {
				id = paramMap.get("id") != null ? Long.valueOf(paramMap.get("id").toString()) : 0;
			}
			if (paramMap.containsKey("updateCount")) {
				updateCount = paramMap.get("updateCount") != null ? paramMap.get("updateCount").toString()
						: StringUtils.EMPTY;
			}
			if (CommonUtil.isDigital(updateCount)) {
				count = Integer.valueOf(updateCount);
				carousel = repository.queryCarouselById(id);
				if (carousel != null) {
					carousel.setUpdateTime(new Date());
					carousel.setSort(count);
					carousel.update();
					result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
					result.put(Constants.RESULT_MSG, "修改成功！");
				} else {
					result.put(Constants.RESULT_CODE, Constants.HANDLER_FAIL_CODE);
					result.put(Constants.RESULT_MSG, "修改对象信息不存在！");
				}
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "排序数字包含非法字符，请重新输入!");
			}
		} else {
			result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
			result.put(Constants.RESULT_MSG, "参数为空");
		}
		return result;
	}

	/**
	 * 修改轮播图信息状态
	 * 
	 * @param paramMap
	 * @return Map<String, Object>
	 * @add by 2016-04-13
	 */
	@Override
	public Map<String, Object> updateCarouselImgStatusSer(Map<String, Object> paramMap) throws Exception {
		result = new HashMap<String, Object>();
		int status = -1;
		CarouselImgDo carousel = null;
		String ids = StringUtils.EMPTY;
		List<Long> listId = null;
		if (paramMap != null) {
			if (paramMap.containsKey("ids")) {
				ids = paramMap.get("ids") != null ? paramMap.get("ids").toString() : StringUtils.EMPTY;
			}
			if (paramMap.containsKey("status")) {
				status = paramMap.get("status") != null ? Integer.valueOf(paramMap.get("status").toString()) : -1;
			}
			if (StringUtils.isNotEmpty(ids)) {
				String[] arr = ids.split(",");
				if (arr != null && arr.length > 0) {
					listId = new ArrayList<>();
					for (String str : arr) {
						listId.add(Long.valueOf(str));
					}
				}
			}
			if (listId != null && listId.stream().count() > 0) {
				for (long id : listId) {
					carousel = repository.queryCarouselById(id);
					carousel.setStatus(status);
					carousel.setUpdateTime(new Date());
					carousel.update();
				}
				result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
				result.put(Constants.RESULT_MSG, "修改成功！");
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} else {
			result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
			result.put(Constants.RESULT_MSG, "参数为空");
		}
		return result;
	}

	/**
	 * 根据条件参数轮播图片
	 * 
	 * @param category
	 * @return List<CarouselImgPrevVo>
	 * @add by 2016-04-15
	 */
	@Override
	public List<CarouselImgPrevVo> getCarouselByCatory(String category) {
		long categoryId = 0;
		if (StringUtils.isNotEmpty(category)) {
			if (CommonUtil.isDigital(category)) {
				categoryId = Long.valueOf(category);
			}
		}
		List<CarouselImgPrevVo> list = repository.getCarouselByCatory(categoryId);
		if (list != null && list.stream().count() > 0) {
			for (CarouselImgPrevVo ig : list) {
				if (ig.getLineStatus() == 0) {
					ig.setLineUrl("");
				}
			}
		}
		return list;
	}

	/**
	 * 根据id查询富文本
	 * 
	 * @param id
	 * @return String
	 * @add by 2016-04-15
	 */
	@Override
	public Map<String, Object>  getRichTextById(String id) {
		long caourseId = 0;
		if (StringUtils.isNotEmpty(id)) {
			if (CommonUtil.isDigital(id)) {
				caourseId = Long.valueOf(id);
			}
		}
		Map<String, Object> result = repository.getRichTextById(caourseId);
		return result;
	}
}
