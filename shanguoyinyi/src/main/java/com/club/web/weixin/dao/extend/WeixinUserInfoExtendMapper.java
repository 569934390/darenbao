package com.club.web.weixin.dao.extend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.club.web.weixin.dao.base.WeixinUserInfoMapper;
import com.club.web.weixin.vo.FansMsgVo;
import com.club.web.weixin.vo.WeixinUserInfoVo;

public interface WeixinUserInfoExtendMapper extends WeixinUserInfoMapper {

	WeixinUserInfoVo findByOpenId(@Param("openId") String openId);

	List<Map<String, Object>> queryWeixinFansPage(Map<String, Object> map);

	Long queryWeixinFansCountPage(Map<String, Object> map);

	List<FansMsgVo> getFansList(@Param("shopId") long shopId, @Param("startIndex") int startIndex,
			@Param("pageSize") int pageSize);

}