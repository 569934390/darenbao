package com.club.web.stock.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.club.core.common.Page;
import com.club.web.stock.vo.CargoBrandVo;

public interface CargoBrandService {

	//Map<String,Object> addCargoBrand(CargoBrand cargoBrand);

	//Map<String,Object> updateCargoBrand(CargoBrand cargoBrand);

	Page<Map<String, Object>> queryCargoBrandPage(Page<Map<String, Object>> page);

	List<CargoBrandVo> findListAll();
	
	CargoBrandVo findCargoBrandById(Long id);

	Map<String, Object> deleteCargoBrand(String idStr);

	Map<String,Object> saveOrUpdateCargoBrand(CargoBrandVo cargoBrand,HttpServletRequest request);


}
