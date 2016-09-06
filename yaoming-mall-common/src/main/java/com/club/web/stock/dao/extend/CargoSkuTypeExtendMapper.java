package com.club.web.stock.dao.extend;

import java.util.List;

import com.club.web.stock.dao.base.CargoSkuTypeMapper;
import com.club.web.stock.dao.base.po.CargoSkuType;

public interface CargoSkuTypeExtendMapper extends CargoSkuTypeMapper {

	public List<CargoSkuType> getListByCargoId(long cargoId);

}
