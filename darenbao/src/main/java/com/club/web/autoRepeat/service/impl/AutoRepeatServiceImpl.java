package com.club.web.autoRepeat.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.framework.util.BeanUtils;
import com.club.web.autoRepeat.dao.base.po.Defaultrepeat;
import com.club.web.autoRepeat.dao.extend.AutoRepeatExtendMapper;
import com.club.web.autoRepeat.dao.extend.DefaultRepeatExtendMapper;
import com.club.web.autoRepeat.domain.AutorepeatDo;
import com.club.web.autoRepeat.domain.DefaultrepeatDo;
import com.club.web.autoRepeat.domain.repository.AutoRepeatRepository;
import com.club.web.autoRepeat.service.AutoRepeatService;
import com.club.web.autoRepeat.vo.AutorepeatVo;
import com.club.web.autoRepeat.vo.DefaultrepeatVo;
import com.club.web.image.service.vo.ImageVo;
import com.club.web.spread.domain.MarketSpreadManagerDo;
import com.club.web.spread.vo.SpreadVo;
import com.club.web.util.CommonUtil;
/**
 * 用来获取所有自动回复模板
 * @author asus
 *
 */
@Transactional
@Service
public class AutoRepeatServiceImpl implements AutoRepeatService {
	
private Logger logger = LoggerFactory.getLogger(AutoRepeatServiceImpl.class);
	
	@Autowired
	private  AutoRepeatRepository AutoRepeatRepository;
	@Autowired
	private  AutoRepeatExtendMapper autoRepeatExtendMapper;
	@Autowired
	private  DefaultRepeatExtendMapper defaultRepeatExtendMapper;
	
	
	public Page<Map<String, Object>> selectAutoRepeat(Page<Map<String, Object>> page){
		Page<Map<String, Object>> result = new Page<Map<String, Object>>();
		result.setStart(page.getStart());
		result.setLimit(page.getLimit());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", page.getStart());
		map.put("limit", page.getLimit());
		if(page.getConditons().get("conditions")!=null && !page.getConditons().get("conditions").equals("")){
			map.put("conditions", page.getConditons().get("conditions").toString());
		}
		try {
			List<AutorepeatVo> list = AutoRepeatRepository.queryAutoRepeatPage(map);
			result.setResultList(CommonUtil.listObjTransToListMap(list));			
		} catch (Exception e) {
			logger.error("查询所有自动回复异常:", e);
			throw e;
		}
		try {
			Long count = AutoRepeatRepository.queryAutoRepeatCountPage(map);
			result.setTotalRecords(count.intValue());
		} catch (Exception e) {
			logger.error("查询自动回复总数量:", e);
			throw e;
		}		
		return result;
	}
	
	@Override
	public void addAutoRepeat(AutorepeatVo autorepeatVo) {
		// TODO Auto-generated method stub
		AutorepeatDo autorepeatDo = new AutorepeatDo();
		BeanUtils.copyProperties(autorepeatVo, autorepeatDo);
		autorepeatDo.setId(Long.parseLong(autorepeatVo.getId()));
		autorepeatDo.setUpdateTime(new Date());
		AutoRepeatRepository.addAutoRepeat(autorepeatDo);
	}
	
	@Override
	public void editAutoRepeat(AutorepeatVo autorepeatVo) {
		// TODO Auto-generated method stub
		AutorepeatDo autorepeatDo = new AutorepeatDo();
		BeanUtils.copyProperties(autorepeatVo, autorepeatDo);
		autorepeatDo.setId(Long.parseLong(autorepeatVo.getId()));
		autorepeatDo.setUpdateTime(new Date());
		AutoRepeatRepository.updateAutoRepeat(autorepeatDo);
	}

	@Override
	public void deleteAutoRepeat(String idStr) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		String[] ids = idStr.split(",");
		for (String id : ids) {
			AutoRepeatRepository.deleteById(Long.parseLong(id));
		}
		
	}
	
	@Override
	public String selectRepeat(String phases,Long id) {
		// TODO Auto-generated method stub
		String content="";
		//count 用来判断是否已经查找到完全相同的关键字
		int count=0;
		int label=0;
		Map<String, Object> map = new HashMap<String, Object>();
		List<AutorepeatVo> keywordList=autoRepeatExtendMapper.selectAllKeyword();
		for (AutorepeatVo autorepeatVo : keywordList) {
			if(phases.equals(autorepeatVo.getKeyword())){
				   content=autorepeatVo.getContent();
				   count=1;
				   break;
			  }
			}
		   if(count !=1){
			for (AutorepeatVo autorepeatVo : keywordList) {
				if(phases.contains(autorepeatVo.getKeyword())){
					   content=autorepeatVo.getContent();
					   break;
				  }
				else{
					   label++;
				}
			}
			if(keywordList!=null && label==keywordList.size()){
				Map<String, Object> map1 = new HashMap<String, Object>();
				content=defaultRepeatExtendMapper.queryDefaultRepeatPage(map1).get(0).getContent();
			}
		}
	
		return content;
		
	}

	@Override
	public void editDefaultRepeat(DefaultrepeatVo defaultrepeatVo) {
		// TODO Auto-generated method stub
		DefaultrepeatDo defaultrepeatDo = new DefaultrepeatDo();
		BeanUtils.copyProperties(defaultrepeatVo, defaultrepeatDo);
		defaultrepeatDo.setId(Long.parseLong(defaultrepeatVo.getId()));
		defaultrepeatDo.setUpdateTime(new Date());
		AutoRepeatRepository.updateDefaultRepeat(defaultrepeatDo);
		
	}

	@Override
	public Page<Map<String, Object>> selectDefaultRepeat(Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		Page<Map<String, Object>> result = new Page<Map<String, Object>>();
		result.setStart(page.getStart());
		result.setLimit(page.getLimit());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", page.getStart());
		map.put("limit", page.getLimit());
		try {
			List<DefaultrepeatVo> list = AutoRepeatRepository.queryDefaultRepeatPage(map);
			result.setResultList(CommonUtil.listObjTransToListMap(list));			
		} catch (Exception e) {
			logger.error("查询默认自动回复异常:", e);
			throw e;
		}
		try {
			Long count = AutoRepeatRepository.queryDefaultRepeatCountPage(map);
			result.setTotalRecords(count.intValue());
		} catch (Exception e) {
			logger.error("查询默认自动回复总数量:", e);
			throw e;
		}		
		return result;
	}
	
}
