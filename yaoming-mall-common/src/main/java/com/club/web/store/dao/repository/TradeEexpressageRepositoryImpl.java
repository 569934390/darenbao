package com.club.web.store.dao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.store.dao.base.po.TradeEexpressage;
import com.club.web.store.dao.extend.TradeEexpressageExtendMapper;
import com.club.web.store.domain.TradeEexpressageDo;
import com.club.web.store.domain.repository.TradeEexpressageRepository;
import com.fasterxml.jackson.databind.util.BeanUtil;
@Repository
public class TradeEexpressageRepositoryImpl implements TradeEexpressageRepository{

	@Autowired
	TradeEexpressageExtendMapper tradeEexpressageExtendMapper;
	
	@Override
	public int insert(TradeEexpressageDo tradeEexpressageDo) {
		TradeEexpressage tradeEexpressage=new TradeEexpressage();
		BeanUtils.copyProperties(tradeEexpressageDo, tradeEexpressage);
		tradeEexpressageExtendMapper.insert(tradeEexpressage);
		return 0;
	}

	@Override
	public int update(TradeEexpressageDo tradeEexpressageDo) {
		TradeEexpressage tradeEexpressage=new TradeEexpressage();
		BeanUtils.copyProperties(tradeEexpressageDo, tradeEexpressage);
		tradeEexpressageExtendMapper.updateByPrimaryKeySelective(tradeEexpressage);
		return 0;
	}

	@Override
	public int delet(TradeEexpressageDo TradeEexpressageDo) {
		
		int reult=tradeEexpressageExtendMapper.deleteByPrimaryKey(TradeEexpressageDo.getId());
		return reult;
	}

	@Override
	public int updateState(TradeEexpressageDo tradeEexpressageDo) {
		TradeEexpressage tradeEexpressage=new TradeEexpressage();
		BeanUtils.copyProperties(tradeEexpressageDo, tradeEexpressage);
		int reult=tradeEexpressageExtendMapper.updateByPrimaryKeySelective(tradeEexpressage);
		return reult;
	}

}
