package com.club.web.stock.dao.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.stock.dao.base.po.CargoBaseSkuType;
import com.club.web.stock.dao.extend.CargoBaseSkuTypeExtendMapper;
import com.club.web.stock.domain.CargoBaseSkuTypeDo;
import com.club.web.stock.domain.repository.CargoBaseSkuTypeRepository;
import com.club.web.stock.vo.CargoBaseSkuTypeVo;

/**   
* @Title: CargoBaseSkuTypeRepositoryImpl.java
* @Package com.club.web.stock.dao.repository
* @Description: 商品基础规格domain接口实现类
* @author hqLin   
* @date 2016/03/19
* @version V1.0   
*/

@Repository
public class CargoBaseSkuTypeRepositoryImpl implements CargoBaseSkuTypeRepository {

	@Autowired 
	CargoBaseSkuTypeExtendMapper skuTypeDao;

	@Override
	public int addBaseSkuType(CargoBaseSkuTypeDo baseSkuType) {
		CargoBaseSkuType record = new CargoBaseSkuType();
		BeanUtils.copyProperties(baseSkuType, record);
		int index = skuTypeDao.insert(record);
		
		return index;
	}
	
	@Override
	public int editBaseSkuType(CargoBaseSkuTypeDo baseSkuTypeDo) {
		CargoBaseSkuType cargoBaseSkuType = new CargoBaseSkuType();
		BeanUtils.copyProperties(baseSkuTypeDo, cargoBaseSkuType);
		cargoBaseSkuType.setId(Long.valueOf(baseSkuTypeDo.getId()));
		int index = skuTypeDao.updateByPrimaryKeySelective(cargoBaseSkuType);
		
		return index;
	}
	
	@Override
	public int deleteBaseSkuType(Long id) {
		int index = skuTypeDao.deleteByPrimaryKey(id);
		
		return index;
	}
	
	@Override
	public List<CargoBaseSkuTypeVo> selectBySkuNameAndSkuType(Map<String, Object> map) {
		List<CargoBaseSkuTypeVo> resultList = skuTypeDao.selectBySkuNameAndSkuType(map);
		
		return resultList;
	}
	
	@Override
	public CargoBaseSkuTypeDo voChangeDo(CargoBaseSkuTypeVo cargoBaseSkuTypeVo) {
		CargoBaseSkuTypeDo newCargoBaseSkuTypeDo = create();
		BeanUtils.copyProperties(cargoBaseSkuTypeVo, newCargoBaseSkuTypeDo);
		newCargoBaseSkuTypeDo.setId(Long.valueOf(cargoBaseSkuTypeVo.getId()));
		
		return newCargoBaseSkuTypeDo;
	}
	
	@Override
	public CargoBaseSkuTypeDo create() {
		CargoBaseSkuTypeDo newCargoBaseSkuTypeDo = new CargoBaseSkuTypeDo();
		
		return newCargoBaseSkuTypeDo;
	}
	
	@Override
	public CargoBaseSkuTypeDo selectCargoBaseSkuTypeById(Long id) {
		CargoBaseSkuType cargoBaseSkuType = skuTypeDao.selectByPrimaryKey(id);
		if(cargoBaseSkuType != null){
			CargoBaseSkuTypeDo cargoBaseSkuTypeDo = create();
			BeanUtils.copyProperties(cargoBaseSkuType, cargoBaseSkuTypeDo);
			return cargoBaseSkuTypeDo;
		}
		return null;
	}
	
	@Override
	public Long queryCargoBaseSkuTypeCountPage(Map<String, Object> map) {
		
		Long count = skuTypeDao.queryCargoBaseSkuTypeCountPage(map);
		
		return count;
	}
	
	@Override
	public List<CargoBaseSkuTypeVo> selectCargoBaseSkuTypeList() {
		
		return skuTypeDao.selectCargoBaseSkuTypeList();
	}
}