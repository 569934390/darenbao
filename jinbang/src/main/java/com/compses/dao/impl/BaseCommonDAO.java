package com.compses.dao.impl;

import java.util.*;

import com.compses.model.DevSqlCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.compses.dao.IBaseCommonDAO;
import com.compses.dao.ITreeCommonDAO;
import com.compses.dto.Page;
import com.compses.util.DBUtils;
import com.compses.util.JsonUtils;
import com.compses.util.StringUtils;

@SuppressWarnings("unchecked")
public class BaseCommonDAO implements ITreeCommonDAO,IBaseCommonDAO {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public List<Map<String,Object>> loadTreeData(Map<String,Object> paramMap){
		return this.loadData(paramMap);
	}
	public List<Map<String,Object>> loadData(Map<String,Object> paramMap){
		if(!"".equals(paramMap.get("paramMap"))&&paramMap.get("paramMap")!=null){
			Map<String, Object> map = JsonUtils.toMap(paramMap.get("paramMap").toString());
			if(map!=null&&map.size()>0&&!"".equals(map.get("listField"))&&map.get("listField")!=null){
				String listField = map.get("listField").toString();
				String[] listArr = listField.split(",");
				for (int listNum = 0; listNum < listArr.length; listNum++) {
					String fieldStr = map.get(listArr[listNum]).toString();
					String[] fieldArr = fieldStr.split(",");
					List<String> fieldList = new ArrayList<String>();
					for (int fNum = 0; fNum < fieldArr.length; fNum++) {
						fieldList.add(fieldArr[fNum]);
					}
					map.put(listArr[listNum], fieldList);
				}
				paramMap.remove("paramMap");
				paramMap.putAll(map);
			}
		}
		String[] strArr=paramMap.get("sqlKey").toString().split("\\.");
		String sql=DBUtils.getSql(strArr[0], strArr[1]);
		List<Map<String,Object>> topoList = DBUtils.query(sql,paramMap,namedParameterJdbcTemplate);
		
		return topoList;
	}
	
	public Page<?> loadPage(Page<?> page) throws Exception {
		String sql;
		if (page.getType().equals("DB")){
			sql = DBUtils.getSql(page.getSqlKey().toString(),namedParameterJdbcTemplate);
		}
		else {
			String[] strArr=page.getSqlKey().toString().split("\\.");
			sql = DBUtils.getSql(strArr[0], strArr[1]);
		}

		page = DBUtils.queryPage(sql, page, namedParameterJdbcTemplate);
		return page;
	}
	
	public Page<?> loadPages(Page<?> page){
		String sql = page.getSqlKey();
		page = DBUtils.queryPage(sql, page, namedParameterJdbcTemplate);
		return page;
	}
	
	public Long save(String tableName,Map<String,Object> record) throws Exception {
		String sql;
		if (tableName.indexOf("DB")!=-1){
			sql = DBUtils.getSql(tableName.substring(0,tableName.indexOf(".")+1)+DBUtils.SAVE,namedParameterJdbcTemplate);
		}
		else {
			sql = DBUtils.getSql(StringUtils.covertHump(tableName),DBUtils.SAVE);
		}
		return DBUtils.insert(sql, namedParameterJdbcTemplate,record);
	}

