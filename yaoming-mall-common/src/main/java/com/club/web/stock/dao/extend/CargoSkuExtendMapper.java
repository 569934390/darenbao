package com.club.web.stock.dao.extend;

import java.util.List;

import com.club.web.stock.dao.base.CargoSkuMapper;
import com.club.web.stock.dao.base.po.CargoSku;
import com.club.web.stock.vo.CargoSkuSimpleVo;

public interface CargoSkuExtendMapper extends CargoSkuMapper {

	public List<CargoSku> getListByCargoId(long cargoId);
	public List<CargoSkuSimpleVo> getSkuList(long cargoId);
}