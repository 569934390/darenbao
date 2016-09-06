package com.club.web.stock.dao.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.web.stock.dao.base.po.CargoSkuItem;
import com.club.web.stock.dao.base.po.CargoSkuOption;
import com.club.web.stock.dao.extend.CargoSkuItemExtendMapper;
import com.club.web.stock.dao.extend.CargoSkuOptionExtendMapper;
import com.club.web.stock.domain.CargoSkuItem2Do;
import com.club.web.stock.domain.CargoSkuItemDo;
import com.club.web.stock.domain.repository.CargoSkuItemRepository;
import com.club.web.util.IdGenerator;

@Repository
public class CargoSkuItemRepositoryImpl implements CargoSkuItemRepository {

	@Autowired private CargoSkuItemExtendMapper cargoSkuItemDao;
	@Autowired private CargoSkuOptionExtendMapper cargoSkuOptionDao;
	
	@Override
	public List<CargoSkuItemDo> getListBySkuTypeId(long skuTypeId) {
		return getDomainByPo(cargoSkuItemDao.getListBySkuTypeId(skuTypeId));
	}

	@Override
	public List<CargoSkuItemDo> getListBySkuId(long skuId) {
		return getDomainByPo(cargoSkuOptionDao.getListBySkuId(skuId));
	}

	@Override
	public void delete(long id) {
		cargoSkuItemDao.deleteByPrimaryKey(id);
	}

	@Override
	public CargoSkuItemDo create(long creatorId, long skuTypeId, long baseSkuItemId, String name, String value) {
		CargoSkuItem2Do csiDo = new CargoSkuItem2Do();
		csiDo.setId(IdGenerator.getDefault().nextId());
		csiDo.setCargoSkuTypeId(skuTypeId);
		csiDo.setCargoBaseSkuItemId(baseSkuItemId);
		csiDo.setName(name);
		csiDo.setValue(value);
		csiDo.setCreateBy(creatorId);
		csiDo.setUpdateBy(creatorId);
		return csiDo;
	}

	@Override
	public void update(CargoSkuItemDo cargoSkuItemDo) {
		cargoSkuItemDao.updateByPrimaryKey(getPoByDomain(cargoSkuItemDo));
	}

	@Override
	public void insert(CargoSkuItemDo cargoSkuItemDo) {
		cargoSkuItemDao.insert(getPoByDomain(cargoSkuItemDo));
	}

	@Override
	public void insertSelectedSkuItem(long creatorId, long skuId, long skuItemId) {
		CargoSkuOption cso = new CargoSkuOption();
		cso.setId(IdGenerator.getDefault().nextId());
		cso.setCargoSkuId(skuId);
		cso.setCargoSkuItemId(skuItemId);
		cso.setCreateBy(creatorId);
		cso.setCreateTime(new Date());
		cso.setUpdateBy(creatorId);
		cso.setUpdateTime(cso.getCreateTime());
		cargoSkuOptionDao.insert(cso);
	}

	@Override
	public void deleteSelectedItemsBySkuId(long skuId) {
		cargoSkuOptionDao.deleteBySkuId(skuId);
	}
	
	private CargoSkuItem getPoByDomain(CargoSkuItemDo s) {
		if(s==null)
			return null;
		CargoSkuItem t = new CargoSkuItem();
		t.setId(s.getId());
		t.setName(s.getName());
		t.setCargoSkuTypeId(s.getCargoSkuTypeId());
		t.setCargoBaseSkuItemId(s.getCargoBaseSkuItemId());
		t.setValue(s.getValue());
		t.setCreateBy(s.getCreateBy());
		t.setCreateTime(s.getCreateTime());
		t.setUpdateBy(s.getUpdateBy());
		t.setUpdateTime(s.getUpdateTime());
		return t;
	}
	
	private CargoSkuItemDo getDomainByPo(CargoSkuItem s) {
		if(s==null)
			return null;
		CargoSkuItem2Do t = new CargoSkuItem2Do();
		t.setId(s.getId());
		t.setName(s.getName());
		t.setCargoSkuTypeId(s.getCargoSkuTypeId());
		if(s.getCargoBaseSkuItemId()!=null)
			t.setCargoBaseSkuItemId(s.getCargoBaseSkuItemId());
		t.setValue(s.getValue());
		t.setCreateBy(s.getCreateBy());
		t.setCreateTime(s.getCreateTime());
		t.setUpdateBy(s.getUpdateBy());
		t.setUpdateTime(s.getUpdateTime());
		return t;
	}

	private List<CargoSkuItemDo> getDomainByPo(List<CargoSkuItem> list){
		List<CargoSkuItemDo> result = new ArrayList<>();
		if(list==null)
			return result;
		for (CargoSkuItem src : list)
			result.add(getDomainByPo(src));
		return result;
	}
	
}
