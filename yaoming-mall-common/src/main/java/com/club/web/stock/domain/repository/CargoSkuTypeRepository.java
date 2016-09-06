package com.club.web.stock.domain.repository;

import java.util.List;

import com.club.web.stock.domain.CargoSkuTypeDo;

public interface CargoSkuTypeRepository {
	public List<CargoSkuTypeDo> getListByCargoId(long cargoId);
	public void delete(long cargoSkuTypeId);
	public void insert(CargoSkuTypeDo cargoSkuTypeDo);
	public void update(CargoSkuTypeDo cargoSkuTypeDo);
	public CargoSkuTypeDo getById(long cargoSkuTypeId);
	public CargoSkuTypeDo create(long creatorId, long cargoBaseSkuTypeId, long cargoId, String name, int type);
}
