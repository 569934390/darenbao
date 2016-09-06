package com.club.web.store.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.club.web.store.dao.base.StoreSupplyPriceMapper;
import com.club.web.store.vo.StoreSupplyPriceVo;

public interface StoreSupplyPriceExtendMapper extends StoreSupplyPriceMapper{
	public void deleteBySkuId(@Param("goodSkuId")long id);
	public void deleteByGoodId(@Param("goodId")long goodId);
	public List<StoreSupplyPriceVo> findByGoodId(@Param("goodId")long goodId);
}