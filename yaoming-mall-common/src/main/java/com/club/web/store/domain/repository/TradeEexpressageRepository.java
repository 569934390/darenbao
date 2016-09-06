package com.club.web.store.domain.repository;

import com.club.web.store.domain.TradeEexpressageDo;

public interface TradeEexpressageRepository {
	
    public int insert(TradeEexpressageDo tradeEexpressageDo);
    
    public int update(TradeEexpressageDo tradeEexpressageDo);
    
    public int delet(TradeEexpressageDo tradeEexpressageDo);
    
    public int updateState(TradeEexpressageDo tradeEexpressageDo);
	
}
