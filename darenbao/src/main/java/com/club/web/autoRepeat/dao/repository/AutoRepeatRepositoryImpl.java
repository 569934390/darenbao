package com.club.web.autoRepeat.dao.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.autoRepeat.dao.base.po.Autorepeat;
import com.club.web.autoRepeat.dao.base.po.Defaultrepeat;
import com.club.web.autoRepeat.dao.extend.AutoRepeatExtendMapper;
import com.club.web.autoRepeat.dao.extend.DefaultRepeatExtendMapper;
import com.club.web.autoRepeat.domain.AutorepeatDo;
import com.club.web.autoRepeat.domain.DefaultrepeatDo;
import com.club.web.autoRepeat.domain.repository.AutoRepeatRepository;
import com.club.web.autoRepeat.vo.AutorepeatVo;
import com.club.web.autoRepeat.vo.DefaultrepeatVo;
import com.club.web.spread.dao.base.po.MarketSpreadManager;
import com.club.web.spread.domain.MarketSpreadManagerDo;
@Repository
public class AutoRepeatRepositoryImpl implements  AutoRepeatRepository {
 
	@Autowired
	AutoRepeatExtendMapper autoRepeatExtendMapper;
	
	@Autowired
	DefaultRepeatExtendMapper defaultRepeatExtendMapper;
	
	@Override
	public List<AutorepeatVo> queryAutoRepeatPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return autoRepeatExtendMapper.queryAutoRepeatPage(map);
	}
	
	public Long queryAutoRepeatCountPage(Map<String, Object> map){
		return autoRepeatExtendMapper.queryAutoRepeatCountPage(map);
	}
	
	@Override
	public void addAutoRepeat(AutorepeatDo autorepeatDo) {
		// TODO Auto-generated method stub
		Autorepeat autorepeat = new Autorepeat();
		BeanUtils.copyProperties(autorepeatDo,autorepeat);
		autoRepeatExtendMapper.insert(autorepeat);
	}
	
	@Override
	public void updateAutoRepeat(AutorepeatDo autorepeatDo) {
		// TODO Auto-generated method stub
		Autorepeat autorepeat = new Autorepeat();
		BeanUtils.copyProperties(autorepeatDo,autorepeat);
		autoRepeatExtendMapper.updateByPrimaryKey(autorepeat);
	}
	
	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		autoRepeatExtendMapper.deleteByPrimaryKey(id);
	}

	@Override
	public AutorepeatVo selectRepeatBykeyword(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return autoRepeatExtendMapper.selectRepeatBykeyword(map);
	}
	
	
	@Override
	public List<DefaultrepeatVo> queryDefaultRepeatPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return defaultRepeatExtendMapper.queryDefaultRepeatPage(map);
	}
	
	@Override
	public Long queryDefaultRepeatCountPage(Map<String, Object> map){
		return defaultRepeatExtendMapper.queryDefaultRepeatCountPage(map);
	}
	
	@Override
	public void updateDefaultRepeat(DefaultrepeatDo defaultrepeatDo) {
		// TODO Auto-generated method stub
		Defaultrepeat defaultrepeat = new Defaultrepeat();
		BeanUtils.copyProperties(defaultrepeatDo,defaultrepeat);
		defaultRepeatExtendMapper.updateByPrimaryKey(defaultrepeat);
	}

}
