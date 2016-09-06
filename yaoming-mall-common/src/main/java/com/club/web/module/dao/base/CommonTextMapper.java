package com.club.web.module.dao.base;

import com.club.web.module.dao.base.po.CommonText;
/**
 * 通用文本Dao
 * @author zhuzd
 *
 */
public interface CommonTextMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CommonText record);

    int insertSelective(CommonText record);

    CommonText selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CommonText record);

    int updateByPrimaryKeyWithBLOBs(CommonText record);

    int updateByPrimaryKey(CommonText record);
}