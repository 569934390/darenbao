package com.club.web.spread.dao.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.core.common.Page;
import com.club.framework.util.BeanUtils;
import com.club.web.spread.constant.SpreadContentTypeEnum;
import com.club.web.spread.dao.base.MarketSpreadManagerMapper;
import com.club.web.spread.dao.base.po.MarketSpreadManager;
import com.club.web.spread.dao.extend.MarketSpreadManagerExtendMapper;
import com.club.web.spread.domain.MarketSpreadManagerDo;
import com.club.web.spread.domain.repository.SpreadManagerRepository;
import com.club.web.spread.vo.SpreadVo;

@Repository
public class SpreadManagerRepositoryImpl implements SpreadManagerRepository{
	
	@Autowired
	MarketSpreadManagerExtendMapper marketSpreadManagerExtendMapper;

	@Override
	public List<SpreadVo> querySpreadPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<SpreadVo> list=marketSpreadManagerExtendMapper.querySpreadPage(map);
		for (SpreadVo spreadVo : list) {
			spreadVo.setSpreadContentTypeName(SpreadContentTypeEnum.getTextByDbData(spreadVo.getSpreadContentType()));
		}
		return list;
	}

	@Override
	public Long querySpreadCountPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return marketSpreadManagerExtendMapper.querySpreadCountPage(map);
	}

	@Override
	public void addSpread(MarketSpreadManagerDo marketSpreadManagerDo) {
		// TODO Auto-generated method stub
		MarketSpreadManager marketSpreadManager = new MarketSpreadManager();
		BeanUtils.copyProperties(marketSpreadManagerDo,marketSpreadManager);
		marketSpreadManagerExtendMapper.insert(marketSpreadManager);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		marketSpreadManagerExtendMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(MarketSpreadManagerDo marketSpreadManagerDo) {
		// TODO Auto-generated method stub
		MarketSpreadManager marketSpreadManager = new MarketSpreadManager();
		BeanUtils.copyProperties(marketSpreadManagerDo,marketSpreadManager);
		marketSpreadManagerExtendMapper.updateByPrimaryKeyWithBLOBs(marketSpreadManager);
	}
	@Override
	public List<SpreadVo> querySpreadList(Map<String, Object> map){
		return marketSpreadManagerExtendMapper.querySpreadList(map);
	}

}
