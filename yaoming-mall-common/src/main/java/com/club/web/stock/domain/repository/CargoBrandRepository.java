package com.club.web.stock.domain.repository;

import java.util.List;
import java.util.Map;

import com.club.core.common.Page;
import com.club.web.stock.dao.base.po.CargoBrand;
import com.club.web.stock.domain.CargoBrandDo;
import com.club.web.stock.domain.CargoBrandDo;
import com.club.web.stock.vo.CargoBrandVo;
import com.club.web.stock.vo.CargoBrandVo;

public interface CargoBrandRepository {

	public CargoBrandDo create(CargoBrandVo cargoBrandVo);

	Long queryCargoBrandCountPage(Page<Map<String, Object>> page);

	List<Map<String,Object>> queryCargoBrandPage(Page<Map<String, Object>> page);

	List<CargoBrandVo> findListAll();

	void deleteByPrimaryKey(long id);

	List<CargoBrandVo> queryCargoBrandByName(String name);

	CargoBrandDo getCargoBrandDoById(Long id);

	public void insert(CargoBrandDo cargoBrandDo);

	public void update(CargoBrandDo cargoBrandDo);

	public CargoBrandVo getCargoBrandVoById(Long id);
}
