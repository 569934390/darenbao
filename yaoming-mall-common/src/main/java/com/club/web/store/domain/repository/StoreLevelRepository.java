package com.club.web.store.domain.repository;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.club.core.common.Page;
import com.club.web.store.domain.StoreLevelDo;
import com.club.web.store.vo.StoreLevelVo;

public interface StoreLevelRepository {

    void insert(StoreLevelDo storeLevelDo);
	
	void update(StoreLevelDo storeLevelDo);

	StoreLevelDo create(StoreLevelVo storeLevelVo);

	StoreLevelDo getStoreLevelDoByLevelId(long parseLong);

	List<StoreLevelVo> queryStoreLevelByName(String name);

	List<Map<String,Object>> queryStoreLevelPage(Page<Map<String, Object>> page, HttpServletRequest request);

	Long queryStoreLevelCountPage(Page<Map<String, Object>> page, HttpServletRequest request);

	void deleteByPrimaryKey(long levelId);

	StoreLevelDo getStoreLevelDoById(long levelId);

	List<StoreLevelVo> findAllStoreLevel(HttpServletRequest request);

}
