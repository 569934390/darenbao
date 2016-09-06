package com.club.web.spread.dao.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.spread.dao.base.po.MarketSpreadClassify;
import com.club.web.spread.dao.extend.MarketSpreadClassifyExtendMapper;
import com.club.web.spread.domain.MarketSpreadClassifyDo;
import com.club.web.spread.domain.repository.SpreadClassifyRepository;
import com.club.web.spread.vo.MarketSpreadClassifyVo;
import com.club.web.store.dao.extend.GoodsBaseLabelExtendMapper;
import com.club.web.store.vo.GoodsBaseLabelVo;
@Repository
public class SpreadClassifyRepositoryImpl implements SpreadClassifyRepository {
    
	
	@Autowired
	MarketSpreadClassifyExtendMapper spreadClassifyMapper;
	
	@Override
	public List<MarketSpreadClassifyVo> selectAllSpreadClassify(Map<String, Object> map){
		
		return spreadClassifyMapper.selectAllSpreadClassify(map);
	}
	@Override
	public Long querySpreadClassifyCountPage(){
		return  spreadClassifyMapper.querySpreadClassifyCountPage();
	}
	@Override
	public int addSpreadClassify(MarketSpreadClassifyDo marketSpreadClassifyDo) {
		// TODO Auto-generated method stub
		MarketSpreadClassify marketSpreadClassify= new MarketSpreadClassify();
		BeanUtils.copyProperties(marketSpreadClassifyDo, marketSpreadClassify);
		return spreadClassifyMapper.insert(marketSpreadClassify);
	}
	@Override
	public int updateSpreadClassify(MarketSpreadClassifyDo marketSpreadClassifyDo) {
		// TODO Auto-generated method stub
		MarketSpreadClassify marketSpreadClassify= new MarketSpreadClassify();
		BeanUtils.copyProperties(marketSpreadClassifyDo, marketSpreadClassify);
		return spreadClassifyMapper.updateByPrimaryKey(marketSpreadClassify);
	}
	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		spreadClassifyMapper.deleteByPrimaryKey(id);
	}
	@Override
	public int updateStatusById(Long id, int status) {
		// TODO Auto-generated method stub
		return spreadClassifyMapper.updateStatusById(id, status);
	}
	
	@Override
	public List<MarketSpreadClassifyVo> findAllSpreadClassify(){
		
		return spreadClassifyMapper.findAllSpreadClassify();
	}
	
	@Override
	public List<MarketSpreadClassifyVo> findAllSpreadClassifyOnStatus(){
		
		return spreadClassifyMapper.findAllSpreadClassifyOnStatus();
	}
	
}
