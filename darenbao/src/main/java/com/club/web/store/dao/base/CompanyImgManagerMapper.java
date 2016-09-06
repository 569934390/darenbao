package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.CompanyImgManager;

public interface CompanyImgManagerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CompanyImgManager record);

    int insertSelective(CompanyImgManager record);

    CompanyImgManager selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CompanyImgManager record);

    int updateByPrimaryKey(CompanyImgManager record);
}