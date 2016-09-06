package com.club.web.stock.dao.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.web.stock.dao.base.po.CargoSkuType;
import com.club.web.stock.dao.extend.CargoSkuTypeExtendMapper;
import com.club.web.stock.domain.CargoSkuType2Do;
import com.club.web.stock.domain.CargoSkuTypeDo;
import com.club.web.stock.domain.repository.CargoSkuTypeRepository;
import com.club.web.util.IdGenerator;

@Repository
public class CargoSkuTypeRepositoryImpl implements CargoSkuTypeRepository {

	@Autowired CargoSkuTypeExtendMapper cargoSkuTypeDao;
	
	@Override
	public List<CargoSkuTypeDo> getListByCargoId(long cargoId) {
		return getDomainByPo(cargoSkuTypeDao.getListByCargoId(cargoId));
	}

	@Override
	public void delete(long cargoSkuTypeId) {
		cargoSkuTypeDao.deleteByPrimaryKey(cargoSkuTypeId);
	}

	@Override
	public void insert(CargoSkuTypeDo cargoSkuTypeDo) {
		cargoSkuTypeDao.insert(getPoByDomain(cargoSkuTypeDo));
	}

	@Override
	public void update(CargoSkuTypeDo cargoSkuTypeDo) {
		cargoSkuTypeDao.updateByPrimaryKeySelective(getPoByDomain(cargoSkuTypeDo));
	}

	@Override
	public CargoSkuTypeDo getById(long cargoSkuTypeId) {
		return getDomainByPo(cargoSkuTypeDao.selectByPrimaryKey(cargoSkuTypeId));
	}

	@Override
	public CargoSkuTypeDo create(long creatorId, long cargoBaseSkuTypeId, long cargoId, String name, int type) {
		CargoSkuType2Do c = new CargoSkuType2Do();
		c.setId(IdGenerator.getDefault().nextId());
		c.setCargoBaseSkuTypeId(cargoBaseSkuTypeId);
		c.setCargoId(cargoId);
		c.setName(name);
		c.setType(type);
		c.setCreateBy(creatorId);
		c.setUpdateBy(creatorId);
		return c;
	}
	
	private CargoSkuTypeDo getDomainByPo(CargoSkuType src){
		if(src==null)
			return null;
		CargoSkuType2Do target = new CargoSkuType2Do();
		target.setId(src.getId());
		target.setCargoId(src.getCargoId());
		target.setCargoBaseSkuTypeId(src.getCargoBaseSkuTypeId());
		target.setName(src.getName());
		target.setType(src.getType());
		target.setCreateBy(src.getCreateBy());
		target.setCreateTime(src.getCreateTime());
		target.setUpdateBy(src.getUpdateBy());
		target.setUpdateTime(src.getUpdateTime());
		return target;
	}
	
	private CargoSkuType getPoByDomain(CargoSkuTypeDo src){
		if(src==null)
			return null;
		CargoSkuType target = new CargoSkuType();
		target.setId(src.getId());
		target.setCargoId(src.getCargoId());
		target.setCargoBaseSkuTypeId(src.getCargoBaseSkuTypeId());
		target.setName(src.getName());
		target.setType(src.getType());
		target.setCreateBy(src.getCreateBy());
		target.setCreateTime(src.getCreateTime());
		target.setUpdateBy(src.getUpdateBy());
		target.setUpdateTime(src.getUpdateTime());
		return target;
	}

	private List<CargoSkuTypeDo> getDomainByPo(List<CargoSkuType> list){
		List<CargoSkuTypeDo> result = new ArrayList<>();
		if(list==null)
			return result;
		for (CargoSkuType src : list)
			result.add(getDomainByPo(src));
		return result;
	}
	
}
