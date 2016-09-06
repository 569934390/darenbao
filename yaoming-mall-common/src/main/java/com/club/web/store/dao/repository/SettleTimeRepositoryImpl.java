package com.club.web.store.dao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.store.dao.base.po.SettleTime;
import com.club.web.store.dao.extend.SettleTimeExtendMapper;
import com.club.web.store.domain.SettleTimeDo;
import com.club.web.store.domain.repository.SettleTimeRepository;
@Repository
public class SettleTimeRepositoryImpl implements SettleTimeRepository{
@Autowired
SettleTimeExtendMapper settleTimeExtendMapper;
	@Override
	public int insert(SettleTimeDo settleTimeDo) {
		SettleTime settleTime=new SettleTime();
		BeanUtils.copyProperties(settleTimeDo,settleTime);
		int result=settleTimeExtendMapper.insert(settleTime);
		return result;
	}

	@Override
	public int update(SettleTimeDo settleTimeDo) {
		SettleTime settleTime=new SettleTime();
		BeanUtils.copyProperties(settleTimeDo,settleTime);
		int result=settleTimeExtendMapper.updateByPrimaryKey(settleTime);
		return result;
	}

}
