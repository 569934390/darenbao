package com.club.web.stock.dao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.web.stock.dao.base.po.Cargo;
import com.club.web.stock.dao.extend.CargoExtendMapper;
import com.club.web.stock.domain.CargoDo;
import com.club.web.stock.domain.repository.CargoRepository;
import com.club.web.util.IdGenerator;

@Repository
public class CargoRepositoryImpl implements CargoRepository{

	@Autowired private CargoExtendMapper cargoDao;

	@Override
	public CargoDo create(long creatorId, String cargoNo, String cargoName, long classifyId, long supplierId, long brandId,
			long smallImageId, long showImageGroupId, long detailImageGroupId) {
		CargoDo cargoDo = new CargoDo();
		cargoDo.setId(IdGenerator.getDefault().nextId());
		cargoDo.setCargoNo(cargoNo);
		cargoDo.setName(cargoName);
		cargoDo.setClassifyId(classifyId);
		if(supplierId>0)
			cargoDo.setSupplierId(supplierId);
		cargoDo.setBrandId(brandId);
		cargoDo.setSmallImageId(smallImageId);
		cargoDo.setShowImageGroupId(showImageGroupId);
		cargoDo.setDetailImageGroupId(detailImageGroupId);
		cargoDo.setCreateBy(creatorId);
		cargoDo.setUpdateBy(creatorId);
		return cargoDo;
	}
	
	@Override
	public CargoDo getCargoById(long cargoId) {
		return getDomainByPo(cargoDao.selectByPrimaryKey(cargoId));
	}

	@Override
	public void insert(CargoDo cargoDo) {
		cargoDao.insert(getPoByDomain(cargoDo));
	}

	@Override
	public void update(CargoDo cargoDo) {
		cargoDao.updateByPrimaryKeySelective(getPoByDomain(cargoDo));
	}

	private CargoDo getDomainByPo(Cargo cargo){
		if(cargo==null)
			return null;
		CargoDo c = new CargoDo();
		c.setId(cargo.getId());
		if(cargo.getSupplierId()!=null)
			c.setSupplierId(cargo.getSupplierId());
		c.setBrandId(cargo.getBrandId());
		c.setClassifyId(cargo.getClassifyId());
		c.setName(cargo.getName());
		c.setCargoNo(cargo.getCargoNo());
		c.setDescription(cargo.getDescription());
		c.setSmallImageId(cargo.getSmallImageId());
		c.setShowImageGroupId(cargo.getShowImageGroupId());
		c.setDetailImageGroupId(cargo.getDetailImageGroupId());	
		c.setCreateBy(cargo.getCreateBy());
		c.setCreateTime(cargo.getCreateTime());
		c.setUpdateBy(cargo.getUpdateBy());
		c.setUpdateTime(cargo.getUpdateTime());
		return c;
	}
	
	private Cargo getPoByDomain(CargoDo cargo){
		if(cargo==null)
			return null;
		Cargo c = new Cargo();
		c.setId(cargo.getId());
		c.setSupplierId(cargo.getSupplierId());
		c.setBrandId(cargo.getBrandId());
		c.setClassifyId(cargo.getClassifyId());
		c.setName(cargo.getName());
		c.setCargoNo(cargo.getCargoNo());
		c.setDescription(cargo.getDescription());
		c.setSmallImageId(cargo.getSmallImageId());
		c.setShowImageGroupId(cargo.getShowImageGroupId());
		c.setDetailImageGroupId(cargo.getDetailImageGroupId());	
		c.setCreateBy(cargo.getCreateBy());
		c.setCreateTime(cargo.getCreateTime());
		c.setUpdateBy(cargo.getUpdateBy());
		c.setUpdateTime(cargo.getUpdateTime());
		return c;
	}

	@Override
	public void delete(long id) {
		cargoDao.deleteByPrimaryKey(id);
	}
	
}
