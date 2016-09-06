package com.club.web.store.dao.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.store.dao.base.po.CarriageRuleDetail;
import com.club.web.store.dao.extend.CarriageRuleDetailExtendMapper;
import com.club.web.store.domain.CarriageRuleDetailDo;
import com.club.web.store.domain.repository.CarriageRuleDetailRepository;
import com.club.web.store.vo.CarriageRuleDetailVo;

@Repository
public class CarriageRuleDetailRepositoryImpl implements CarriageRuleDetailRepository {

	@Autowired
	CarriageRuleDetailExtendMapper carriageRuleDetailDao;
	
	@Override
	public int insertCarriageRuleDetail(CarriageRuleDetailDo carriageRuleDetailDo) {
		CarriageRuleDetail carriageRuleDetail = new CarriageRuleDetail();
		BeanUtils.copyProperties(carriageRuleDetailDo, carriageRuleDetail);

		return carriageRuleDetailDao.insert(carriageRuleDetail);
	}

	@Override
	public int updateCarriageRuleDetail(CarriageRuleDetailDo carriageRuleDetailDo) {
		CarriageRuleDetail carriageRuleDetail = new CarriageRuleDetail();
		BeanUtils.copyProperties(carriageRuleDetailDo, carriageRuleDetail);

		return carriageRuleDetailDao.updateByPrimaryKeySelective(carriageRuleDetail);
	}

	@Override
	public int deleteCarriageRuleDetail(Long id) {
		
		return carriageRuleDetailDao.deleteByPrimaryKey(id);
	}
	
	@Override
	public int deleteCarriageRuleDetailByCarriageRuleId(Long carriageRuleId){
		
		return carriageRuleDetailDao.deleteCarriageRuleDetailByCarriageRuleId(carriageRuleId);
	}
	
	@Override
	public List<CarriageRuleDetailVo> selectCarriageRuleDetailListByCarriageRuleId(Long carriageRuleId){
		
		return carriageRuleDetailDao.selectCarriageRuleDetailListByCarriageRuleId(carriageRuleId);
	}
	
	@Override
	public BigDecimal getCarriageByRegionId(Map<String, Object> carriageRuleId){
		
		return carriageRuleDetailDao.getCarriageByRegionId(carriageRuleId);
	}
	
	@Override
	public CarriageRuleDetailDo voChangeDo(CarriageRuleDetailVo carriageRuleDetailVo){
		CarriageRuleDetailDo carriageRuleDetailDo = new CarriageRuleDetailDo();
		BeanUtils.copyProperties(carriageRuleDetailVo, carriageRuleDetailDo);
		if(carriageRuleDetailVo.getId() != null && !carriageRuleDetailVo.getId().isEmpty()){
			carriageRuleDetailDo.setId(Long.valueOf(carriageRuleDetailVo.getId()));			
		}
		if(carriageRuleDetailVo.getCarriageRuleId() != null && !carriageRuleDetailVo.getCarriageRuleId().isEmpty()){
			carriageRuleDetailDo.setCarriageRuleId(Long.valueOf(carriageRuleDetailVo.getCarriageRuleId()));			
		}
		
		return carriageRuleDetailDo;
	}
}