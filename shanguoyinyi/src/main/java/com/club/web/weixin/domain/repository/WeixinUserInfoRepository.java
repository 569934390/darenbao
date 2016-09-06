package com.club.web.weixin.domain.repository;

import java.util.List;
import java.util.Map;

import com.club.core.common.Page;
import com.club.web.weixin.domain.WeixinUserInfoDo;
import com.club.web.weixin.vo.FansMsgVo;
import com.club.web.weixin.vo.WeixinUserInfoVo;

public interface WeixinUserInfoRepository {

	WeixinUserInfoVo findByOpenId(String openid);

	WeixinUserInfoDo create(WeixinUserInfoVo oldWeixinUserInfoVo);

	void insert(WeixinUserInfoDo weixinUserInfoDo);

	void update(WeixinUserInfoDo weixinUserInfoDo);

	List<Map<String, Object>> queryWeixinFansPage(Page<Map<String, Object>> page);

	Long queryWeixinFansCountPage(Page<Map<String, Object>> page);

	WeixinUserInfoDo findWeixinUserInfoDoById(String id);

	/**
	 * 查看店铺的粉丝列表
	 * 
	 * @param shopId
	 * @param startIndex
	 * @param pageSize
	 * @return List<FansMsgVo>
	 * @add by 2016-05-13
	 */
	List<FansMsgVo> getFansList(long shopId, int startIndex, int pageSize);

}
