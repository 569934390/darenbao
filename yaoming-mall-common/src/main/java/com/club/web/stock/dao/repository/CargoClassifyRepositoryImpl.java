package com.club.web.stock.dao.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.ListUtils;
import com.club.web.stock.dao.base.po.CargoClassify;
import com.club.web.stock.dao.extend.CargoClassifyExtendMapper;
import com.club.web.stock.domain.CargoClassifyDo;
import com.club.web.stock.domain.repository.CargoClassifyRepository;

/**
 * 货品分类仓库接口实现类
 * 
 * @author zhuzd
 *
 */
@Repository
public class CargoClassifyRepositoryImpl implements CargoClassifyRepository {

	@Autowired
	private CargoClassifyExtendMapper cargoClassifyDoDao;

	@Override
	public List<CargoClassifyDo> findDoListByIds(String ids) {
		return getDomainListByPoList(cargoClassifyDoDao.findListByIds(ListUtils.strToLongList(ids)));
	}

	@Override
	public CargoClassifyDo findDoById(Long id) {
		return getDomainByPo(cargoClassifyDoDao.findByIdAndStatus(id, null));
	}

	@Override
	public List<Long> getAllIdsByIdAndStatus(Long id, Integer status) {
		CargoClassifyDo cargoClassifyDo = getDomainByPo(cargoClassifyDoDao.findByIdAndStatus(id, null));
		return cargoClassifyDo != null ? cargoClassifyDo.getAllIds(status) : null;
	}

	@Override
	public CargoClassifyDo findDoByIdAndStatus(Long id, Integer status) {
		return getDomainByPo(cargoClassifyDoDao.findByIdAndStatus(id, status));
	}

	@Override
	public List<CargoClassifyDo> findDoByParentId(Long parentId) {
		return getDomainListByPoList(cargoClassifyDoDao.findByParentIdAndStatus(parentId, null));
	}

	@Override
	public List<CargoClassifyDo> findDoByParentIdAndStatus(Long parentId, Integer status) {
		return getDomainListByPoList(cargoClassifyDoDao.findByParentIdAndStatus(parentId, status));
	}

	@Override
	public int updateStatus(CargoClassifyDo cargoClassifyDo) {
		return cargoClassifyDoDao.updateByPrimaryKeySelective(getPoByDomain(cargoClassifyDo));
	}

	@Override
	public int delete(CargoClassifyDo cargoClassifyDo) {
		return cargoClassifyDoDao.deleteByPrimaryKey(cargoClassifyDo.getId());
	}

	@Override
	public int add(CargoClassifyDo cargoClassifyDo) {
		return cargoClassifyDoDao.insert(getPoByDomain(cargoClassifyDo));
	}

	@Override
	public int modify(CargoClassifyDo cargoClassifyDo) {
		return cargoClassifyDoDao.updateByPrimaryKeySelective(getPoByDomain(cargoClassifyDo));
	}

	@Override
	public List<CargoClassifyDo> findDoNoIds(List<Long> ids) {
		return getDomainListByPoList(cargoClassifyDoDao.findNoIds(ids));
	}

	@Override
	public List<CargoClassifyDo> findAllDoByStatus(Integer status) {
		return getDomainListByPoList(cargoClassifyDoDao.findAllPoByStatus(status));
	}

	// TODO 这个方法没用到, 可以先删掉或注释掉
	private List<CargoClassify> getPoListByDomainList(List<CargoClassifyDo> srcs) {
		List<CargoClassify> targets = new ArrayList<CargoClassify>();
		if (srcs != null && srcs.size() != 0) {
			for (CargoClassifyDo src : srcs) {
				targets.add(getPoByDomain(src));
			}
		}
		return targets;
	}

	private List<CargoClassifyDo> getDomainListByPoList(List<CargoClassify> srcs) {
		List<CargoClassifyDo> targets = new ArrayList<CargoClassifyDo>();
		if (srcs != null && srcs.size() != 0) {
			for (CargoClassify src : srcs) {
				targets.add(getDomainByPo(src));
			}
		}
		return targets;
	}

	private CargoClassify getPoByDomain(CargoClassifyDo src) {
		CargoClassify target = null;
		if (src != null) {
			target = new CargoClassify();
			target.setId(src.getId());
			target.setName(src.getName());
			target.setParentId(src.getParentId());
			target.setStatus(src.getStatus());
			target.setOrderIndex(src.getOrderIndex());
			target.setCreateBy(src.getCreateBy());
			target.setCreateTime(src.getCreateTime());
			target.setUpdateBy(src.getUpdateBy());
			target.setUpdateTime(src.getUpdateTime());
			target.setImgUrl(src.getImgUrl());
		}
		return target;
	}

	private CargoClassifyDo getDomainByPo(CargoClassify src) {
		CargoClassifyDo target = null;
		if (src != null) {
			target = new CargoClassifyDo();
			target.setId(src.getId());
			target.setName(src.getName());
			target.setParentId(src.getParentId());
			target.setStatus(src.getStatus());
			target.setOrderIndex(src.getOrderIndex());
			target.setCreateBy(src.getCreateBy());
			target.setCreateTime(src.getCreateTime());
			target.setUpdateBy(src.getUpdateBy());
			target.setUpdateTime(src.getUpdateTime());
			target.setImgUrl(src.getImgUrl());
		}
		return target;
	}

	@Override
	public String queryNameById(long id) {
		String name = cargoClassifyDoDao.queryNameById(id);
		return name;
	}
}