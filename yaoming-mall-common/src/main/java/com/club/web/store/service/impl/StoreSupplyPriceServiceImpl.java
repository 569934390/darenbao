package com.club.web.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.web.store.domain.repository.StoreSupplyPriceRepository;
import com.club.web.store.service.StoreSupplyPriceService;
import com.club.web.store.vo.StoreSupplyPriceVo;
@Service
public class StoreSupplyPriceServiceImpl implements StoreSupplyPriceService{
 
	@Autowired
	private StoreSupplyPriceRepository storeSupplyPriceRepository;
	@Override
	public List<StoreSupplyPriceVo> findByGoodId(long goodId) {
		// TODO Auto-generated method stub
		return storeSupplyPriceRepository.findByGoodId(goodId);
	}

}
