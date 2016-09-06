package com.club.web.stock.domain.repository;

import com.club.web.stock.domain.CargoDo;

public interface CargoRepository {
	public CargoDo getCargoById(long cargoId);
	public void insert(CargoDo cargoDo);
	public void update(CargoDo cargoDo);
	public CargoDo create(long creatorId, String cargoNo, String cargoName, long classifyId, long supplierId, 
			long brandId, long smallImageId, long showImageGroupId, long detailImageGroupId);
	public void delete(long id);
}
