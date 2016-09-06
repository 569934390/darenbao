package com.club.web.store.service;

import java.util.List;

import com.club.web.store.vo.StoreSupplyPriceVo;

public interface StoreSupplyPriceService {
	List<StoreSupplyPriceVo> findByGoodId(long goodId);
}
