package com.compses.dao;

import java.util.List;
import java.util.Map;

import com.compses.dto.Page;

public interface IBaseCommonDAO {

	List<Map<String,Object>> loadData(Map<String,Object> paramMap);
	Page<?> loadPage(Page<?> page) throws Exception;
	Page<?> loadPages(Page<?> page);
	Long save(String tableName,Map<String,Object> record) throws Exception;
	String saveForUUID(String tableName,Map<String,Object> record) throws Exception;
	int update(String tableName,Map<String,Object> record) throws Exception;
	int delete(String tableName,Map<String,Object> record) throws Exception;
	Map<String,Object> selectOne(String tableName,Map<String,Object> record) throws Exception;
	<T> Long save(T record);
	<T> String saveForUUid(T record);
	<T> int update(T record);
	<T> int delete(T  record);
	<T> T selectOne(T  record);
	<T> List<T> loadData(T record);
	<T> List<T> loadData(String fileName,String sqlKey,T record);

	void saveSqlCache(String sqlKey,String contextStr);
}
