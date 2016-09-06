package com.club.web.stock.dao.extend;

import java.util.List;

import com.club.web.stock.dao.base.CargoSkuOptionMapper;
import com.club.web.stock.dao.base.po.CargoSkuItem;

public interface CargoSkuOptionExtendMapper extends CargoSkuOptionMapper{

	public List<CargoSkuItem> getListBySkuId(long skuId);

	public void deleteBySkuId(long skuId);
}