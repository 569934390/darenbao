package com.club.web.stock.service;

import java.util.List;
import java.util.Map;

import com.club.core.common.Page;
import com.club.web.stock.vo.ClassifyColumnVo;

public interface ClassifyColumnService {

	Page<Map<String, Object>> list(Page<Map<String, Object>> page) throws Exception;

	List<ClassifyColumnVo> findListByIds(String ids) throws Exception;
	
	Map<String,Object> saveOrUpdate(ClassifyColumnVo classifyColumn) throws Exception;
	
	Map<String,Object> delete(String ids) throws Exception;
	
	List<ClassifyColumnVo> list() throws Exception;
}
