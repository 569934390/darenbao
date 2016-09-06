package com.club.web.weixin.dao.base;

import com.club.web.weixin.dao.base.po.WeixinUserInfo;

public interface WeixinUserInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WeixinUserInfo record);

    int insertSelective(WeixinUserInfo record);

    WeixinUserInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WeixinUserInfo record);

    int updateByPrimaryKey(WeixinUserInfo record);
}