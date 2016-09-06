package com.club.web.store.dao.extend;

import java.util.List;
import java.util.Map;

import com.club.web.store.dao.base.StoreLevelMapper;
import com.club.web.store.dao.base.po.StoreLevel;
import com.club.web.store.vo.StoreLevelVo;

public interface StoreLevelExtendMapper extends StoreLevelMapper{

	List<StoreLevelVo> queryStoreLevelByName(String name);

	List<Map<String,Object>> querystoreLevelPage(Map<String, Object> map);

	Long querystoreLevelCountPage(Map<String, Object> map);

	List<StoreLevelVo> findAllStoreLevel();
   
}