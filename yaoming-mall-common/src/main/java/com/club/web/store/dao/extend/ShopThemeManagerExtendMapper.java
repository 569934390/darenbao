package com.club.web.store.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.club.web.store.dao.base.ShopThemeManagerMapper;
import com.club.web.store.domain.ShopThemeManagerDo;
import com.club.web.store.vo.ShopThemeExtendVo;
import com.club.web.store.vo.ShopThemeManagerVo;

public interface ShopThemeManagerExtendMapper extends ShopThemeManagerMapper {
	List<ShopThemeManagerVo> queryThemeList(@Param("status") int status, @Param("matchParam") String matchParam,
			@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

	int queryThemeListTotal(@Param("status") int status, @Param("matchParam") String matchParam);

	ShopThemeManagerDo queryThemeObjById(@Param("id") long id);

	void delTheme(@Param("ids") List<Long> ids);

	List<ShopThemeManagerVo> queryStartTheme(@Param("status") int status);

	List<ShopThemeExtendVo> queryPrevTheme();
}