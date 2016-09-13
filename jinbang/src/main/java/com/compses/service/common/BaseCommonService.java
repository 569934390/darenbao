package com.compses.service.common;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.compses.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compses.dao.IBaseCommonDAO;
import com.compses.dto.DBMeta;
import com.compses.dto.Page;
import com.compses.util.DBUtils;
import com.compses.util.StringUtils;

@Service
@Transactional
public class BaseCommonService {
	
	@Autowired
	private IBaseCommonDAO baseCommonDAO;
	
	public List<Map<String,Object>> loadData(Map<String,Object> paramMap){
		return baseCommonDAO.loadData(paramMap);
	}
	
	public Page<?> loadPage(Page<?> page) throws Exception {
		return baseCommonDAO.loadPage(page);
	}
	
	public Page<?> loadPages(Page<?> page){
		return baseCommonDAO.loadPages(page);
	}
	
	public <T> List<T> loadData(T record){
		return baseCommonDAO.loadData(record);
	}
	
	public void verifyRecord(Map<String,Object> record){
		for(String key : record.keySet()){
			if(!StringUtils.isEmptyForObject(record.get(key))&&record.get(key).equals("NULL")){
				record.put(key, null);
			}
			if(record.containsKey("modifyTime")){
				record.put("modifyTime", new Date());
			}
		}
	}
	
	public Long save(String tableName,Map<String,Object>record) throws Exception {
		verifyRecord(record);
		return baseCommonDAO.save(tableName, record);
	}

	public String saveForUUID(String tableName,Map<String,Object>record) throws Exception {
		verifyRecord(record);
		return baseCommonDAO.saveForUUID(tableName,record);
	}
	public int update(String tableName,Map<String,Object>record) throws Exception {
		verifyRecord(record);
		return baseCommonDAO.update(tableName, record);
	}
	public int delete(String tableName,String ids) throws Exception {
		Map<String,Object> record = new HashMap<String, Object>();
		for(String id : ids.split(",")){
			if (!id.trim().equals("")) {
				record.put("id", id);
				baseCommonDAO.delete(tableName, record);
			}
		}
		return 1;
	}
	public Map<String,Object> selectOne(String tableName,String id) throws Exception {
		Map<String,Object>record = new HashMap<String, Object>();
		record.put("id", id);
		return baseCommonDAO.selectOne(tableName, record);
	}
	@SuppressWarnings("unchecked")
	public int updateItems(String tableName,Map<String,Object>record) throws Exception {
		baseCommonDAO.delete(tableName, record);
		List<Map<String,Object>> list = (List<Map<String,Object>>)record.get("list");
		for(Map<String,Object> item : list){
			save(tableName, item);
		}
		return 1;
	}
	
	public boolean testDBConnection(String driver,String dbName,String characterEncoding,String ip,String port,String params,String username,String password){
		StringBuffer url = new StringBuffer("jdbc:");
		if(driver.toLowerCase().contains("mysql")){
			url.append("mysql://").append(ip).append(":").append(port).append("/").append(dbName).append("?characterEncoding=").append(characterEncoding)
			.append("&").append(params);
		}
		else{
			url.append("oracle:thin:@").append(ip).append(":").append(port).append(":").append(dbName);
		}
		
		return DBUtils.testDBConnection(driver, url.toString(), username, password);
	}
	public DBMeta loadDBMeta(String driver,String dbName,String characterEncoding,String ip,String port,String params,String username,String password){
		StringBuffer url = new StringBuffer("jdbc:");
		if(driver.toLowerCase().contains("mysql")){
			url.append("mysql://").append(ip).append(":").append(port).append("/").append(dbName).append("?characterEncoding=").append(characterEncoding)
			.append("&").append(params);
		}
		else{
			url.append("oracle:thin:@").append(ip).append(":").append(port).append(":").append(dbName);
		}
		
		return DBUtils.loadDBMeta(driver, url.toString(), username, password);
	}

	public void saveSqlCache(String sqlKey,String contextStr){

		baseCommonDAO.saveSqlCache(sqlKey,contextStr);
	}
}