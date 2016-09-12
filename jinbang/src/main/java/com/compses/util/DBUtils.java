package com.compses.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.compses.model.DevSqlCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.compses.common.Constants;
import com.compses.dto.DBColumn;
import com.compses.dto.DBMeta;
import com.compses.dto.DBTable;
import com.compses.dto.Page;

public class DBUtils {
	public static final String SAVE = "save";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public static final String SELECT_ONE = "selectOne";
	public static final String SELECT_LIST = "selectList";
	public static Pattern pattern = Pattern.compile("(\\{.*?\\})");
	public static Pattern wordPattern=Pattern.compile(":(\\$*\\w+)",Pattern.CASE_INSENSITIVE);//java变量
	public static Pattern optPattern=Pattern.compile(":(\\$*\\w+Opt)",Pattern.CASE_INSENSITIVE);//以Opt结尾的变量
	public static String split=":";
	public static Map<String, Properties> sqlPropMap = new HashMap<String, Properties>();
	public static Map<String, DevSqlCache> sqlMap = new HashMap<String, DevSqlCache>();
	public static Map<String, String> filterMap=new HashMap<String,String>();
	public static Logger logger = LoggerFactory.getLogger(DBUtils.class);  
	//public static final String resources = "com" + File.separator + "compses"+ File.separator + "resources";
	public static final String resources = "sql/";
	static{
		filterMap.put("YYYY-MM-DD HH:MM:SS", "");
		filterMap.put("YYYY-MM-DD HH12:MM:SS", "");
		filterMap.put("YYYY-MM-DD HH24:MM:SS", "");
	}

	private static String getFormatSql(String originalSql,Map paramMap){
		List<String> paramList=new ArrayList<String>();
		Pattern filterPattern;
		Matcher matcher=pattern.matcher(originalSql);
		String returnSql=null;
		returnSql=originalSql;
		while(matcher.find()){
			paramList.add(matcher.group(1));
        }
		for (int i = 0; i < paramList.size(); i++) {//空变量替换
			Iterator<String> keyIt=filterMap.keySet().iterator();
			String matchStr=paramList.get(i);
			while (keyIt.hasNext()) {
				String key = (String) keyIt.next();
				if(matchStr.toUpperCase().contains(key.toUpperCase())){
					filterPattern=Pattern.compile("("+key+")",Pattern.CASE_INSENSITIVE);
					Matcher filterMatcher=filterPattern.matcher(matchStr);
					while(filterMatcher.find()){
						String matchWord=filterMatcher.group(1);
						matchStr=matchStr.replace(matchWord, "");
					}
				}
			}
			Matcher wordMatcher=wordPattern.matcher(matchStr);
			while(wordMatcher.find()){
				String matchWord=wordMatcher.group(1);
				if(StringUtils.isEmptyForObject(paramMap.get(matchWord))){
					returnSql=returnSql.replace(paramList.get(i), "");
				}
			}
		}
		Matcher optMatcher=optPattern.matcher(returnSql);//操作符变量替换
		while(optMatcher.find()){
			String matchWord=optMatcher.group(1);
			returnSql=returnSql.replace(split+matchWord, paramMap.get(matchWord).toString());
		}
		returnSql=returnSql.replaceAll("\\{", "");
		returnSql=returnSql.replaceAll("\\}", "");
		return returnSql;
	}

	public static String getSql(String key,NamedParameterJdbcTemplate namedParameterJdbcTemplate) throws Exception {
		if (!sqlMap.containsKey(key)||!Constants.getContextProperty("cacheSql").equals("true")) {
			String sql=DBUtils.getSql("DevSqlCache","selectOne");
			DevSqlCache cache = new DevSqlCache();
			cache.setSqlKey(key);
			cache = DBUtils.queryForObject(sql, cache, namedParameterJdbcTemplate, cache.getClass());
			if (cache==null){
				throw new Exception("数据库DEV_SQL_CACHE无："+key);
			}
			sqlMap.put(key,cache);
			return cache.getContext();
		}
		return sqlMap.get(key).getContext();
	}

