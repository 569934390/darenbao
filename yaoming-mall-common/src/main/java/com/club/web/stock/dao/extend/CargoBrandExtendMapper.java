package com.club.web.stock.dao.extend;

import java.util.List;
import java.util.Map;

import com.club.web.stock.dao.base.CargoBrandMapper;
import com.club.web.stock.dao.base.po.CargoBrand;
import com.club.web.stock.vo.CargoBrandVo;
import com.club.web.stock.vo.CargoBrandVo;


public interface CargoBrandExtendMapper extends CargoBrandMapper{

	List<Map<String,Object>> queryCargoBrandPage(Map<String, Object> map);

	List<CargoBrandVo> findListAll();

	Long queryCargoBrandCountPage(Map<String, Object> map);

	List<CargoBrandVo> queryCargoBrandByName(String name);
	
}