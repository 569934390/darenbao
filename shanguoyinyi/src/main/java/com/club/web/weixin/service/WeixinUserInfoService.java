package com.club.web.weixin.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestParam;

import com.club.core.common.Page;
import com.club.web.weixin.vo.FansMsgVo;
import com.club.web.weixin.vo.WeixinUserInfoVo;

public interface WeixinUserInfoService {

	Page<Map<String, Object>> queryWeixinFansPage(Page<Map<String, Object>> page);

	String weixinIndex(String code, String storeId, HttpServletRequest request);

	String weixinEvent(String code, String storeId, HttpServletRequest request);

	Map<String, Object> updateMyInfo(WeixinUserInfoVo weixinUserInfoVo, HttpServletRequest request);

	Map<String, Object> loadClientInfo(Long id);

	/**
	 * 查看店铺的粉丝列表
	 * 
	 * @param shopId
	 * @param startIndex
	 * @param pageSize
	 * @return List<FansMsgVo>
	 * @add by 2016-05-13
	 */
	List<FansMsgVo> getFansListSer(String shopId, int startIndex, int pageSize);

}
