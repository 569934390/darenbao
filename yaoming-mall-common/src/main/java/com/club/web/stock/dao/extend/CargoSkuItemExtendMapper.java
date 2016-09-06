package com.club.web.stock.dao.extend;

import java.util.List;

import com.club.web.stock.dao.base.CargoSkuItemMapper;
import com.club.web.stock.dao.base.po.CargoSkuItem;

public interface CargoSkuItemExtendMapper extends CargoSkuItemMapper {

	public List<CargoSkuItem> getListBySkuTypeId(long skuTypeId);
}