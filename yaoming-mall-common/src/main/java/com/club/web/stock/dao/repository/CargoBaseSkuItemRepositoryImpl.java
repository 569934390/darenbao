package com.club.web.stock.dao.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.stock.dao.base.po.CargoBaseSkuItem;
import com.club.web.stock.dao.extend.CargoBaseSkuItemExtendMapper;
import com.club.web.stock.domain.CargoBaseSkuItemDo;
import com.club.web.stock.domain.repository.CargoBaseSkuItemRepository;
import com.club.web.stock.vo.CargoBaseSkuItemVo;

/**   
* @Title: CargoBaseSkuItemRepositoryImpl.java
* @Package com.club.web.stock.dao.repository
* @Description: 商品基础规格选项domain接口实现类
* @author hqLin   
* @date 2016/03/19
* @version V1.0   
*/

@Repository
public class CargoBaseSkuItemRepositoryImpl implements CargoBaseSkuItemRepository {

	@Autowired 
	CargoBaseSkuItemExtendMapper skuItemDao;

	@Override
	public int addBaseSkuItem(CargoBaseSkuItemDo cargoBaseSkuItemDo) {
		CargoBaseSkuItem cargoBaseSkuItem = new CargoBaseSkuItem();
		BeanUtils.copyProperties(cargoBaseSkuItemDo, cargoBaseSkuItem);
		int index = skuItemDao.insert(cargoBaseSkuItem);
		
		return index;
	}
	
	@Override
	public int deleteSkuItemByBaseSkuTypeId(Long baseSkuTypeId, Long id) {
		int index = skuItemDao.deleteByBaseSkuTypeId(baseSkuTypeId, id);
		
		return index;
	}
	
	@Override
	public List<CargoBaseSkuItemVo> selectSkuItemBySkuTypeId(Long id) {
		List<CargoBaseSkuItemVo> resultList = skuItemDao.selectSkuItemBySkuTypeId(id);
		
		return resultList;
	}
	
	@Override
	public List<CargoBaseSkuItemVo> selectSkuItemAndImgBySkuTypeId(Long id) {
		List<CargoBaseSkuItemVo> resultList = skuItemDao.selectSkuItemAndImgBySkuTypeId(id);
		
		return resultList;
	}
	
	@Override
	public CargoBaseSkuItemDo create() {
		CargoBaseSkuItemDo newCargoBaseSkuItemDo = new CargoBaseSkuItemDo();
		
		return newCargoBaseSkuItemDo;
	}
	
	@Override
	public CargoBaseSkuItemVo selectSkuItemById(Long id) {
		CargoBaseSkuItem cargoBaseSkuItem = skuItemDao.selectByPrimaryKey(id);
		if(cargoBaseSkuItem != null){
			CargoBaseSkuItemVo cargoBaseSkuItemVo = new CargoBaseSkuItemVo();
			BeanUtils.copyProperties(cargoBaseSkuItem, cargoBaseSkuItemVo);
			cargoBaseSkuItemVo.setId(cargoBaseSkuItem.getId()+"");
			cargoBaseSkuItemVo.setBaseSkuTypeId(cargoBaseSkuItem.getBaseSkuTypeId()+"");
			return cargoBaseSkuItemVo;
		}
		
		return null;
	}
}