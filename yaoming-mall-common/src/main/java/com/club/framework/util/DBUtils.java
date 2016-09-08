package com.club.framework.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.club.core.common.Page;
import com.club.core.common.QueryCondition;
import com.club.core.common.QueryCondition.ConditionOperation;
import com.club.core.idproduce.ISequenceGenerator;
import com.club.core.spring.context.CustomPropertyConfigurer;
import com.club.framework.exception.BaseAppException;
import com.club.web.common.cache.DBMetaCache;
import com.club.web.common.db.po.WfDbColumnsPO;
import com.club.web.common.vo.DBColumn;
import com.club.web.common.vo.DBMeta;
import com.club.web.common.vo.DBTable;

public class DBUtils {
    public static Pattern pattern = Pattern.compile("(\\{.*?\\})");
    public static Pattern wordPattern=Pattern.compile(":(\\$*\\w+)",Pattern.CASE_INSENSITIVE);//java变量
//    public static Pattern optPattern=Pattern.compile(":(\\$*\\w+Opt)",Pattern.CASE_INSENSITIVE);//以Opt结尾的变量
    public static String split=":";
    public static Map<String, String> filterMap=new HashMap<String,String>();
    static{
        filterMap.put("YYYY-MM-DD HH:MM:SS", "");
        filterMap.put("YYYY-MM-DD HH12:MM:SS", "");
        filterMap.put("YYYY-MM-DD HH24:MM:SS", "");
    }
    private static final Logger logger = LoggerFactory.getLogger(DBUtils.class);

