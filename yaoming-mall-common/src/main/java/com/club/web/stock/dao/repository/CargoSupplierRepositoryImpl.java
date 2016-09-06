package com.club.web.stock.dao.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.core.common.Page;
import com.club.framework.util.BeanUtils;
import com.club.web.stock.dao.base.po.CargoSupplier;
import com.club.web.stock.dao.extend.CargoSupplierExtendMapper;
import com.club.web.stock.domain.CargoSupplier2Do;
import com.club.web.stock.domain.CargoSupplierDo;
import com.club.web.stock.domain.repository.CargoSupplierRepository;
import com.club.web.stock.vo.CargoSupplierVo;

/**
 * @Title: CargoSupplierRepositoryImpl.java
 * @Package com.club.web.stock.dao.repository
 * @Description: 供应商RepositoryImpl
 * @author 柳伟军
 * @date 2016年3月26日 上午9:30:13
 * @version V1.0
 */
@Repository
public class CargoSupplierRepositoryImpl implements CargoSupplierRepository {

	@Autowired
	private CargoSupplierExtendMapper supplierDao;

	@Override
	public CargoSupplierDo create(CargoSupplierVo cargoSupplierVo) {
		if (cargoSupplierVo == null)
			return null;
		CargoSupplier2Do cargoSupplierDo = new CargoSupplier2Do();
		BeanUtils.copyProperties(cargoSupplierVo, cargoSupplierDo);
		cargoSupplierDo.setId(Long.parseLong(cargoSupplierVo.getId()));
		return cargoSupplierDo;
	}

	private CargoSupplierDo getDomainByPo(CargoSupplier cargoSupplier) {
		if (cargoSupplier == null)
			return null;
		CargoSupplier2Do cargoSupplierDo = new CargoSupplier2Do();
		BeanUtils.copyProperties(cargoSupplier, cargoSupplierDo);
		return cargoSupplierDo;
	}

	private CargoSupplier getPoByDomain(CargoSupplierDo cargoSupplierDo) {
		if (cargoSupplierDo == null)
			return null;
		CargoSupplier cargoSupplier = new CargoSupplier();
		BeanUtils.copyProperties(cargoSupplierDo, cargoSupplier);
		return cargoSupplier;
	}

	private CargoSupplierVo getVoByPo(CargoSupplier cargoSupplier) {
		if (cargoSupplier == null)
			return null;
		CargoSupplierVo cargoSupplierVo = new CargoSupplierVo();
		BeanUtils.copyProperties(cargoSupplier, cargoSupplierVo);
		cargoSupplierVo.setId(cargoSupplier.getId() + "");
		return cargoSupplierVo;
	}

	@Override
	public CargoSupplierDo getCargoSupplierDoById(Long id) {
		return getDomainByPo(supplierDao.selectByPrimaryKey(id));
	}

	@Override
	public void insert(CargoSupplierDo cargoSupplierDo) {
		supplierDao.insert(getPoByDomain(cargoSupplierDo));
	}

	@Override
	public void update(CargoSupplierDo cargoSupplierDo) {
		supplierDao.updateByPrimaryKey(getPoByDomain(cargoSupplierDo));
	}

	@Override
	public Long queryCargoSupplierCountPage(Page<Map<String, Object>> page) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (page.getConditons() != null) {
			map.put("conditions", page.getConditons().get("conditions").toString());
		}
		return supplierDao.queryCargoSupplierCountPage(map);
	}

	@Override
	public List<Map<String, Object>> queryCargoSupplierPage(Page<Map<String, Object>> page) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (page.getConditons() != null) {
			map.put("conditions", page.getConditons().get("conditions").toString());
		}
		map.put("start", page.getStart());
		map.put("limit", page.getLimit());

		return supplierDao.queryCargoSupplierPage(map);
	}

	@Override
	public List<CargoSupplierVo> findListAll() {
		return supplierDao.findListAll();
	}

	@Override
	public void deleteByPrimaryKey(long id) {
		supplierDao.deleteByPrimaryKey(id);
	}

	@Override
	public List<CargoSupplierVo> queryCargoSupplierByName(String name) {
		return supplierDao.queryCargoSupplierByName(name);
	}

	@Override
	public CargoSupplierVo getCargoSupplierVoById(Long id) {
		return getVoByPo(supplierDao.selectByPrimaryKey(id));
	}

}
