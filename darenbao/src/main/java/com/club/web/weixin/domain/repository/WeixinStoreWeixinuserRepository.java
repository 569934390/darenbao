package com.club.web.weixin.domain.repository;

import com.club.web.weixin.domain.WeixinStoreWeixinuserDo;
import com.club.web.weixin.vo.WeixinStoreWeixinuserVo;

public interface WeixinStoreWeixinuserRepository {

	void insert(WeixinStoreWeixinuserDo weixinStoreWeixinuserDo);

	WeixinStoreWeixinuserVo findByStoreAndWeixinUserinfo(Long storeId, Long weixinUserinfoId);

	WeixinStoreWeixinuserDo create(Long storeId, Long weixinUserinfoId);

	void delete(WeixinStoreWeixinuserVo weixinStoreWeixinuserVo);

}