	public static String getSql(String propertiesName, String key) {
		Properties prop = sqlPropMap.get(propertiesName);
		if (prop == null||!Constants.getContextProperty("cacheSql").equals("true")) {
			prop = new Properties();
			synchronized (sqlPropMap) {
				//String fileName = resources + File.separator + propertiesName+ ".properties";
				String fileName = resources +  propertiesName+ ".properties";
				InputStream inputStream = DBUtils.class.getClassLoader()
						.getResourceAsStream(fileName);
				if (inputStream == null) {
					logger.error("don't find  file "+fileName);
				}
				try {
					prop.load(inputStream);
					sqlPropMap.put(propertiesName, prop);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (inputStream != null) {
							inputStream.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		String returnSql="";
		try {
			returnSql = new String(prop.getProperty(key).getBytes("ISO-8859-1"),"utf-8");
			logger.debug(new String(prop.getProperty(key).getBytes("ISO-8859-1"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnSql;
	}
	
	private static String getPageSql(String sql,Page page){
		String dbType=Constants.getContextProperty("dbType").toString();
		StringBuffer paginationSQL=new StringBuffer(sql.length()+100);
		if(dbType.equals("oracle")){
			paginationSQL.append(" SELECT * FROM ( ");
			paginationSQL.append(" SELECT TEMP.* ,ROWNUM NUM FROM ( ");
			paginationSQL.append(sql);
			paginationSQL.append(") TEMP)");
			paginationSQL.append("  WHERE NUM > " + page.getStart()+" AND NUM<="+(page.getLimit()+page.getStart()));
		}else if(dbType.equals("mysql")){
			paginationSQL.append("SELECT " +sql.substring(6) +" LIMIT "+ page.getStart()+" , " +page.getLimit());
		}else{
			paginationSQL.append("SELECT "+" SKIP "+page.getStart()+" FIRST " +page.getLimit()+sql.substring(6));
		}
		return paginationSQL.toString();
	}
	private static String getCountSql(String sql){
		return "select count(*) count from ("+sql+") t";
	}
	
	public static  <clazz> Page<clazz> queryPage(String sql,Page<clazz> page,Object[] params,
			JdbcTemplate jdbcTemplate, Class<clazz> clazz) {
		String pageSql=getPageSql(sql, page);
		String countSql=getCountSql(sql);
		List<clazz> result=jdbcTemplate.query(pageSql,params,BeanPropertyRowMapper.newInstance(clazz));
		long totalCount=jdbcTemplate.queryForObject(countSql, Long.class);
		page.setResult(result);
		page.setTotalCount(totalCount);
		return page;
	}
	public static  <clazz> Page<clazz> queryPage(String sql,Page<clazz> page,Object paramObj,
			NamedParameterJdbcTemplate namedParameterJdbcTemplate, Class<clazz> clazz) {
		Map paramMap=JsonUtils.toMap(paramObj);
		String formatSql=getFormatSql(sql,paramMap);
		String pageSql=getPageSql(formatSql, page);
		String countSql=getCountSql(formatSql);
		List<clazz> result=null;
		long totalCount=0;
		if(paramObj==null){
			totalCount=namedParameterJdbcTemplate.queryForObject(countSql,paramMap,Long.class); 
			result=namedParameterJdbcTemplate.query(pageSql,paramMap,BeanPropertyRowMapper.newInstance(clazz)); 
		}else if (paramObj instanceof Map){
			totalCount=namedParameterJdbcTemplate.queryForObject(countSql,paramMap,Long.class); 
			result=namedParameterJdbcTemplate.query(pageSql, (Map)paramObj,BeanPropertyRowMapper.newInstance(clazz));
		}else{
			SqlParameterSource paramSource = new BeanPropertySqlParameterSource(paramObj); 
			totalCount=namedParameterJdbcTemplate.queryForObject(countSql,paramSource,Long.class); 
			result=namedParameterJdbcTemplate.query(pageSql,paramSource,BeanPropertyRowMapper.newInstance(clazz)); 
		}
		page.setResult(result);
		page.setTotalCount(totalCount);
		return page;
	}
	public static  Page<Map<String,Object>> queryPage(String sql,Page page,Object paramObj,
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		Map paramMap=JsonUtils.toMap(paramObj);
		String formatSql=getFormatSql(sql,paramMap);
		String pageSql=getPageSql(formatSql, page);
		String countSql=getCountSql(formatSql);
		long totalCount=0;
		List<Map<String,Object>> result=null;
		if(paramObj==null){
			totalCount=namedParameterJdbcTemplate.queryForObject(countSql,paramMap,Long.class); 
			result=namedParameterJdbcTemplate.queryForList(pageSql, paramMap);
		}else if (paramObj instanceof Map){
			totalCount=namedParameterJdbcTemplate.queryForObject(countSql,paramMap,Long.class); 
			result=namedParameterJdbcTemplate.queryForList(pageSql, (Map)paramObj);
		}else{
			SqlParameterSource paramSource = new BeanPropertySqlParameterSource(paramObj); 
			totalCount=namedParameterJdbcTemplate.queryForObject(countSql,paramSource,Long.class); 
			result= namedParameterJdbcTemplate.queryForList(pageSql, paramSource);
		}
		page.setResult(result);
		page.setTotalCount(totalCount);
		return page;
	}
	
	public static  Page<Map<String,Object>> queryPage(String sql,Page page,
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		Map paramMap=JsonUtils.toMap(page.getConditions());
		String formatSql=getFormatSql(sql,paramMap);
		String pageSql=getPageSql(formatSql, page);
		String countSql=getCountSql(formatSql);
		long totalCount=0;
		List<Map<String,Object>> result=null;
		if(page.getConditions()==null||page.getConditions().keySet().size()==0){
			totalCount=namedParameterJdbcTemplate.queryForObject(countSql,paramMap,Long.class); 
			result=namedParameterJdbcTemplate.queryForList(pageSql, paramMap);
		}else if (page.getConditions() instanceof Map){
			totalCount=namedParameterJdbcTemplate.queryForObject(countSql,paramMap,Long.class); 
			result=namedParameterJdbcTemplate.queryForList(pageSql, (Map)page.getConditions());
		}else{
			SqlParameterSource paramSource = new BeanPropertySqlParameterSource(page.getConditions()); 
			totalCount=namedParameterJdbcTemplate.queryForObject(countSql,paramSource,Long.class); 
			result= namedParameterJdbcTemplate.queryForList(pageSql, paramSource);
		}
		page.setResult(result);
		page.setTotalCount(totalCount);
		return page;
	}
	
	public static <clazz> List<clazz> query(String sql,Object paramObj,
			NamedParameterJdbcTemplate namedParameterJdbcTemplate, Class<clazz> clazz) {
		Map paramMap=JsonUtils.toMap(paramObj);
		String formatSql=getFormatSql(sql,paramMap);
		if(paramObj==null){
		    return namedParameterJdbcTemplate.query(formatSql,BeanPropertyRowMapper.newInstance(clazz)); 
		}else if (paramObj instanceof Map){
			return namedParameterJdbcTemplate.query(formatSql, (Map)paramObj,BeanPropertyRowMapper.newInstance(clazz));
		}else{
			SqlParameterSource paramSource = new BeanPropertySqlParameterSource(paramObj); 
		    return namedParameterJdbcTemplate.query(formatSql,paramSource,BeanPropertyRowMapper.newInstance(clazz)); 
		}
		
	}
	
	public static List<Map<String,Object>> query(String sql,Object paramObj,
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		Map paramMap=JsonUtils.toMap(paramObj);
		String formatSql=getFormatSql(sql,paramMap);
		if(paramObj==null){
			Map<String,Object> temp = new HashMap<String, Object>();
		    return namedParameterJdbcTemplate.queryForList(formatSql,temp); 
		}else if(paramObj instanceof Map){
			return namedParameterJdbcTemplate.queryForList(formatSql, (Map)paramObj);
		}else{
			SqlParameterSource paramSource = new BeanPropertySqlParameterSource(paramObj); 
		    return namedParameterJdbcTemplate.queryForList(formatSql,paramSource); 
		}
		
	}
	public static <clazz> Page<clazz> queryPage(String sql,Page page,
			NamedParameterJdbcTemplate namedParameterJdbcTemplate, Class<clazz> clazz) {
		String formatSql=getFormatSql(sql,page.getConditions());
	    String pageSql=getPageSql(formatSql, page);
		String countSql=getCountSql(formatSql);
		page.setResult(namedParameterJdbcTemplate.query(pageSql,page.getConditions(),BeanPropertyRowMapper.newInstance(clazz))); 
		page.setTotalCount(namedParameterJdbcTemplate.queryForObject(countSql, page.getConditions(), Long.class));
		return page;
	}
	/**
	 * 不带参数的sql
	 * @param <clazz>
	 * @param sql
	 * @param namedParameterJdbcTemplate
	 * @param clazz
	 * @return
	 */
	public  static <clazz> List<clazz> query(String sql,NamedParameterJdbcTemplate namedParameterJdbcTemplate, Class<clazz> clazz) {
	    return namedParameterJdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(clazz)); 
	}
	
	public  static <clazz> clazz  queryForObject(String sql,Object paramObj,
			NamedParameterJdbcTemplate namedParameterJdbcTemplate, Class<clazz> clazz) {
		Map paramMap=JsonUtils.toMap(paramObj);
		String formatSql=getFormatSql(sql,paramMap);
		List<clazz> list=null;
		boolean isInteger=Integer.class.isAssignableFrom(clazz);
		boolean isLong=Long.class.isAssignableFrom(clazz);
		if(paramObj==null||paramObj instanceof Map){
			if(isInteger){
				return (clazz) namedParameterJdbcTemplate.queryForObject(sql,paramMap, Integer.class);
			}
			if(isLong){
				return (clazz) namedParameterJdbcTemplate.queryForObject(sql,paramMap, Long.class);
			}
			if(Map.class.isAssignableFrom(clazz)){
				list=(List<clazz>)namedParameterJdbcTemplate.queryForList(sql,paramMap);
			}else{
				list=namedParameterJdbcTemplate.query(sql,paramMap,BeanPropertyRowMapper.newInstance(clazz)); 
			}
		}else{
			SqlParameterSource paramSource = new BeanPropertySqlParameterSource(paramObj); 
			if(isInteger){
				return (clazz) namedParameterJdbcTemplate.queryForObject(sql,paramSource, Integer.class);
			}
			if(isLong){
				return (clazz) namedParameterJdbcTemplate.queryForObject(sql,paramSource, Long.class);
			}
			if(Map.class.isAssignableFrom(clazz)){
				list=(List<clazz>)namedParameterJdbcTemplate.queryForList(formatSql,paramSource);
			}else{
				list=namedParameterJdbcTemplate.query(formatSql,paramSource,BeanPropertyRowMapper.newInstance(clazz));
			}
		}
	    return list.size()==0?null:list.get(0); 
	}

	/**
	 * 单个操作
	 * @param sql
	 * @param namedParameterJdbcTemplate
	 * @param object
	 * @return
	 */
	public static int update(String sql,NamedParameterJdbcTemplate namedParameterJdbcTemplate,Object object){
		Map paramMap=JsonUtils.toMap(object);
		String formatSql=getFormatSql(sql,paramMap);
		if(object instanceof Map){
			return namedParameterJdbcTemplate.update(formatSql, (Map)object);
		}
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(object); 
		return namedParameterJdbcTemplate.update(formatSql, paramSource);
	}
	
	/**
	 * 单个操作
	 * @param sql
	 * @param namedParameterJdbcTemplate
	 * @param object
	 * @return
	 */
	public static String insertForUUID(String sql,NamedParameterJdbcTemplate namedParameterJdbcTemplate,Object object){
		Map paramMap=JsonUtils.toMap(object);
		String uuid =UUIDUtils.getUUID();
		for (Object ob : paramMap.keySet()){
			String idPara = ob.toString();
			paramMap.put(idPara,uuid);
			break;
		}
		String formatSql=getFormatSql(sql, paramMap);
		SqlParameterSource paramSource;
		if(object instanceof Map){
			Map result = (Map)object;
			for (Object ob : paramMap.keySet()){
				String idPara = ob.toString();
				result.put(idPara,uuid);
				break;
			}
			paramSource =  new MapSqlParameterSource(result);
				}
		else{
				paramSource = new BeanPropertySqlParameterSource(object);

				}
		namedParameterJdbcTemplate.update(formatSql, paramSource);
		return uuid;
	}

	/**
	 * 单个操作
	 * @param sql
	 * @param namedParameterJdbcTemplate
	 * @param object
	 * @return
	 */
	public static Long insert(String sql,NamedParameterJdbcTemplate namedParameterJdbcTemplate,Object object){
		KeyHolder keyHolder = new GeneratedKeyHolder();
		Map paramMap=JsonUtils.toMap(object);
		String formatSql=getFormatSql(sql,paramMap);
		SqlParameterSource paramSource;
		if(object instanceof Map){
			paramSource =  new MapSqlParameterSource((Map)object);
		}
		else{
			paramSource = new BeanPropertySqlParameterSource(object);
		}
//		String uuid =UUIDUtils.getUUID();
		namedParameterJdbcTemplate.update(formatSql, paramSource,keyHolder);
//		namedParameterJdbcTemplate.update(formatSql, paramSource);
		return keyHolder.getKey().longValue();
//		return uuid;
	}
	
	/**
	 * 批量操作
	 * @param sql
	 * @param namedParameterJdbcTemplate
	 * @param objectArr
	 * @return
	 */
	public static int[] batchUpdate(String sql,NamedParameterJdbcTemplate namedParameterJdbcTemplate,Object[] objectArr){
		SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(objectArr);
	    return namedParameterJdbcTemplate.batchUpdate(sql, params);
	}
	
	/**
	 * 批量操作
	 * @param sql
	 * @param namedParameterJdbcTemplate
	 * @param objectList
	 * @return
	 */
	public static int[] batchUpdate(String sql,NamedParameterJdbcTemplate namedParameterJdbcTemplate,List<?> objectList){
		Object[] objectArr=(Object[])objectList.toArray(new Object[0]);
		return batchUpdate(sql,namedParameterJdbcTemplate,objectArr);
	}
	
	public static boolean testDBConnection(String driver,String url,String username,String password){
		Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url,username,password);
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        } finally {
            try {
                if (null != conn) {
                    conn.close();
                }
            } catch (Exception e2) {
            }
        }
		return false;
	}
	
	public static DBMeta loadDBMeta(String driver,String url,String username,String password){
		DBMeta meta = new DBMeta();
		Map<String, String> fkTableNamesAndPk = new HashMap<String, String>();
		Connection conn = null;
        DatabaseMetaData metaData = null;
        ResultSet rs = null;
        ResultSet crs = null;
        try {
            Class.forName(driver);
            Properties props =new Properties();
            props.setProperty("user", username);
            props.setProperty("password", password);
            props.setProperty("remarks", "true"); //设置可以获取remarks信息 
            props.setProperty("useInformationSchema", "true");//设置可以获取tables remarks信息
            conn = DriverManager.getConnection(url,props);
            String catalog = conn.getCatalog(); // catalog 其实也就是数据库名
            metaData = conn.getMetaData();
            meta.setDbName(catalog);
            // 获取表
            rs = metaData.getTables(null, "%", "%", new String[] { "TABLE" });
            while (rs.next()) {
            	DBTable table = new DBTable();
            	meta.getTables().add(table);
                String tablename = rs.getString("TABLE_NAME");
                String tremarks = rs.getString("REMARKS");
                table.setTableName(tablename);
                table.setRemarks(tremarks);
                // 获取当前表的列
                crs = metaData.getColumns(null, "%", tablename, "%");
                ResultSet pks = metaData.getPrimaryKeys(catalog, null, tablename);
                while(pks.next()){
                	table.getPks().add(pks.getString("COLUMN_NAME"));
                }
                // 获取被引用的表，它的主键就是当前表的外键 
                fkTableNamesAndPk.clear();
                ResultSet foreignKeyResultSet = metaData.getImportedKeys(catalog, null, tablename);
                while (foreignKeyResultSet.next()) {
                    String pkTablenName = foreignKeyResultSet.getString("PKTABLE_NAME"); // 外键表
                    String fkColumnName = foreignKeyResultSet.getString("FKCOLUMN_NAME"); // 外键
                    if (!fkTableNamesAndPk.containsKey(fkColumnName))
                        fkTableNamesAndPk.put(fkColumnName, pkTablenName);
                    table.getFks().add(fkColumnName);
                }
                while (crs.next()) {
                	DBColumn column = new DBColumn();
                	table.getColumns().add(column);
                    String columnname = crs.getString("COLUMN_NAME");
                    String columntype = crs.getString("TYPE_NAME");
                    String remarks = crs.getString("REMARKS");
                    int nullAble = crs.getInt("NULLABLE");
                    int size = crs.getInt("COLUMN_SIZE");
                    String defaultValue = crs.getString("COLUMN_DEF");
                    column.setName(columnname);
                    column.setType(columntype);
                    column.setRemarks(remarks);
                    column.setDefaultValue(defaultValue);
                    column.setSize(size);
                    column.setNullAble(nullAble);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace(System.out);
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
                if (null != conn) {
                    conn.close();
                }
            } catch (Exception e2) {
            }
        }
		return meta;
	}
	
	public static void main(String[] args) {
		testDBConnection("com.mysql.jdbc.Driver", 
				"jdbc:mysql://localhost:3307/dopdb?characterEncoding=utf8&generateSimpleParameterMetadata=true&autoReconnect=true"
				,"root","");
	}
}
