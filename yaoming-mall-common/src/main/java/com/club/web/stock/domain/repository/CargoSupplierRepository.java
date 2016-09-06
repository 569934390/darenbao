package com.club.web.stock.domain.repository;

import java.util.List;
import java.util.Map;

import com.club.core.common.Page;
import com.club.web.stock.domain.CargoSupplierDo;
import com.club.web.stock.vo.CargoSupplierVo;

public interface CargoSupplierRepository {
	
	public CargoSupplierDo create(CargoSupplierVo cargoSupplierVo);

	Long queryCargoSupplierCountPage(Page<Map<String, Object>> page);

	List<Map<String,Object>> queryCargoSupplierPage(Page<Map<String, Object>> page);

	List<CargoSupplierVo> findListAll();

	void deleteByPrimaryKey(long id);

	List<CargoSupplierVo> queryCargoSupplierByName(String name);

	CargoSupplierDo getCargoSupplierDoById(Long id);

	public void insert(CargoSupplierDo cargoSupplierDo);

	public void update(CargoSupplierDo cargoSupplierDo);

	public CargoSupplierVo getCargoSupplierVoById(Long id);
}
