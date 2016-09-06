package com.club.web.store.dao.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.store.dao.base.po.CarriageRule;
import com.club.web.store.dao.extend.CarriageRuleExtendMapper;
import com.club.web.store.domain.CarriageRuleDo;
import com.club.web.store.domain.repository.CarriageRuleRepository;
import com.club.web.store.vo.CarriageRuleVo;

@Repository
public class CarriageRuleRepositoryImpl implements CarriageRuleRepository {

	@Autowired
	CarriageRuleExtendMapper carriageRuleDao;
	
	@Override
	public int insertCarriageRule(CarriageRuleDo carriageRuleDo) {
		CarriageRule carriageRule = new CarriageRule();
		BeanUtils.copyProperties(carriageRuleDo, carriageRule);

		return carriageRuleDao.insert(carriageRule);
	}

	@Override
	public int updateCarriageRule(CarriageRuleDo carriageRuleDo) {
		CarriageRule carriageRule = new CarriageRule();
		BeanUtils.copyProperties(carriageRuleDo, carriageRule);

		return carriageRuleDao.updateByPrimaryKeySelective(carriageRule);
	}

	@Override
	public int deleteCarriageRule(Long id) {		
		
		return carriageRuleDao.deleteByPrimaryKey(id);
	}
	
	@Override
	public List<CarriageRuleVo> selectCarriageRule(Map<String, Object> map){
		
		return carriageRuleDao.selectCarriageRule(map);
	}
	
	@Override
	public Long selectCarriageRuleCountPage(Map<String, Object> map){
		
		return carriageRuleDao.selectGoodsColumnCountPage(map);
	}
	
	@Override
	public CarriageRuleVo selectCarriageRuleById(Long id){
		CarriageRuleVo carriageRuleVo = new CarriageRuleVo();
		CarriageRule carriageRule = carriageRuleDao.selectByPrimaryKey(id);
		if(carriageRule != null){
			BeanUtils.copyProperties(carriageRule, carriageRuleVo);
			carriageRuleVo.setId(carriageRule.getId()+"");
		} else{
			return null;
		}
		
		return carriageRuleVo;
	}
	
	@Override
	public CarriageRuleDo createCarriageRuleDo(){
		
		return new CarriageRuleDo();
	}
	
	@Override
	public CarriageRuleDo voChangeDo(CarriageRuleVo carriageRuleVo){
		CarriageRuleDo carriageRuleDo = new CarriageRuleDo();
		BeanUtils.copyProperties(carriageRuleVo, carriageRuleDo);
		if(carriageRuleVo.getId() != null && !carriageRuleVo.getId().isEmpty()){
			carriageRuleDo.setId(Long.valueOf(carriageRuleVo.getId()));			
		}
		
		return carriageRuleDo;
	}
	
	@Override
	public List<CarriageRuleVo> getCarriageRuleList(){
		
		return carriageRuleDao.getCarriageRuleList();
	}
	
	@Override
	public Long getGoodsColumnByCarriageRuleId(Long carriageRuleId){
		
		return carriageRuleDao.getGoodsColumnByCarriageRuleId(carriageRuleId);
	}
}