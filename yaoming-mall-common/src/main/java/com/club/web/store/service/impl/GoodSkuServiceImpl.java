
package com.club.web.store.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.club.framework.util.BeanUtils;
import com.club.web.store.domain.TradeGoodSkuDo;
import com.club.web.store.domain.repository.GoodSKURepository;
import com.club.web.store.service.GoodSkuService;
import com.club.web.store.vo.TradeGoodSkuVo;
import com.club.web.util.IdGenerator;

/**
 *@Title:
 *@Description:service层接口实现
 *@Author:Czj
 *@Since:2016年3月26日
 *@Version:1.1.0
 */
@Transactional
@Service("goodSkuServiceImpl")
public class GoodSkuServiceImpl implements GoodSkuService {

	/* (non-Javadoc)
	 * @see com.club.web.store.service.GoodSkuService#addGood(com.club.web.store.vo.TradeGoodSkuVo)
	 */
	private Logger logger = LoggerFactory.getLogger(GoodSkuServiceImpl.class);
	
	@Autowired
	private GoodSKURepository goodSKURepository;

	/* (non-Javadoc)
	 * @see com.club.web.store.service.GoodSkuService#addGoodSku(com.club.web.store.vo.TradeGoodSkuVo)
	 */
	@Override
	public void addGoodSku(TradeGoodSkuVo vo) {
		// TODO Auto-generated method stub
		TradeGoodSkuDo tradeGoodSkuDo = new TradeGoodSkuDo();
	    BeanUtils.copyProperties(vo, tradeGoodSkuDo);
	    tradeGoodSkuDo.setId(Long.parseLong(vo.getId()));
	    tradeGoodSkuDo.setGoodId(Long.parseLong(vo.getGoodId()));
	    tradeGoodSkuDo.setCargoSkuId(Long.parseLong(vo.getCargoSkuId()));
	    tradeGoodSkuDo.saveSelf();
	}

	/* (non-Javadoc)
	 * @see com.club.web.store.service.GoodSkuService#updateGoodSku(com.club.web.store.vo.TradeGoodSkuVo)
	 */
	@Override
	public void updateGoodSku(TradeGoodSkuVo vo) {
		// TODO Auto-generated method stub
		TradeGoodSkuDo tradeGoodSkuDo = new TradeGoodSkuDo();
	    BeanUtils.copyProperties(vo, tradeGoodSkuDo);
	    tradeGoodSkuDo.setId(Long.valueOf(vo.getId()));
	    goodSKURepository.updateSkuByGoodId(tradeGoodSkuDo);	
	}
	
	/* (non-Javadoc)
	 * @see com.club.web.store.service.GoodSkuService#deleteSkuById(long)
	 */
	@Override
	public void deleteSkuById(long id) {
		// TODO Auto-generated method stub
		goodSKURepository.deleteTradeGoodSKU(id);
	}

	@Override
	public TradeGoodSkuVo getSkuvoById(long id) {
		// TODO Auto-generated method stub
		return goodSKURepository.selectSkuById(id);
	}

	@Override
	public Long ifGoodSkuExgist(long cargoSkuId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
