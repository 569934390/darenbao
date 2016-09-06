package com.club.web.weixin.dao.base;

import com.club.web.weixin.dao.base.po.WeixinStoreWeixinuser;

public interface WeixinStoreWeixinuserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WeixinStoreWeixinuser record);

    int insertSelective(WeixinStoreWeixinuser record);

    WeixinStoreWeixinuser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WeixinStoreWeixinuser record);

    int updateByPrimaryKey(WeixinStoreWeixinuser record);
}