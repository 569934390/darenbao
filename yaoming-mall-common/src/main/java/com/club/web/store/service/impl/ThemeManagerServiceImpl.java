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

import com.club.core.common.Page;
import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.image.service.ImageService;
import com.club.web.image.service.vo.ImageVo;
import com.club.web.stock.domain.repository.CargoClassifyRepository;
import com.club.web.store.domain.ShopThemeManagerDo;
import com.club.web.store.domain.repository.ThemeManagerRepository;
import com.club.web.store.service.ThemeManagerService;
import com.club.web.store.vo.ShopThemeExtendVo;
import com.club.web.store.vo.ShopThemeManagerVo;
import com.club.web.util.CommonUtil;

/**
 * 主题区管理-service接口
 * 
 * @author wqh
 * @add by 2016-04-12
 */
@Service
public class ThemeManagerServiceImpl implements ThemeManagerService {
	private Logger logger = LoggerFactory.getLogger(ThemeManagerServiceImpl.class);
	@Autowired
	ThemeManagerRepository repository;
	@Autowired
	private CargoClassifyRepository cargoClassifyRepository;
	@Autowired
	ImageService image;
	private Map<String, Object> result;

	/**
	 * 根据参数查询主题列表信息
	 * 
	 * @param page
	 * @return Page<Map<String, Object>>
	 * @add by 2016-04-26
	 */
	@Override
	public Page<Map<String, Object>> queryThemeListSer(Page<Map<String, Object>> page) {
		int startIndex = 0;
		int pageSize = 10;
		List<ShopThemeManagerVo> list = null;
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
				total = repository.queryThemeListTotal(status, matchParam);
				page.setTotalRecords(total);
				if (total > 0) {
					list = repository.queryThemeList(status, matchParam, startIndex, pageSize);
					if (list != null && list.stream().count() > 0) {
						packThemeData(list);
						listMap = CommonUtil.listObjTransToListMap(list);
						page.setResultList(listMap);
					}
				}
			}
		} catch (Exception e) {
			logger.error("根据参数查询主题列表信息异常<queryThemeListSer>:", e);
		}
		return page;
	}

	/**
	 * 组装列表信息
	 * 
	 * @param page
	 * @return Page<Map<String, Object>>
	 * @add by 2016-04-26
	 */
	private void packThemeData(List<ShopThemeManagerVo> listMap) {
		if (listMap != null && listMap.stream().count() > 0) {
			for (ShopThemeManagerVo obj : listMap) {
				StringBuilder classifyName = new StringBuilder("");
				String ids = obj.getClassifyId();
				if (StringUtils.isNotEmpty(ids)) {
					String[] arr = ids.split(",");
					if (arr != null && arr.length > 0) {
						for (int i = 0; i < arr.length; i++) {
							String name = cargoClassifyRepository.queryNameById(Long.valueOf(arr[i].trim()));
							if (StringUtils.isNotEmpty(name)) {
								classifyName.append(name + "");
								if (i != arr.length - 1) {
									classifyName.append(",");
								}
							}
						}
					}
				}
				obj.setClassify(classifyName.toString());
			}
		}
	}

	/**
	 * 新增主题区信息
	 * 
	 * @param param
	 * @return Map<String, Object>
	 * @add by 2016-04-27
	 */
	@Override
	public Map<String, Object> saveThemeMsgSer(Map<String, Object> param) throws Exception {
		result = new HashMap<>();
		ShopThemeManagerDo theme = null;
		ImageVo imgObj = null;
		if (param != null) {
			theme = repository.createThemeObj(param);
			if (theme != null) {
				if (theme.getFlag() == 0) {
					imgObj = image.saveImage(theme.getTitlePicUrl());
					theme.setTitlePicUrl(String.valueOf(imgObj.getId()));
					theme.save();
				} else {
					if (StringUtils.isNotEmpty(theme.getExtendId())) {
						imgObj = new ImageVo();
						imgObj.setId(Long.valueOf(theme.getExtendId()));
						imgObj.setPicUrl(theme.getTitlePicUrl());
						image.updateImage(imgObj);
						theme.setTitlePicUrl(String.valueOf(imgObj.getId()));
					}
					theme.update();
				}
				result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
				result.put(Constants.RESULT_MSG, "操作成功");
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
	 * 删除主题区
	 * 
	 * @param paramMap
	 * @return Map<String, Object>
	 * @add by 2016-04-27
	 */
	@Override
	public Map<String, Object> delThemeSer(Map<String, Object> paramMap) {
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
				repository.delTheme(ids);
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
	 * 修改主题区状态
	 * 
	 * @param paramMap
	 * @return Map<String, Object>
	 * @add by 2016-04-27
	 */
	@Override
	public Map<String, Object> updateThemeStatusSer(Map<String, Object> paramMap) throws Exception {
		result = new HashMap<String, Object>();
		int status = -1;
		ShopThemeManagerDo theme = null;
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
					theme = repository.queryThemeById(id);
					theme.setStatus(status);
					theme.setCreateTime(new Date());
					theme.update();
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
	 * 查询启动状态的主题区列表
	 * 
	 * @return List<ShopThemeManagerVo>
	 * @add by 2016-04-27
	 */
	@Override
	public List<ShopThemeManagerVo> queryStartThemeSer() {
		List<ShopThemeManagerVo> list = repository.queryStartTheme(1);
		return list;
	}

	/**
	 * 查询启动状态的主题区列表
	 * 
	 * @return List<ShopThemeManagerVo>
	 * @add by 2016-04-27
	 */
	@Override
	public List<ShopThemeExtendVo> queryPrevThemeSer() {
		List<ShopThemeExtendVo> list = repository.queryPrevTheme();
		return list;
	}
}
