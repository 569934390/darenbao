/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.club.web.store.service;

import com.club.web.store.vo.TradeGoodSkuVo;

/**
 *@Title:
 *@Description:GoodSku Service层接口定义
 *@Author:Administrator
 *@Since:2016年3月26日
 *@Version:1.1.0
 */
public interface GoodSkuService {
	void  addGoodSku(TradeGoodSkuVo vo);
    void  updateGoodSku(TradeGoodSkuVo vo);
    void deleteSkuById(long id);
    TradeGoodSkuVo getSkuvoById(long id);
    public Long ifGoodSkuExgist(long cargoSkuId);
}