	public String saveForUUID(String tableName,Map<String,Object> record) throws Exception {
		String sql;
		if (tableName.indexOf("DB")!=-1){
			sql = DBUtils.getSql(tableName.substring(0,tableName.indexOf(".")+1)+DBUtils.SAVE,namedParameterJdbcTemplate);
		}
		else {
			sql = DBUtils.getSql(StringUtils.covertHump(tableName),DBUtils.SAVE);
		}
		return DBUtils.insertForUUID(sql, namedParameterJdbcTemplate,record);
	}
	@Override
	public int update(String tableName, Map<String, Object> record) throws Exception {
		String sql;
		if (tableName.indexOf("DB")!=-1){
			sql = DBUtils.getSql(tableName.substring(0,tableName.indexOf(".")+1)+DBUtils.UPDATE,namedParameterJdbcTemplate);
		}
		else {
			sql = DBUtils.getSql(StringUtils.covertHump(tableName),DBUtils.UPDATE);
		}
		return DBUtils.update(sql, namedParameterJdbcTemplate,record);
	}
	@Override
	public int delete(String tableName, Map<String, Object> record) throws Exception {
		String sql;
		if (tableName.indexOf("DB")!=-1){
			sql = DBUtils.getSql(tableName.substring(0,tableName.indexOf(".")+1)+DBUtils.DELETE,namedParameterJdbcTemplate);
		}
		else {
			sql = DBUtils.getSql(StringUtils.covertHump(tableName),DBUtils.DELETE);
		}
		return DBUtils.update(sql, namedParameterJdbcTemplate,record);
	}
	@Override
	public Map<String, Object> selectOne(String tableName,
			Map<String, Object> record) throws Exception {
		String sql;
		if (tableName.indexOf("DB")!=-1){
			sql = DBUtils.getSql(tableName.substring(0,tableName.indexOf(".")+1)+DBUtils.SELECT_ONE,namedParameterJdbcTemplate);
		}
		else {
			sql = DBUtils.getSql(StringUtils.covertHump(tableName),DBUtils.SELECT_ONE);
		}
		return DBUtils.queryForObject(sql, record, namedParameterJdbcTemplate, Map.class);
	}
	@Override
	public <T> int update(T record) {
		String className = record.getClass().getSimpleName();
		String sql=DBUtils.getSql(className,DBUtils.UPDATE);
		return DBUtils.update(sql, namedParameterJdbcTemplate,record);
	}
	@Override
	public <T> int delete(T record) {
		String className = record.getClass().getSimpleName();
		String sql=DBUtils.getSql(className,DBUtils.DELETE);
		return DBUtils.update(sql, namedParameterJdbcTemplate,record);
	}
	@Override
	public <T> T selectOne(T record) {
		String className = record.getClass().getSimpleName();
		String sql=DBUtils.getSql(className,DBUtils.SELECT_ONE);
		return (T) DBUtils.queryForObject(sql, record, namedParameterJdbcTemplate, record.getClass());
	}
	@Override
	public <T> Long save(T record) {
		String className = record.getClass().getSimpleName();
		String sql=DBUtils.getSql(className,DBUtils.SAVE);
		return DBUtils.insert(sql, namedParameterJdbcTemplate,record);
	}

	public <T> String saveForUUid(T record) {
		String className = record.getClass().getSimpleName();
		String sql=DBUtils.getSql(className,DBUtils.SAVE);
		return DBUtils.insertForUUID(sql, namedParameterJdbcTemplate,record);
	}
	public <T> List<T> loadData(T record){
		String className = record.getClass().getSimpleName();
		String sql=DBUtils.getSql(className,DBUtils.SELECT_LIST);
		List<T> topoList = (List<T>) DBUtils.query(sql, record, namedParameterJdbcTemplate, record.getClass());
		return topoList;
	}
	
	public <T> List<T> loadData(String fileName,String sqlKey,T record){
		String sql=DBUtils.getSql(fileName,sqlKey);
		List<T> topoList = (List<T>) DBUtils.query(sql, record, namedParameterJdbcTemplate, record.getClass());
		return topoList;
	}

	public void saveSqlCache(String sqlKey,String contextStr){
		DevSqlCache devSqlCache = new DevSqlCache();
		devSqlCache.setSqlKey(sqlKey);

		DevSqlCache _devSqlCache = this.selectOne(devSqlCache);
		if (_devSqlCache!=null){
			_devSqlCache.setModifyTime(new Date());
			_devSqlCache.setState("00A");
			_devSqlCache.setContext(contextStr);
			_devSqlCache.setType("user");
			this.update(_devSqlCache);
		}
		else {
			devSqlCache.setModifyTime(new Date());
			devSqlCache.setState("00A");
			devSqlCache.setContext(contextStr);
			devSqlCache.setType("user");
			devSqlCache.setCreateDate(new Date());
			this.save(devSqlCache);
		}

	}
}
