package com.club.web.common.service;

import java.util.List;
import java.util.Map;

import com.club.core.common.Page;
import com.club.core.common.TreeNode;
import com.club.core.common.TreePO;
import com.club.core.common.TreeQueryPO;
import com.club.framework.exception.BaseAppException;
import com.club.web.common.vo.DBMeta;


public interface IBaseService {
	/**
	 * 查询单条记录
	 * tableColumnKey:  XXXX.xx=1,XXXX.yy=2,OOO.xx=11
	 * @param tableColumnKey
	 * @return
	 * @throws BaseAppException
	 */
	Map<String,Object> selectOne(String tableColumnKey) throws BaseAppException;

	Map<String, Object> selectOneBySql(String sql) throws BaseAppException;

	Map<String,Object> selectOne(String tableName, Map<String, Object> conditions) throws BaseAppException;

	Map<String, Object> selectOne( Map<String, Object> conditions,String sql) throws BaseAppException;

	Map<String, Object> selectHumOne( Map<String, Object> conditions,String sql) throws BaseAppException;

	<T> T selectOne(T record) throws BaseAppException;

	<T> int delete(T record) throws BaseAppException;

	void delete(String tableColumnKey) throws BaseAppException;

	List<Map<String,Object>> selectList(String tableName, Map<String, Object> conditions) throws BaseAppException;

	List<Map<String,Object>> selectList(Map<String,Object> conditions,String sql) throws BaseAppException;

	List<Map<String, Object>> selectList(String sql) throws BaseAppException;

	Page<Map<String,Object>> selectPage(String tableName,Map<String,Object> conditions) throws BaseAppException;

	Page<Map<String,Object>> selectPage(Map<String,Object> conditions, boolean isHump) throws BaseAppException;

    List<Map<String,Object>> selectList2(String sqlKey, Map<String, Object> paramMap);
    
	boolean testDBConnection(String driver, String dbName, String characterEncoding, String ip, String port, String params, String username, String password) throws BaseAppException;
	
	DBMeta loadDBMeta(String driver, String dbName, String characterEncoding, String ip, String port, String params, String username, String password);

	boolean saveDBMeta(String driver, String url, String username, String password) throws BaseAppException;

	boolean saveDBMeta(String driver, String dbName, String characterEncoding, String ip, String port, String params, String username, String password) throws BaseAppException;

	TreeNode getTreeAllData(TreePO params, Map<String, Object> root, int path);
	
	TreeNode queryTree(TreeQueryPO reqDto);

	<T> T insert(T record) throws BaseAppException;

	Map<String, Object> insert(String tableName, Map<String, Object> record) throws BaseAppException;

	String saveOrUpdate(Map<String,Object> record) throws  BaseAppException;

	String save(Map<String,Object> record) throws  BaseAppException;

	<T> int update(T record) throws BaseAppException;

	int update(String tableName,Map<String,Object> record) throws BaseAppException;

	String testRun(Map<String,Object> record) throws  BaseAppException;
}
