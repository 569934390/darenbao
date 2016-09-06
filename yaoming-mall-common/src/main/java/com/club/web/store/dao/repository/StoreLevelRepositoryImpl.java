package com.club.web.store.dao.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.core.common.Page;
import com.club.framework.util.BeanUtils;
import com.club.web.store.dao.base.po.StoreLevel;
import com.club.web.store.dao.extend.StoreLevelExtendMapper;
import com.club.web.store.domain.StoreLevel2Do;
import com.club.web.store.domain.StoreLevelDo;
import com.club.web.store.domain.repository.StoreLevelRepository;
import com.club.web.store.vo.StoreLevelVo;

/**
* @Title: StoreLevelRepositoryImpl.java 
* @Package com.club.web.store.dao.repository 
* @Description: 店铺等级StoreLevelRepository
* @author 柳伟军   
* @date 2016年3月26日 上午10:02:25 
* @version V1.0
 */
@Repository
public class StoreLevelRepositoryImpl implements StoreLevelRepository{

	@Autowired
	private StoreLevelExtendMapper storeLevelExtendMapper;
	
	@Override
	public StoreLevelDo create(StoreLevelVo storeLevelVo) {
		if(storeLevelVo==null)
		   return null;
		StoreLevel2Do storeLevelDo=new StoreLevel2Do();
		BeanUtils.copyProperties(storeLevelVo, storeLevelDo);
		storeLevelDo.setLevelId(Long.parseLong(storeLevelVo.getLevelId()));
		return storeLevelDo;
	}
	
	private StoreLevel getPoByDomian(StoreLevelDo storeLevelDo){
		if(storeLevelDo==null)
			return null;
		StoreLevel storeLevel=new StoreLevel();
		BeanUtils.copyProperties(storeLevelDo, storeLevel);
		return storeLevel;
	}
	
	private StoreLevelDo getDomainByPo(StoreLevel storeLevel) {
		if(storeLevel==null)
			return null;
		StoreLevel2Do storeLevelDo=new StoreLevel2Do();
		BeanUtils.copyProperties(storeLevel, storeLevelDo);
		return storeLevelDo;
	}

	/**
	 * 新增
	 */
	public void insert(StoreLevelDo storeLevelDo) {
		storeLevelExtendMapper.insert(getPoByDomian(storeLevelDo));
	}

	/**
	 * 修改
	 */
	public void update(StoreLevelDo storeLevelDo) {
		storeLevelExtendMapper.updateByPrimaryKeySelective(getPoByDomian(storeLevelDo));
	}

	/**
	 * 根据levelId查询等级信息
	 */
	public StoreLevelDo getStoreLevelDoByLevelId(long levelId) {
		return getDomainByPo(storeLevelExtendMapper.selectByPrimaryKey(levelId));
	}

	/**
	 * 根据名字查询等级信息
	 */
	public List<StoreLevelVo> queryStoreLevelByName(String name) {
		return storeLevelExtendMapper.queryStoreLevelByName(name);
	}

	/**
	 * 分页查询
	 */
	public List<Map<String,Object>> queryStoreLevelPage(Page<Map<String, Object>> page, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("start", page.getStart());
		map.put("limit", page.getLimit());
		
		return storeLevelExtendMapper.querystoreLevelPage(map);
	}

	/**
	 * 查询条数
	 */
	public Long queryStoreLevelCountPage(Page<Map<String, Object>> page, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		return storeLevelExtendMapper.querystoreLevelCountPage(map);
	}

	/**
	 * 根据ID删除
	 */
	public void deleteByPrimaryKey(long levelId) {
		storeLevelExtendMapper.deleteByPrimaryKey(levelId);
	}

	/**
	 * 根据Id查询
	 */
	public StoreLevelDo getStoreLevelDoById(long levelId) {
		return getDomainByPo(storeLevelExtendMapper.selectByPrimaryKey(levelId));
	}


	/**
	 * 获取当前用户绑定总店有效的店铺等级
	 * @return 
	 */
	public List<StoreLevelVo> findAllStoreLevel(HttpServletRequest request) {
		
		return storeLevelExtendMapper.findAllStoreLevel();
	}
	
}
