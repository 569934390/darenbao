package com.club.web.stock.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.club.web.stock.domain.CargoSkuDo;

public interface CargoSkuRepository {
	public List<CargoSkuDo> getListByCargoId(long cargoId);
	public void delete(long id);
	public CargoSkuDo getById(long skuId);
	public void update(CargoSkuDo cargoSkuDo);
	public CargoSkuDo create(long creatorId, long cargoId, String code, BigDecimal price);
	public void insert(CargoSkuDo cargoSkuDo);
}
