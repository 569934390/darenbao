package com.club.web.store.domain.repository;

import java.util.List;

import com.club.web.store.domain.StoreSupplyPriceDo;
import com.club.web.store.vo.StoreSupplyPriceVo;

public interface StoreSupplyPriceRepository {
    void saveSupplyPrice(StoreSupplyPriceDo supplyPriceDo);
    void deleteById(long id);
	void deleteBySkuId(long id);
	void deleteByGoodId(long goodId);
	void updateSupplyPrice(StoreSupplyPriceDo supplyPriceDo);
	List<StoreSupplyPriceVo> findByGoodId(long goodId);
}
