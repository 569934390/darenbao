/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.club.web.store.dao.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.stock.vo.SkuGoodsParam;
import com.club.web.store.dao.base.TradeGoodSkuMapper;
import com.club.web.store.dao.base.po.TradeGood;
import com.club.web.store.dao.base.po.TradeGoodSku;
import com.club.web.store.dao.extend.TradeGoodSkuExtendMapper;
import com.club.web.store.domain.GoodUpDo;
import com.club.web.store.domain.TradeGoodDo;
import com.club.web.store.domain.TradeGoodSkuDo;
import com.club.web.store.domain.repository.GoodSKURepository;
import com.club.web.store.vo.TradeGoodSkuVo;

/**
 *@Title:
 *@Description:domain层的仓库实现类
 *@Author:Czj
 *@Since:2016年3月26日
 *@Version:1.1.0
 */
@Repository
public class GoodSKUReposityImpl implements GoodSKURepository{

	@Autowired
	TradeGoodSkuExtendMapper  tradeGoodSkuMapper; 
	/* (non-Javadoc)
	 * @see com.club.web.store.domain.repository.GoodSKURepository#addGoodSKU(com.club.web.store.domain.TradeGoodSkuDo)
	 */
	@Override
	public void addGoodSKU(TradeGoodSkuDo tradeGoodSkuDo) {
		// TODO Auto-generated method stub
		TradeGoodSku tradeGoodSku= new TradeGoodSku();
		BeanUtils.copyProperties(tradeGoodSkuDo, tradeGoodSku);
		tradeGoodSkuMapper.insert(tradeGoodSku);
	}

	/* (non-Javadoc)
	 * @see com.club.web.store.domain.repository.GoodSKURepository#updateTradeGoodSKU(com.club.web.store.domain.TradeGoodSkuDo)
	 */
	@Override
	public void updateTradeGoodSKU(TradeGoodSkuDo tradeGoodSkuDo) {
		// TODO Auto-generated method stub
		TradeGoodSku tradeGoodSku= new TradeGoodSku();
		BeanUtils.copyProperties(tradeGoodSkuDo, tradeGoodSku);
		tradeGoodSkuMapper.updateByPrimaryKey(tradeGoodSku);
	}

	/* (non-Javadoc)
	 * @see com.club.web.store.domain.repository.GoodSKURepository#deleteTradeGoodSKU(long)
	 */
	@Override
	public void deleteTradeGoodSKU(long id) {
		// TODO Auto-generated method stub
		tradeGoodSkuMapper.deleteByPrimaryKey(id);
	}
	
	/* (non-Javadoc)
	 * @see com.club.web.store.domain.repository.GoodSKURepository#deleteTradeGoodSKU(long)
	 */
	
	
	public void deleteSkuByGoodId(Long goodId){
		tradeGoodSkuMapper.deleteSkuByGoodId(goodId);;
	}

	@Override
	public void updateSkuByGoodId(TradeGoodSkuDo tradeGoodSkuDo) {
		// TODO Auto-generated method stub
		tradeGoodSkuMapper.updateSkuByGoodId(tradeGoodSkuDo);
	};
	
	@Override
	public List<TradeGoodSkuVo> selectGoodSkuByGoodId(long goodId) {
		// TODO Auto-generated method stub
		return tradeGoodSkuMapper.selectSkuByGoodId(goodId);
	}

	/* (non-Javadoc)
	 * @see com.club.web.store.domain.repository.GoodSKURepository#updateNumByGoodId(java.util.List)
	 */
	@Override
	public void updateNumById(List<GoodUpDo> list) {
		// TODO Auto-generated method stub
		for (GoodUpDo goodUpDo : list) {
			tradeGoodSkuMapper.updateNumById(goodUpDo);
		}
	}
	
	public void updateSaleNumById(List<GoodUpDo> list) {
		// TODO Auto-generated method stub
		for (GoodUpDo goodUpDo : list) {
			tradeGoodSkuMapper.updateSaleNumById(goodUpDo);
		}
	}

	@Override
	public TradeGoodSkuVo selectSkuById(Long id) {
		// TODO Auto-generated method stub
		return tradeGoodSkuMapper.selectById(id);
	}

	@Override
	public List<TradeGoodSkuDo> ifGoodSkuExgist(long cargoSkuId) {
		// TODO Auto-generated method stub
		return tradeGoodSkuMapper.selectGoodSkuNumsByCargoSkuId(cargoSkuId);
	}
	
	@Override
	public List<TradeGoodSkuVo> selectSkuList(long goodId) {
		
		return tradeGoodSkuMapper.selectSkuList(goodId);
	}
}
