package com.club.web.stock.dao.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.web.stock.dao.base.po.CargoSku;
import com.club.web.stock.dao.extend.CargoSkuExtendMapper;
import com.club.web.stock.domain.CargoSku2Do;
import com.club.web.stock.domain.CargoSkuDo;
import com.club.web.stock.domain.repository.CargoSkuRepository;
import com.club.web.util.IdGenerator;

@Repository
public class CargoSkuRepositoryImpl implements CargoSkuRepository {

	@Autowired private CargoSkuExtendMapper cargoSkuDao;


	@Override
	public CargoSkuDo create(long creatorId, long cargoId, String code, BigDecimal price) {
		CargoSku2Do cargoSkuDo = new CargoSku2Do();
		cargoSkuDo.setId(IdGenerator.getDefault().nextId());
		cargoSkuDo.setCode(code);
		cargoSkuDo.setPrice(price);
		cargoSkuDo.setCargoId(cargoId);
		cargoSkuDo.setCreateBy(creatorId);
		cargoSkuDo.setUpdateBy(creatorId);
		return cargoSkuDo;
	}
	
	@Override
	public List<CargoSkuDo> getListByCargoId(long cargoId) {
		return getDomainByPo(cargoSkuDao.getListByCargoId(cargoId));
	}

	@Override
	public void delete(long id) {
		cargoSkuDao.deleteByPrimaryKey(id);
	}

	@Override
	public CargoSkuDo getById(long skuId) {
		return getDomainByPo(cargoSkuDao.selectByPrimaryKey(skuId));
	}

	@Override
	public void update(CargoSkuDo cargoSkuDo) {
		cargoSkuDao.updateByPrimaryKey(getPoByDomain(cargoSkuDo));
	}

	@Override
	public void insert(CargoSkuDo cargoSkuDo) {
		cargoSkuDao.insert(getPoByDomain(cargoSkuDo));
	}

	private CargoSkuDo getDomainByPo(CargoSku s) {
		if(s==null)
			return null;
		CargoSku2Do t = new CargoSku2Do();
		t.setId(s.getId());
		t.setCargoId(s.getCargoId());
		t.setCode(s.getCode());
		t.setPrice(s.getPrice());
		t.setCreateBy(s.getCreateBy());
		t.setCreateTime(s.getCreateTime());
		t.setUpdateBy(s.getUpdateBy());
		t.setUpdateTime(s.getUpdateTime());
		return t;
	}

	private CargoSku getPoByDomain(CargoSkuDo s) {
		if(s==null)
			return null;
		CargoSku t = new CargoSku();
		t.setId(s.getId());
		t.setCargoId(s.getCargoId());
		t.setCode(s.getCode());
		t.setPrice(s.getPrice());
		t.setCreateBy(s.getCreateBy());
		t.setCreateTime(s.getCreateTime());
		t.setUpdateBy(s.getUpdateBy());
		t.setUpdateTime(s.getUpdateTime());
		return t;
	}

	private List<CargoSkuDo> getDomainByPo(List<CargoSku> list){
		List<CargoSkuDo> result = new ArrayList<>();
		if(list==null)
			return result;
		for (CargoSku src : list)
			result.add(getDomainByPo(src));
		return result;
	}
}
