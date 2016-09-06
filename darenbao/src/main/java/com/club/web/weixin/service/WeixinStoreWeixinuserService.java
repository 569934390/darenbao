package com.club.web.weixin.service;

import com.club.web.weixin.vo.WeixinStoreWeixinuserVo;

public interface WeixinStoreWeixinuserService {

	void saveorUpdate(Long storeId, Long weixinUserinfoId);

	void delete(WeixinStoreWeixinuserVo weixinStoreWeixinuserVo);

}
