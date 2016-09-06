package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.ShopThemeManager;

public interface ShopThemeManagerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShopThemeManager record);

    int insertSelective(ShopThemeManager record);

    ShopThemeManager selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShopThemeManager record);

    int updateByPrimaryKey(ShopThemeManager record);
}