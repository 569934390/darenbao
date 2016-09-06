package com.club.web.stock.domain.repository;

import java.util.List;

import com.club.web.stock.domain.CargoClassifyDo;

/**
 * 货品分类仓库接口
 * 
 * @author zhuzd
 *
 */
public interface CargoClassifyRepository {

	public List<CargoClassifyDo> findDoListByIds(String ids);

	public CargoClassifyDo findDoById(Long id);

	public CargoClassifyDo findDoByIdAndStatus(Long id, Integer status);

	public List<CargoClassifyDo> findDoByParentId(Long parentId);

	public int updateStatus(CargoClassifyDo children);

	public int delete(CargoClassifyDo cargoClassifyDo);

	public int add(CargoClassifyDo cargoClassifyDo);

	public int modify(CargoClassifyDo cargoClassifyDo);

	public List<CargoClassifyDo> findDoNoIds(List<Long> ids);

	public List<CargoClassifyDo> findDoByParentIdAndStatus(Long parentId, Integer status);

	public List<Long> getAllIdsByIdAndStatus(Long id, Integer status);

	public List<CargoClassifyDo> findAllDoByStatus(Integer status);

	String queryNameById(long id);

}
