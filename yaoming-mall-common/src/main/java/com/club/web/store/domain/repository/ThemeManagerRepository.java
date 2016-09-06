package com.club.web.store.domain.repository;

import java.util.List;
import java.util.Map;

import com.club.web.store.domain.ShopThemeManagerDo;
import com.club.web.store.vo.ShopThemeExtendVo;
import com.club.web.store.vo.ShopThemeManagerVo;

/**
 * 主题区仓库
 * 
 * @author wqh
 * 
 * @add by 2016-04-26
 */
public interface ThemeManagerRepository extends CommonRepository {
	/**
	 * 根据参数查询主题列表信息
	 * 
	 * @param status
	 * @param matchParam
	 * @param startIndex
	 * @param pageSize
	 * @return List<Map<String, Object>>
	 * @add by 2016-04-26
	 */
	List<ShopThemeManagerVo> queryThemeList(int status, String matchParam, int startIndex, int pageSize);

	/**
	 * 根据参数查询主题列表总条数
	 * 
	 * @param status
	 * @param matchParam
	 * @return int
	 * @add by 2016-04-26
	 */
	int queryThemeListTotal(int status, String matchParam);

	/**
	 * 根据参数创建对象
	 * 
	 * @param param
	 * @return ShopThemeManagerVo
	 * @add by 2016-04-27
	 */
	ShopThemeManagerDo createThemeObj(Map<String, Object> param);

	/**
	 * 根据参数删除主题区
	 * 
	 * @param ids
	 * @return void
	 * @add by 2016-04-27
	 */
	void delTheme(List<Long> ids);

	/**
	 * 根据参数查询主题区
	 * 
	 * @param id
	 * @return ShopThemeManagerDo
	 * @add by 2016-04-27
	 */
	ShopThemeManagerDo queryThemeById(long id);

	/**
	 * 根据参数查询主题区列表
	 * 
	 * @param status
	 * @return List<ShopThemeManagerVo>
	 * @add by 2016-04-27
	 */
	List<ShopThemeManagerVo> queryStartTheme(int status);

	/**
	 * 查询主题区列表
	 * 
	 * @param status
	 * @return List<ShopThemeManagerVo>
	 * @add by 2016-04-27
	 */
	List<ShopThemeExtendVo> queryPrevTheme();

}
