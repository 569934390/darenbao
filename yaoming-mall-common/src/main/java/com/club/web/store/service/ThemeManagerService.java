package com.club.web.store.service;

import java.util.List;
import java.util.Map;

import com.club.core.common.Page;
import com.club.web.store.vo.ShopThemeExtendVo;
import com.club.web.store.vo.ShopThemeManagerVo;

/**
 * 主题区管理-service接口
 * 
 * @author wqh
 * @add by 2016-04-12
 */
public interface ThemeManagerService {

	/**
	 * 根据参数查询主题列表信息
	 * 
	 * @param page
	 * @return Page<Map<String, Object>>
	 * @add by 2016-04-26
	 */
	Page<Map<String, Object>> queryThemeListSer(Page<Map<String, Object>> page);

	/**
	 * 新增主题区信息
	 * 
	 * @param param
	 * @return Map<String, Object>
	 * @add by 2016-04-27
	 */
	Map<String, Object> saveThemeMsgSer(Map<String, Object> param) throws Exception;

	/**
	 * 删除主题区
	 * 
	 * @param paramMap
	 * @return Map<String, Object>
	 * @add by 2016-04-27
	 */
	Map<String, Object> delThemeSer(Map<String, Object> paramMap);

	/**
	 * 修改主题区状态
	 * 
	 * @param paramMap
	 * @return Map<String, Object>
	 * @add by 2016-04-27
	 */
	Map<String, Object> updateThemeStatusSer(Map<String, Object> paramMap) throws Exception;

	/**
	 * 查询启动状态的主题区列表
	 * 
	 * @return List<ShopThemeManagerVo>
	 * @add by 2016-04-27
	 */
	List<ShopThemeManagerVo> queryStartThemeSer();

	/**
	 * 查询启动状态的主题区列表
	 * 
	 * @return List<ShopThemeManagerVo>
	 * @add by 2016-04-27
	 */
	List<ShopThemeExtendVo> queryPrevThemeSer();
}
