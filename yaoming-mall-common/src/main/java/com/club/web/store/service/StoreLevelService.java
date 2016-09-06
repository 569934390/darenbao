package com.club.web.store.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.club.core.common.Page;
import com.club.web.store.vo.StoreLevelVo;

public interface StoreLevelService {

	Map<String, Object> saveOrUpdateStoreLevel(StoreLevelVo storeLevelVo, HttpServletRequest request);

	Page<Map<String, Object>> queryStoreLevelPage(Page<Map<String, Object>> page, HttpServletRequest request);

	Map<String, Object> updateStoreLevelStatue(String idStr, Long statue, HttpServletRequest request);

	Map<String, Object> deleteStoreLevel(String idStr);

	List<StoreLevelVo> findAllStoreLevel(HttpServletRequest request);

}
