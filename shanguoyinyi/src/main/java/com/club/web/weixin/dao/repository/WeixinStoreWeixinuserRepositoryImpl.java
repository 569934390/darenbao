package com.club.web.weixin.dao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.util.IdGenerator;
import com.club.web.weixin.dao.base.po.WeixinStoreWeixinuser;
import com.club.web.weixin.dao.extend.WeixinStoreWeixinuserExtendMapper;
import com.club.web.weixin.domain.WeixinStoreWeixinuserDo;
import com.club.web.weixin.domain.repository.WeixinStoreWeixinuserRepository;
import com.club.web.weixin.vo.WeixinStoreWeixinuserVo;

@Repository
public class WeixinStoreWeixinuserRepositoryImpl implements WeixinStoreWeixinuserRepository{
	
	@Autowired 
	WeixinStoreWeixinuserExtendMapper weixinStoreWeixinuserExtendMapper;
	
	@Override
	public WeixinStoreWeixinuserDo create(Long storeId, Long weixinUserinfoId) {
		WeixinStoreWeixinuserDo weixinStoreWeixinuserDo=new WeixinStoreWeixinuserDo();
		weixinStoreWeixinuserDo.setId(IdGenerator.getDefault().nextId());
		weixinStoreWeixinuserDo.setStoreId(storeId);
		weixinStoreWeixinuserDo.setWeixinuserId(weixinUserinfoId);
		return weixinStoreWeixinuserDo;
	}

	private WeixinStoreWeixinuser getPoByDo(WeixinStoreWeixinuserDo weixinStoreWeixinuserDo) {
		if(weixinStoreWeixinuserDo==null){
			return null;
		}
		WeixinStoreWeixinuser weixinStoreWeixinuser=new WeixinStoreWeixinuser();
		BeanUtils.copyProperties(weixinStoreWeixinuserDo, weixinStoreWeixinuser);
		return weixinStoreWeixinuser;
	}

	public void insert(WeixinStoreWeixinuserDo weixinStoreWeixinuserDo) {
		weixinStoreWeixinuserExtendMapper.insert(getPoByDo(weixinStoreWeixinuserDo));
	}

	@Override
	public WeixinStoreWeixinuserVo findByStoreAndWeixinUserinfo(Long storeId, Long weixinUserinfoId) {
		WeixinStoreWeixinuser storeWeixinuser=new WeixinStoreWeixinuser();
		storeWeixinuser.setStoreId(storeId);
		storeWeixinuser.setWeixinuserId(weixinUserinfoId);
		return weixinStoreWeixinuserExtendMapper.findByStoreAndWeixinUserinfo(storeWeixinuser);
	}

	@Override
	public void delete(WeixinStoreWeixinuserVo weixinStoreWeixinuserVo) {
		weixinStoreWeixinuserExtendMapper.delete(weixinStoreWeixinuserVo);		
	}

}