    public static String getPageSql(String sql,Page page){
        String dbType= CustomPropertyConfigurer.getContextProperty("dbType").toString();
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
    public static String getCountSql(String sql){
        return "select count(*) count from ("+sql+") t";
    }

    public static <T> Map<String,Object> getUpdateSql(T record) throws BaseAppException {
        Map<String,Object> keyValues = null;
        try {
            keyValues = BeanUtils.convertBean(record,null);
        }
        catch (Exception e){
            throw new BaseAppException("update record convert Map error：",e);
        }
        String sql = getUpdateTableSql(DBUtils.getTableName(record), keyValues);
        keyValues.put("sql", sql);
        logger.info("general update sql :{0}",sql);
        return keyValues;
    }

    public static String defaultLength(String type){
        if("INT".equals(type)){
            return "11";
        }else if("VARCHAR".equals(type)){
            return "255";
        }else if("CHAR".equals(type)){
            return "255";
        }else if("TEXT".equals(type)){
            return "0";
        }else if("TINYINT".equals(type)){
            return "4";
        }else if("BIGINT".equals(type)){
            return "20";
        }else if("DOUBLE".equals(type)){
            return "0";
        }else if("BIT".equals(type)){
            return "1";
        }else if("DATETIME".equals(type)){
            return "0";
        }
        return "1" ;
    }

    public  static String generalSql(List<WfDbColumnsPO> columns) {
        if(columns!= null && columns.size() > 0){
            String sql = " create table " + columns.get(0).getTableName().toUpperCase() + "(" ;
            String pkStatement = " primary key (";
            String typeLength = "" ;
            String autoIncrement = "" ;
            String defaultValue = "" ;
            for(WfDbColumnsPO po:columns){
                typeLength = "";
                autoIncrement = "" ;
                defaultValue = "" ;
                if(!po.getDbType().equals("DATETIME")&&!po.getDbType().equals("DOUBLE")){
                    typeLength = "(";
                    if(Utils.isEmpty(po.getLength())){
                        typeLength = typeLength + defaultLength(po.getDbType()) + ")" ;
                    }else{
                        typeLength = typeLength + po.getLength() + ")";
                    }
                }else{
                    typeLength = " ";
                }
                if("pk".equals(po.getType())&&("INT".equals(po.getDbType())||"BIGINT".equals(po.getDbType())||"TINYINT".equals(po.getDbType()))){
                    autoIncrement = " AUTO_INCREMENT " ;
                }else{
                    autoIncrement = " " ;
                }
                if(!"pk".equals(po.getType())&&!"TEXT".equals(po.getDbType())){
                    if (StringUtils.isEmpty(po.getDefaultValue())){
                        defaultValue = "" ;
                    }else{
                        if("BIT".equals(po.getDbType())){
                            defaultValue = " default " + po.getDefaultValue() + " " ;
                        }else{
                            defaultValue = " default '" + po.getDefaultValue() + "' ";
                        }
                    }
                }
                sql = sql + " " + po.getColumnName().toUpperCase() + " " + po.getDbType()
                        + typeLength +
                        (("1".equals(po.getIsNull()))? " ":" not null ") +
                        autoIncrement +
                        " comment '" + po.getDisplayName() +"' " +
                        defaultValue+",";
                if("pk".equals(po.getType())){
                    pkStatement = pkStatement + po.getColumnName().toUpperCase() +",";
                }
            }
            if(!" primary key (".equals(pkStatement)){
                pkStatement = pkStatement.substring(0,pkStatement.length()-1)+")";
                sql = sql  + pkStatement + ");";
            }else{
                sql = sql.substring(0,sql.length()-1)+");";
            }
            return sql ;
        }
        return "" ;
    }

    public static String getUpdateTableSql(String tableName,Map<String,Object> record) throws BaseAppException {
        DBTable table = DBMetaCache.getTable(tableName);
        if (table==null){
            throw new RuntimeException("表【"+tableName+"】未找到！");
        }
        StringBuffer sqlBuffer = new StringBuffer("update ");
        sqlBuffer.append(table.getTableName());

        StringBuffer keys = new StringBuffer(" set ");
        StringBuffer wheres = new StringBuffer(" where 1=1 AND ");

        for (DBColumn column : table.getColumns()){
            String columnName = getColumnName(record, column.getColumnName());

            if(columnName!=null) {
                if(column.getDbType().toUpperCase().indexOf("DATE")!=-1||column.getDbType().toUpperCase().indexOf("TIME")!=-1
                        ||column.getDbType().toUpperCase().equals("INT")||column.getDbType().equals("LONG")||column.getDbType().toUpperCase().equals("BIGINT")){
                    if(StringUtils.isEmpty(record.get(columnName)))continue;
                }
                if (column.getType() != null && column.getType().indexOf("pk") != -1) {
                    wheres.append(column.getColumnName() + "=#{" + columnName + "} AND ");
                } else if (record.containsKey(columnName) && record.get(columnName) != null) {
                    keys.append(column.getColumnName());
                    keys.append("=#{" + columnName + "},");
                }
                if (column.getDbType().toUpperCase().equals("LONG") || column.getDbType().toUpperCase().equals("INT") || column.getDbType().toUpperCase().equals("BIGINT")) {
                    record.put(columnName, Integer.parseInt(record.get(columnName) + ""));
                }
            }
        }

        sqlBuffer.append(keys.toString().substring(0, keys.length() - 1) + wheres.toString().substring(0, wheres.length() - 5));

        logger.info("general update sql :{0}",sqlBuffer.toString());
        return sqlBuffer.toString();
    }

    public static  <T> Map<String,Object> getDeleteSql(T record) throws BaseAppException {
        Map<String,Object> keyValues = null;
        try {
            keyValues = BeanUtils.convertBean(record,null);
        }
        catch (Exception e){
            throw new BaseAppException("selectOne record convert Map error：",e);
        }
        keyValues.put("sql", getDeleteSql(DBUtils.getTableName(record)));
        logger.info("general delate sql :{0}", keyValues.get("sql"));
        return keyValues;
    }

    public static<T> String getTableName(T record){
        String tableName = record.getClass().getSimpleName();
        return StringUtils.toDBString(tableName);
    }

    public static String getDeleteSql(String tableName) throws BaseAppException {
        DBTable table = DBMetaCache.getTable(tableName);
        if (table==null){
            throw new RuntimeException("表【"+tableName+"】未找到！");
        }
        StringBuffer sqlBuffer = new StringBuffer("delete from ");
        sqlBuffer.append(table.getTableName());

        StringBuffer wheres = new StringBuffer(" where 1=1 AND ");

        for (DBColumn column : table.getColumns()){
            String columnName = StringUtils.toHump(column.getColumnName());

            if (column.getType()!=null&&column.getType().indexOf("pk")!=-1){
                wheres.append(column.getColumnName()+"=#{"+columnName+"} AND ");
            }
        }

        sqlBuffer.append(wheres.toString().substring(0, wheres.length() - 5));

        logger.info("general delate sql :{0}", sqlBuffer.toString());
        return sqlBuffer.toString();
    }

    public static <T> Map<String,Object> getInsertSql(T record) throws BaseAppException {
        Map<String,Object> keyValues = null;
        try {
            keyValues = BeanUtils.convertBean(record,null);
        }
        catch (Exception e){
            throw new BaseAppException("insert record convert Map error：",e);
        }

        String sql = getInsertTableSql(DBUtils.getTableName(record),keyValues);
        keyValues.put("sql", sql);
        logger.info("general insert sql :{0}", sql);
        return keyValues;
    }

    public static<T> List<DBColumn> getTalbePK(T record) throws BaseAppException {
        if (record instanceof Map){
            throw new BaseAppException("不支持map接口");
        }
        else {return getTalbePK(DBUtils.getTableName(record));}
    }

    public static List<DBColumn> getTalbePK(String tableName) throws BaseAppException {
        List<DBColumn> pks = new ArrayList<DBColumn>();
        DBTable table = DBMetaCache.getTable(tableName);
        if (table==null){
            throw new RuntimeException("表【"+tableName+"】未找到！");
        }
        for (DBColumn column : table.getColumns()){
            if (column.getType()!=null&&column.getType().toLowerCase().indexOf("pk")!=-1) {
                pks.add(column);
            }
        }
        return pks;
    }

    public static<T> DBTable getTalbe(T record) throws BaseAppException {
        if (record instanceof Map){
            throw new BaseAppException("不支持map接口");
        }
        else {
            return getTalbe(DBUtils.getTableName(record));
        }
    }

    public static DBTable getTalbe(String tableName) throws BaseAppException {
        return DBMetaCache.getTable(tableName);
    }

    public static String getColumnName(Map<String,Object> record,String columnName){
        if(record.containsKey(columnName.toUpperCase())){
            return columnName.toUpperCase();
        }
        else if(record.containsKey(columnName.toLowerCase())){
            return columnName.toLowerCase();
        }
        else if(record.containsKey(StringUtils.toHump(columnName))){
            return StringUtils.toHump(columnName);
        }
        else{
            return null;
        }
    }

    public static String getInsertTableSql(String tableName,Map<String,Object> record) throws BaseAppException {
        DBTable table = DBMetaCache.getTable(tableName);
        if (table==null){
            throw new RuntimeException("表【"+tableName+"】未找到！");
        }
        StringBuffer sqlBuffer = new StringBuffer("insert into ");
        sqlBuffer.append(table.getTableName());

        StringBuffer keys = new StringBuffer("(");
        StringBuffer values = new StringBuffer(" values(");

        for (DBColumn column : table.getColumns()){
            String columnName = getColumnName(record,column.getColumnName());

            if (columnName!=null&&record.get(columnName)!=null) {
                if(column.getDbType().toUpperCase().indexOf("DATE")!=-1||column.getDbType().toUpperCase().indexOf("TIME")!=-1
                        ||column.getDbType().toUpperCase().equals("INT")||column.getDbType().equals("LONG")||column.getDbType().toUpperCase().equals("BIGINT")){
                    if(StringUtils.isEmpty(record.get(columnName)))continue;
                }
                keys.append(column.getColumnName());
//                if (column.getDbType().toUpperCase().indexOf("DATE")!=-1||column.getDbType().toUpperCase().indexOf("TIME")!=-1) {
//                    values.append("#{" + columnName + ",jdbcType=DATE}");
//                }
//                else{
                    values.append("#{" + columnName + "}");
//                }
                keys.append(",");
                values.append(",");
                if (column.getDbType().toUpperCase().equals("INT")) {
                    record.put(columnName, Integer.parseInt(record.get(columnName) + ""));
                }else if(column.getDbType().toUpperCase().equals("LONG") ||column.getDbType().toUpperCase().equals("BIGINT")){
                    record.put(columnName, Long.parseLong(record.get(columnName) + ""));
                }
//                else if (column.getDbType().toUpperCase().indexOf("DATE")!=-1||column.getDbType().toUpperCase().indexOf("TIME")!=-1){
//                    record.put(columnName,new Date());
//                }
            }
        }
        sqlBuffer.append(keys.toString().substring(0,keys.length()-1)+")");
        sqlBuffer.append(values.toString().substring(0, values.length() - 1) + ")");

        logger.info("general insert sql :{0}", sqlBuffer.toString());
        return sqlBuffer.toString();
    }

    public static <T> Map<String,Object> getSelectOneSql(T record) throws BaseAppException{
        Map<String,Object> keyValues = null;
        Set<String> conditions=new HashSet<String>();
        try {
            keyValues = BeanUtils.convertBean(record,null);
        }
        catch (Exception e){
            throw new BaseAppException("selectOne record convert Map error：",e);
        }
        for (String condition : keyValues.keySet()){
            if(keyValues.get(condition)!=null&&!keyValues.get(condition).equals(-1)){
                conditions.add(condition);
            }
        }
        String sql = getSelectListSql(DBUtils.getTableName(record),conditions);
        keyValues.put("sql", sql);
        logger.info("general select one sql :{0}", sql);
        return keyValues;
    }

    public static String getSelectListSql(String tableName,Set<String> conditions) throws BaseAppException {
		DBTable table = DBMetaCache.getTable(tableName);
        if (table==null){
            throw new RuntimeException("表【"+tableName+"】未找到！");
        }
		StringBuffer sb = new StringBuffer("select ");
        if(table.getColumns().size()==0) sb.append(" * ");
		for(DBColumn column : table.getColumns()){
			sb.append(column.getColumnName()+" as \""+StringUtils.toHump(column.getColumnName())).append("\"");
			if(table.getColumns().indexOf(column)<table.getColumns().size()-1){
				sb.append(",");
			}
			else{
				sb.append(" ");
			}
		}
		sb.append(" from " + tableName);
		
		if(conditions.size()>0){
			sb.append(" where 1=1 ");
			for(String field : conditions){
                if(table.getColumnMap().containsKey(field)) {
                    sb.append(" and ");
                    sb.append(table.getColumnMap().get(field).getColumnName() + "=#{" + field + "} ");
                }
			}
		}
        logger.info("general select list sql :{0}", sb.toString());
		return sb.toString();
	}

    public static String getSelectListSql(String tableName,Set<String> conditions,String selectColumns) throws BaseAppException {
        DBTable table = DBMetaCache.getTable(tableName);
        if (table==null){
            throw new RuntimeException("表【"+tableName+"】未找到！");
        }
        StringBuffer sb = new StringBuffer("select ");
        if(selectColumns==null){
            if(table.getColumns().size()==0) sb.append(" * ");
            for(DBColumn column : table.getColumns()){
                sb.append(column.getColumnName()+" as \""+StringUtils.toHump(column.getColumnName())).append("\"");
                if(table.getColumns().indexOf(column)<table.getColumns().size()-1){
                    sb.append(",");
                }
                else{
                    sb.append(" ");
                }
            }
        }else {
            List<String> list=Arrays.asList(selectColumns.split(","));
            int index=0;
            for (String columnName : list) {
                sb.append(columnName+" as \""+StringUtils.toHump(columnName)).append("\"");
                if(index<list.size()-1){
                    sb.append(",");
                }
                else{
                    sb.append(" ");
                }
                index++;
            }
        }

        sb.append(" from " + tableName);

        if(conditions.size()>0){
            sb.append(" where 1=1 ");
            for(String field : conditions){
                if(table.getColumnMap().containsKey(field)) {
                    sb.append(" and ");
                    sb.append(table.getColumnMap().get(field).getColumnName() + "=#{" + field + "} ");
                }
            }
        }
        logger.info("general select list sql :{0}", sb.toString());
        return sb.toString();
    }

    public static String getSelectListSql(String tableName,Map<String,Object> record) throws BaseAppException {
		DBTable table = DBMetaCache.getTable(tableName);
        if (table==null){
            throw new RuntimeException("表【"+tableName+"】未找到！");
        }
		StringBuffer sb = new StringBuffer("select ");
        if(table.getColumns().size()==0) sb.append(" * ");
		for(DBColumn column : table.getColumns()){
			sb.append(column.getColumnName());
			if(table.getColumns().indexOf(column)<table.getColumns().size()-1){
				sb.append(",");
			}
			else{
				sb.append(" ");
			}
		}
		sb.append(" from "+tableName);

		if(record.size()>0){
			sb.append(" where 1=1 ");
			for(String field : record.keySet()){
                if(table.getColumnMap().containsKey(field)) {
                    if(table.getColumnMap().get(field).getDbType().equals("INT")||table.getColumnMap().get(field).getDbType().equals("LONG")||table.getColumnMap().get(field).getDbType().equals("BIGINT")||table.getColumnMap().get(field).getDbType().indexOf("DATE")!=-1||table.getColumnMap().get(field).getDbType().indexOf("TIME")!=-1) {
                        if(StringUtils.isEmpty(record.get(field)))continue;
                    }
                    sb.append(" and ").append(table.getColumnMap().get(field).getColumnName() + "=#{" + field + "} ");

                }
			}
		}
        logger.info("general select list sql :{0}", sb.toString());
		return sb.toString();
	}

    public static String getSelectListSql(String tableName,Map<String,Object> record,String selectColumns) throws BaseAppException {
        DBTable table = DBMetaCache.getTable(tableName);
        if (table==null){
            throw new RuntimeException("表【"+tableName+"】未找到！");
        }
        StringBuffer sb = new StringBuffer("select ");
        if(selectColumns==null){
            if(table.getColumns().size()==0) sb.append(" * ");
            for(DBColumn column : table.getColumns()){
                sb.append(column.getColumnName());
                if(table.getColumns().indexOf(column)<table.getColumns().size()-1){
                    sb.append(",");
                }
                else{
                    sb.append(" ");
                }
            }
        }else {
            List<String> list=Arrays.asList(selectColumns.split(","));
            int index=0;
            for (String columnName : list) {
                sb.append(columnName+" as \""+StringUtils.toHump(columnName)).append("\"");
                if(index<list.size()-1){
                    sb.append(",");
                }
                else{
                    sb.append(" ");
                }
                index++;
            }
        }
        sb.append(" from "+tableName);

        if(record.size()>0){
            sb.append(" where 1=1 ");
            for(String field : record.keySet()){
                if(table.getColumnMap().containsKey(field)) {
                    if(table.getColumnMap().get(field).getDbType().equals("INT")||table.getColumnMap().get(field).getDbType().equals("LONG")||table.getColumnMap().get(field).getDbType().equals("BIGINT")||table.getColumnMap().get(field).getDbType().indexOf("DATE")!=-1||table.getColumnMap().get(field).getDbType().indexOf("TIME")!=-1) {
                        if(StringUtils.isEmpty(record.get(field)))continue;
                    }
                    sb.append(" and ").append(table.getColumnMap().get(field).getColumnName() + "=#{" + field + "} ");

                }
            }
        }
        logger.info("general select list sql :{0}", sb.toString());
        return sb.toString();
    }
	
	public static Map<String,Object> getSelectListSql(String tableName,List<QueryCondition> conditions){
	    Map<String,Object> params = new HashMap<String, Object>();
        DBTable table = DBMetaCache.getTable(tableName);
        StringBuffer sb = new StringBuffer("select ");
        for(DBColumn column : table.getColumns()){
            sb.append(column.getColumnName()+" as \""+StringUtils.toHump(column.getColumnName())).append("\"");
            if(table.getColumns().indexOf(column)<table.getColumns().size()-1){
                sb.append(",");
            }
            else{
                sb.append(" ");
            }
        }
        sb.append(" from "+tableName+" A ");
        
        int size = conditions.size();
        if(size>0){
            sb.append(" where ");
            for (int i = 0; i < size; i++) {
                QueryCondition condition = conditions.get(i);
                sb.append(StringUtils.toDBString(condition.getParamName())).append(paraseParamValue(condition,params));
                if(i < size-1){
                    sb.append(" and ");
                }
            }
        }
        params.put("sql", sb.toString());
        logger.info("general select list sql :{0}", sb.toString());
        return params;
    }
	
	public static String paraseParamValue(QueryCondition condition,Map<String,Object> params){
	    ConditionOperation operation = condition.getOperation();
	    StringBuffer sb = new StringBuffer();
	    switch (operation) {
            case IsNull:
                sb.append(" is null ");
                break;
            case IsNotNull:
                sb.append(" is not null ");
                break;
            case EqualTo:
                sb.append(" =#{").append(condition.getParamName()).append("} ");
                params.put(condition.getParamName(), condition.getParamValue()[0]);
                break;
            case NotEqualTo:
                sb.append(" <>#{").append(condition.getParamName()).append("} ");
                params.put(condition.getParamName(), condition.getParamValue()[0]);
                break;
            case GreaterThan:
                sb.append(" >#{").append(condition.getParamName()).append("} ");
                params.put(condition.getParamName(), condition.getParamValue()[0]);
                break;
            case GreaterThanOrEqualTo:
                sb.append(" >=#{").append(condition.getParamName()).append("} ");
                params.put(condition.getParamName(), condition.getParamValue()[0]);
                break;
            case LessThan:
                sb.append(" <#{").append(condition.getParamName()).append("} ");
                params.put(condition.getParamName(), condition.getParamValue()[0]);
                break;
            case LessThanOrEqualTo:
                sb.append(" <=#{").append(condition.getParamName()).append("} ");
                params.put(condition.getParamName(), condition.getParamValue()[0]);
                break;
            case Like:
                sb.append(" like #{").append(condition.getParamName()).append("} ");
                params.put(condition.getParamName(), "%" + condition.getParamValue()[0] + "%");
                break;
            case NotLike:
                sb.append(" not like #{").append(condition.getParamName()).append("} ");
                params.put(condition.getParamName(), "%" + condition.getParamValue()[0] + "%");
                break;
            case In:
                sb.append(" in ").append(StringUtils.getQryCondtion(condition.getParamValue(), false)).append(" ");
                break;
            case NotIn:
                sb.append(" not in ").append(StringUtils.getQryCondtion(condition.getParamValue(), false)).append(" ");
                break;
            default:
                sb.append(condition.getParamName());
                break;
        }
	    return sb.toString();
	}
	public static boolean testDBConnection(String driver,String url,String username,String password) throws BaseAppException{
		Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url,username,password);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseAppException("数据库连接异常");
        } finally {
            try {
                if (null != conn) {
                    conn.close();
                }
            } catch (Exception e2) {
            }
        }
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
            if(StringUtils.isEmpty(catalog)){
                meta.setDbName(ISequenceGenerator.DEFAULT_SCHEMA);
            }
            else {
                meta.setDbName(catalog);
            }
            // 获取表
            rs = metaData.getTables(null, username.toUpperCase(), "%", new String[]{"TABLE"});
            while (rs.next()) {
            	DBTable table = new DBTable();
            	meta.getTables().add(table);
                String tablename = rs.getString("TABLE_NAME");
                String tremarks = rs.getString("REMARKS");
                if(tremarks.length()>200) tremarks = tremarks.substring(0,199);
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
                    if(remarks.length()>200) remarks = remarks.substring(0,199);
                    for(String key : table.getPks()){
                    	if(columnname.equals(key)){
                    		column.setType("pk");
                    	}
                    }
                    for(String key : table.getFks()){
                    	if(columnname.equals(key)){
                    		column.setType("fk");
                    	}
                    }
                    int nullAble = crs.getInt("NULLABLE");
                    int size = crs.getInt("COLUMN_SIZE");
                    String defaultValue = crs.getString("COLUMN_DEF");
                    column.setColumnName(columnname);
                    column.setDbType(columntype);
                    column.setDisplayName(remarks);
                    column.setDefaultValue(defaultValue);
                    column.setLength(size);
                    column.setIsNull(nullAble+"");
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
	

    public static String getFormatSql(String originalSql,Map<String,Object> paramMap){
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
            if(paramMap == null || paramMap.isEmpty()){
                returnSql=returnSql.replace(paramList.get(i), "");
            }else{
                Matcher wordMatcher=wordPattern.matcher(matchStr);
                while(wordMatcher.find()){
                    String matchWord=wordMatcher.group(1);
                    if(StringUtils.isEmpty(paramMap.get(matchWord))){
                        returnSql=returnSql.replace(paramList.get(i), "");
                    }else{
                        returnSql=returnSql.replace(split+matchWord, "'"+paramMap.get(matchWord).toString()+"'");
                    }
                }
            }
        }
//        Matcher optMatcher=optPattern.matcher(returnSql);//操作符变量替换
//        while(optMatcher.find()){
//            String matchWord=optMatcher.group(1);
//            returnSql=returnSql.replace(split+matchWord, paramMap.get(matchWord).toString());
//        }
        returnSql=returnSql.replaceAll("\\{", "");
        returnSql=returnSql.replaceAll("\\}", "");
        return returnSql;
    }

    public static String businessKeyToSql(String businessKey){
        String array[] = businessKey.split("\\.");
        String sql = "delete from " + array[0] + " where " + array[1] ;
        return sql;
    }
}
