package com.club.web.stock.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.club.core.common.Page;
import com.club.web.stock.vo.CargoSupplierVo;

public interface CargoSupplierService {

	//Map<String,Object> addCargoSupplier(CargoSupplier cargoSupplier);

	//Map<String,Object> updateCargoSupplier(CargoSupplier cargoSupplier);

	Page<Map<String, Object>> queryCargoSupplierPage(Page<Map<String, Object>> page);

	List<CargoSupplierVo> findListAll();
	
	CargoSupplierVo findCargoSupplierById(Long id);

	Map<String, Object> deleteCargoSupplier(String idStr);

	Map<String,Object> saveOrUpdateCargoSupplier(CargoSupplierVo cargoSupplier,HttpServletRequest request);
}
