package com.club.web.stock.dao.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.core.common.Page;
import com.club.framework.util.BeanUtils;
import com.club.web.stock.dao.base.po.CargoBrand;
import com.club.web.stock.dao.extend.CargoBrandExtendMapper;
import com.club.web.stock.domain.CargoBrand2Do;
import com.club.web.stock.domain.CargoBrandDo;
import com.club.web.stock.domain.repository.CargoBrandRepository;
import com.club.web.stock.vo.CargoBrandVo;

/**
 * @Title: CargoBrandRepositoryImpl.java
 * @Package com.club.web.stock.dao.repository
 * @Description: 品牌RepositoryImpl
 * @author 柳伟军
 * @date 2016年3月26日 上午9:30:56
 * @version V1.0
 */
@Repository
public class CargoBrandRepositoryImpl implements CargoBrandRepository {

	@Autowired
	private CargoBrandExtendMapper brandDao;

	@Override
	public CargoBrandDo create(CargoBrandVo cargoBrandVo) {
		if (cargoBrandVo == null)
			return null;
		CargoBrand2Do cargoBrandDo = new CargoBrand2Do();
		BeanUtils.copyProperties(cargoBrandVo, cargoBrandDo);
		cargoBrandDo.setId(Long.parseLong(cargoBrandVo.getId()));
		return cargoBrandDo;
	}

	private CargoBrandDo getDomainByPo(CargoBrand cargoBrand) {
		if (cargoBrand == null)
			return null;
		CargoBrand2Do cargoBrandDo = new CargoBrand2Do();
		BeanUtils.copyProperties(cargoBrand, cargoBrandDo);
		return cargoBrandDo;
	}

	private CargoBrand getPoByDomain(CargoBrandDo cargoBrandDo) {
		if (cargoBrandDo == null)
			return null;
		CargoBrand cargoBrand = new CargoBrand();
		BeanUtils.copyProperties(cargoBrandDo, cargoBrand);
		return cargoBrand;
	}

	private CargoBrandVo getVoByPo(CargoBrand cargoBrand) {
		if (cargoBrand == null)
			return null;
		CargoBrandVo cargoBrandVo = new CargoBrandVo();
		BeanUtils.copyProperties(cargoBrand, cargoBrandVo);
		cargoBrandVo.setId(cargoBrand.getId() + "");
		return cargoBrandVo;
	}

	@Override
	public CargoBrandDo getCargoBrandDoById(Long id) {
		return getDomainByPo(brandDao.selectByPrimaryKey(id));
	}

	@Override
	public void insert(CargoBrandDo cargoBrandDo) {
		brandDao.insert(getPoByDomain(cargoBrandDo));
	}

	@Override
	public void update(CargoBrandDo cargoBrandDo) {
		brandDao.updateByPrimaryKey(getPoByDomain(cargoBrandDo));
	}

	@Override
	public Long queryCargoBrandCountPage(Page<Map<String, Object>> page) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (page.getConditons() != null) {
			map.put("conditions", page.getConditons().get("conditions").toString());
		}
		return brandDao.queryCargoBrandCountPage(map);
	}

	@Override
	public List<Map<String, Object>> queryCargoBrandPage(Page<Map<String, Object>> page) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (page.getConditons() != null) {
			map.put("conditions", page.getConditons().get("conditions").toString());
		}
		map.put("start", page.getStart());
		map.put("limit", page.getLimit());

		return brandDao.queryCargoBrandPage(map);
	}

	@Override
	public List<CargoBrandVo> findListAll() {
		return brandDao.findListAll();
	}

	@Override
	public void deleteByPrimaryKey(long id) {
		brandDao.deleteByPrimaryKey(id);
	}

	@Override
	public List<CargoBrandVo> queryCargoBrandByName(String name) {
		return brandDao.queryCargoBrandByName(name);
	}

	@Override
	public CargoBrandVo getCargoBrandVoById(Long id) {
		return getVoByPo(brandDao.selectByPrimaryKey(id));
	}

}
