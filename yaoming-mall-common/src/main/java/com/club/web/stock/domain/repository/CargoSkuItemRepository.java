package com.club.web.stock.domain.repository;

import java.util.List;

import com.club.web.stock.domain.CargoSkuItemDo;

public interface CargoSkuItemRepository {
	public List<CargoSkuItemDo> getListBySkuTypeId(long skuTypeId);
	public List<CargoSkuItemDo> getListBySkuId(long skuId);
	public void delete(long id);
	public CargoSkuItemDo create(long creatorId, long skuTypeId, long baseSkuItemId, String name, String value);
	public void update(CargoSkuItemDo cargoSkuItemDo);
	public void insert(CargoSkuItemDo cargoSkuItemDo);
	public void insertSelectedSkuItem(long creatorId, long skuId, long skuItemId);
	public void deleteSelectedItemsBySkuId(long skuId);

}
