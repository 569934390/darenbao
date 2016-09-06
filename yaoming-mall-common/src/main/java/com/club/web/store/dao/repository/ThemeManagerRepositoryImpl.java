package com.club.web.store.dao.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.StringUtils;
import com.club.web.store.dao.base.po.ShopThemeManager;
import com.club.web.store.dao.extend.ShopThemeManagerExtendMapper;
import com.club.web.store.domain.ShopThemeManagerDo;
import com.club.web.store.domain.repository.ThemeManagerRepository;
import com.club.web.store.vo.ShopThemeExtendVo;
import com.club.web.store.vo.ShopThemeManagerVo;
import com.club.web.util.IdGenerator;

/**
 * 主题区仓库
 * 
 * @author wqh
 * 
 * @add by 2016-04-26
 */

@Repository
public class ThemeManagerRepositoryImpl implements ThemeManagerRepository {
	@Autowired
	ShopThemeManagerExtendMapper themeDao;

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
	@Override
	public List<ShopThemeManagerVo> queryThemeList(int status, String matchParam, int startIndex, int pageSize) {
		List<ShopThemeManagerVo> list = themeDao.queryThemeList(status, matchParam, startIndex, pageSize);
		return list;
	}

	/**
	 * 根据参数查询主题列表总条数
	 * 
	 * @param status
	 * @param matchParam
	 * @return int
	 * @add by 2016-04-26
	 */
	@Override
	public int queryThemeListTotal(int status, String matchParam) {
		int total = themeDao.queryThemeListTotal(status, matchParam);
		return total;
	}

	/**
	 * 根据参数创建对象
	 * 
	 * @param param
	 * @return ShopThemeManagerVo
	 * @add by 2016-04-27
	 */
	@Override
	public ShopThemeManagerDo createThemeObj(Map<String, Object> param) {
		ShopThemeManagerDo theme = null;
		long id = -1;
		if (param != null) {
			if (param.containsKey("theme_id")) {
				id = param.get("theme_id") != null && StringUtils.isNotEmpty(param.get("theme_id").toString()) ? Long
						.valueOf(param.get("theme_id").toString()) : -1;
			}
			theme = themeDao.queryThemeObjById(id);
			String picUrl = param.get("picUrl") != null ? param.get("picUrl").toString() : "";
			int lastIndex = picUrl.indexOf("-img");
			if (lastIndex > 0) {
				picUrl = picUrl.substring(0, lastIndex);
			}
			String classify = param.get("theme_multiselect") != null ? param.get("theme_multiselect").toString() : "";
			if (StringUtils.isNotEmpty(classify)) {
				if (classify.contains("[")) {
					classify = classify.substring(1, classify.length() - 1);
					classify = classify.replaceAll(" ", "");
				}
			}
			if (theme != null) {
				theme.setFlag(1);
				if (StringUtils.isNotEmpty(picUrl)) {
					theme.setExtendId(theme.getTitlePicUrl());
					theme.setTitlePicUrl(picUrl);
				}
				theme.setTitle(param.get("title") != null ? param.get("title").toString() : theme.getTitle());
				theme.setClassifyId(StringUtils.isNotEmpty(classify) ? classify : theme.getClassifyId());
				theme.setCreateTime(new Date());
				theme.setSort(param.get("sort") != null ? Integer.valueOf(param.get("sort").toString()) : theme
						.getSort());
			} else {
				theme = new ShopThemeManagerDo();
				theme.setId(IdGenerator.getDefault().nextId());
				theme.setFlag(0);
				theme.setSort(param.get("sort") != null ? Integer.valueOf(param.get("sort").toString()) : 0);
				theme.setTitle(param.get("title") != null ? param.get("title").toString() : "");
				theme.setStatus(param.get("status") != null ? Integer.valueOf(param.get("status").toString()) : 1);
				theme.setTitlePicUrl(picUrl);
				theme.setCreateTime(new Date());
				theme.setClassifyId(StringUtils.isNotEmpty(classify) ? classify : "");
			}
		}
		return theme;
	}

	/**
	 * 保存对象
	 * 
	 * @param t
	 * @return void
	 * @add by 2016-04-27
	 */
	@Override
	public <T> void save(T t) throws Exception {
		if (t != null) {
			if (t instanceof ShopThemeManagerDo) {
				ShopThemeManagerDo theme = (ShopThemeManagerDo) t;
				ShopThemeManager themePo = new ShopThemeManager();
				BeanUtils.copyProperties(themePo, theme);
				themeDao.insert(themePo);
			}
		} else {
			throw new NullPointerException();
		}
	}

	/**
	 * 更新对象
	 * 
	 * @param t
	 * @return void
	 * @add by 2016-04-27
	 */
	@Override
	public <T> void update(T t) throws Exception {
		if (t != null) {
			if (t instanceof ShopThemeManagerDo) {
				ShopThemeManagerDo theme = (ShopThemeManagerDo) t;
				ShopThemeManager themePo = new ShopThemeManager();
				BeanUtils.copyProperties(themePo, theme);
				themeDao.updateByPrimaryKey(themePo);
			}
		} else {
			throw new NullPointerException();
		}
	}

	/**
	 * 根据参数删除主题区
	 * 
	 * @param ids
	 * @return void
	 * @add by 2016-04-27
	 */
	@Override
	public void delTheme(List<Long> ids) {
		themeDao.delTheme(ids);
	}

	/**
	 * 根据参数查询主题区
	 * 
	 * @param id
	 * @return ShopThemeManagerDo
	 * @add by 2016-04-27
	 */
	@Override
	public ShopThemeManagerDo queryThemeById(long id) {
		ShopThemeManagerDo theme = themeDao.queryThemeObjById(id);
		return theme;
	}

	/**
	 * 根据参数查询主题区列表
	 * 
	 * @param status
	 * @return List<ShopThemeManagerVo>
	 * @add by 2016-04-27
	 */
	@Override
	public List<ShopThemeManagerVo> queryStartTheme(int status) {
		List<ShopThemeManagerVo> list = themeDao.queryStartTheme(status);
		return list;
	}

	/**
	 * 查询主题区列表
	 * 
	 * @param status
	 * @return List<ShopThemeManagerVo>
	 * @add by 2016-04-27
	 */
	@Override
	public List<ShopThemeExtendVo> queryPrevTheme() {
		List<ShopThemeExtendVo> list = themeDao.queryPrevTheme();
		return list;
	}

}
