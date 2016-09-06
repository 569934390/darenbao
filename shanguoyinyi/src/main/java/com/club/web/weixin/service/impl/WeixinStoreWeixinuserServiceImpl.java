package com.club.web.weixin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.web.weixin.domain.WeixinStoreWeixinuserDo;
import com.club.web.weixin.domain.repository.WeixinStoreWeixinuserRepository;
import com.club.web.weixin.service.WeixinStoreWeixinuserService;
import com.club.web.weixin.vo.WeixinStoreWeixinuserVo;

@Service("weixinStoreWeixinuserService")
public class WeixinStoreWeixinuserServiceImpl implements WeixinStoreWeixinuserService{

	@Autowired
	WeixinStoreWeixinuserRepository weixinStoreWeixinuserRepository;

	public void saveorUpdate(Long storeId, Long weixinUserinfoId) {
		
		//查看是否有，没有保存
		WeixinStoreWeixinuserVo weixinStoreWeixinuserVo=weixinStoreWeixinuserRepository.findByStoreAndWeixinUserinfo(storeId,weixinUserinfoId);
		if(weixinStoreWeixinuserVo==null){
			WeixinStoreWeixinuserDo weixinStoreWeixinuserDo=weixinStoreWeixinuserRepository.create(storeId,weixinUserinfoId);
			weixinStoreWeixinuserDo.insert();
		}
	}

	@Override
	public void delete(WeixinStoreWeixinuserVo weixinStoreWeixinuserVo) {
		weixinStoreWeixinuserRepository.delete(weixinStoreWeixinuserVo);
	}
	

}
