package com.club.web.stock.dao.extend;

import java.util.List;
import java.util.Map;

import com.club.core.common.Page;
import com.club.web.stock.dao.base.CargoSupplierMapper;
import com.club.web.stock.dao.base.po.CargoSupplier;
import com.club.web.stock.vo.CargoSupplierVo;

public interface CargoSupplierExtendMapper extends CargoSupplierMapper{

	List<Map<String,Object>> queryCargoSupplierPage(Map<String, Object> map);

	List<CargoSupplierVo> findListAll();

	Long queryCargoSupplierCountPage(Map<String, Object> map);

	List<CargoSupplierVo> queryCargoSupplierByName(String name);
 
	
}