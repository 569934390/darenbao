package com.club.web.weixin.dao.extend;

import com.club.web.weixin.dao.base.WeixinStoreWeixinuserMapper;
import com.club.web.weixin.dao.base.po.WeixinStoreWeixinuser;
import com.club.web.weixin.vo.WeixinStoreWeixinuserVo;

public interface WeixinStoreWeixinuserExtendMapper extends WeixinStoreWeixinuserMapper {

	WeixinStoreWeixinuserVo findByStoreAndWeixinUserinfo(WeixinStoreWeixinuser storeWeixinuser);

	void delete(WeixinStoreWeixinuserVo weixinStoreWeixinuserVo);
   
}