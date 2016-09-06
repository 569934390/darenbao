/**
 *
 */
package com.club.web.common.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.core.common.QueryCondition;
import com.club.core.common.QueryCondition.ConditionOperation;
import com.club.core.common.TreeNode;
import com.club.core.common.TreePO;
import com.club.core.common.TreeQueryPO;
import com.club.core.common.TreeSortComparator;
import com.club.core.db.dao.BaseDao;
import com.club.core.idproduce.ISequenceGenerator;
import com.club.framework.exception.BaseAppException;
import com.club.framework.log.ClubLogManager;
import com.club.framework.util.BeanUtils;
import com.club.framework.util.DBUtils;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;
import com.club.framework.util.Utils;
import com.club.framework.util.ValidformUtils;
import com.club.web.common.cache.DBMetaCache;
import com.club.web.common.db.arg.WfDbColumnsArg;
import com.club.web.common.db.arg.WfDbMetaArg;
import com.club.web.common.db.arg.WfDbTableArg;
import com.club.web.common.db.dao.WfDbColumnsDao;
import com.club.web.common.db.dao.WfDbMetaDao;
import com.club.web.common.db.dao.WfDbTableDao;
import com.club.web.common.db.po.WfDbColumnsPO;
import com.club.web.common.db.po.WfDbMetaPO;
import com.club.web.common.db.po.WfDbTablePO;
import com.club.web.common.service.IBaseService;
import com.club.web.common.vo.DBColumn;
import com.club.web.common.vo.DBMeta;
import com.club.web.common.vo.DBTable;


@Service
public class BaseServiceImpl implements IBaseService {

    private static final ClubLogManager logger = ClubLogManager
            .getLogger(BaseServiceImpl.class);

    @Autowired
    private BaseDao baseDao;
    @Autowired
    private WfDbMetaDao dbMetaDao;
    @Autowired
    private WfDbTableDao dbTableDao;
    @Autowired
    private WfDbColumnsDao dbColumnDao;
    /**
     * 主键生成器
     */
    @Resource(name = "sequenceProcGenerator")
    private ISequenceGenerator sequenceGenerator;

    @Override
    public List<Map<String,Object>> selectList(String tableName,Map<String,Object> conditions) throws BaseAppException {
        return this.selectList(conditions, DBUtils.getSelectListSql(tableName.toUpperCase(), conditions.keySet()));
    }

    @Override
    public List<Map<String,Object>> selectList(Map<String,Object> conditions,String sql) throws BaseAppException {
        conditions.put("sql", sql);
        return baseDao.selectList(conditions);
    }

    @Override
    public List<Map<String,Object>> selectList(String sql) throws BaseAppException {
        return this.selectList(new HashMap<>(), sql);
    }



    @Override
    public Page<Map<String,Object>> selectPage(String tableName,Map<String,Object> conditions) throws BaseAppException {
        String sql = DBUtils.getSelectListSql(tableName.toUpperCase(), conditions.keySet());
        if (conditions!=null&&!conditions.containsKey("start")){
            conditions.put("start","0");
        }
        if (conditions!=null&&!conditions.containsKey("limit")){
            conditions.put("limit","50");
        }
        conditions.put("sql", sql);
        return selectPage(conditions, true);
    }

    @Override
    public Page<Map<String,Object>> selectPage(Map<String,Object> conditions, boolean isHump) throws BaseAppException {
        //isHump 是否转换下划线列名为驼峰规则
        Page<Map<String,Object>> page = new Page<Map<String,Object>>();
        page.setLimit(Integer.parseInt(conditions.get("limit").toString()));
        page.setStart(Integer.parseInt(conditions.get("start").toString()));
        String sql = conditions.get("sql").toString();
        if(Utils.isEmpty(sql)){
            return null;
        }
    	conditions.put("sql", DBUtils.getPageSql(sql, page));
        if(isHump) {
            page.setResultList(StringUtils.toHump(baseDao.selectList(conditions)));
        }else{
            page.setResultList(baseDao.selectList(conditions));
        }
        conditions.put("sql", DBUtils.getCountSql(sql));
        Map<String,Object> r = baseDao.selectOne(conditions);
        page.setTotalRecords(Integer.parseInt(r.get("count").toString()));
        return page;
    }

    @Override
    public List<Map<String, Object>> selectList2(String sqlKey,Map<String,Object> paramMap){
        logger.debug("load table {0},by conditions[{1}]",sqlKey,paramMap);
        return baseDao.selectList(sqlKey,paramMap);
    }

    public boolean testDBConnection(String driver,String dbName,String characterEncoding,String ip,String port,String params,String username,String password) throws BaseAppException{
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

	public boolean saveDBMeta(String driver,String dbName,String characterEncoding,String ip,String port,String params,String username,String password) throws BaseAppException{
		StringBuffer url = new StringBuffer("jdbc:");
		if(driver.toLowerCase().contains("mysql")){
			url.append("mysql://").append(ip).append(":").append(port).append("/").append(dbName).append("?characterEncoding=").append(characterEncoding)
			.append("&").append(params);
		}
		else{
			url.append("oracle:thin:@").append(ip).append(":").append(port).append(":").append(dbName);
		}

		return saveDBMeta(driver, url.toString(), username, password);
	}

	public boolean saveDBMeta(String driver,String url,String username,String password) throws BaseAppException{

        logger.info("auto flush table caches by driver:"+driver+",url:"+url+",username:"+username+",password:"+password);

		DBMeta meta =  DBUtils.loadDBMeta(driver, url.toString(), username, password);

        WfDbTableArg tableArg = new WfDbTableArg();
        tableArg.createCriteria().andSourceEqualTo("USR");

        List<WfDbTablePO> tablePOss = dbTableDao.selectByArg(tableArg);
        Map<String,String> tableKey = new HashMap<String, String>();

        for(WfDbTablePO tablePO : tablePOss) {
            DBTable table = DBTable.parse(tablePO);
            tableKey.put(table.getTableName().toLowerCase(),table.getSource());
        }

        for(DBTable t : meta.getTables()){
            if(tableKey.containsKey(t.getTableName().toLowerCase())){
                t.setSource(tableKey.get(t.getTableName()));
            }
            else {
                t.setSource("SYS");
            }
            for(DBColumn column : t.getColumns()){
                t.getColumnMap().put(column.getColumnName(),column);
                t.getColumnMap().put(column.getColumnName().toUpperCase(),column);
                t.getColumnMap().put(StringUtils.toHump(column.getColumnName()), column);
                column.setTableName(t.getTableName());
            }
            t.setDbName(meta.getDbName());
        }
        DBMetaCache.setMeta(meta);//更新缓存

		WfDbMetaPO metaPo = new WfDbMetaPO();
		meta.convert(metaPo);
        dbColumnDao.deleteByArg(new WfDbColumnsArg());
        dbTableDao.deleteByArg(new WfDbTableArg());
		dbMetaDao.deleteByArg(new WfDbMetaArg());
		dbMetaDao.insert(metaPo);
		List<WfDbTablePO> tables = new ArrayList<WfDbTablePO>();
		List<WfDbColumnsPO> columns = new ArrayList<WfDbColumnsPO>();
		for(DBTable table : meta.getTables()){
            WfDbTablePO tablePo = new WfDbTablePO();
			table.convert(tablePo);
            tablePo.setModifyTime(new Date());

			tablePo.setDbName(metaPo.getDbName());
			tables.add(tablePo);
			for(DBColumn column : table.getColumns()){
				WfDbColumnsPO columnPo = new WfDbColumnsPO();
				column.convert(columnPo);
				columnPo.setTableName(tablePo.getTableName());
				columns.add(columnPo);
			}
		}
		dbTableDao.insertBatch(tables);
		dbColumnDao.insertBatch(columns);
		return true;
	}


	public TreeNode getTreeAllData(TreePO params,Map<String,Object> obj,int path){
		path++;
		Integer deep = params.getDeep();
		String checked = params.getChecked();
		String[] icons = params.getIcons();
		TreeNode node = new TreeNode();
		node.setText(obj.get(params.getDisplayField())+"");
		node.setId(obj.get(params.getValueField())+"");
		if(!StringUtils.isEmpty(params.getCheckField())){
		    boolean checkField = false;
		    if(!StringUtils.isEmpty(obj.get(params.getCheckField()))){
		        checkField = true;
		    }
		    obj.put("fieldChecked", checkField);
		}
		node.setAttributeMap(obj);
		//��ѯ��������
        String relatePropertyKey = params.getRelatePropertyKey();
        if(!StringUtils.isEmpty(relatePropertyKey)){
            Map<String, Object> req = new HashMap<String, Object>();
            req.put("relateId", node.getId());
            List<Map<String, Object>> fields = baseDao.selectList(relatePropertyKey, req);
            if(!StringUtils.isEmpty(fields)){
                node.getAttributeMap().put("relateProperty", fields);
            }
        }
		if(path>=deep){
			if(obj.containsKey("expandable")){
				node.setExpandable(Boolean.parseBoolean(obj.get("expandable")+""));
			}
			else{
				node.setExpandable(true);
			}
			node.setExpanded(false);
		}
		else{
			List<TreeNode> childs = new ArrayList<TreeNode>();
			node.setChildren(childs);
			Map<String,Object> req = null;
			if(!StringUtils.isEmpty(params.getParamData())){
			    req = new HashMap<String, Object>();
			    req.putAll(obj);
			    req.putAll(params.getParamData());
			}else{
			    req = obj;
	        }
			// 对数据管理大师做特殊处理
			// isCleaner中有值（true），说明是数据管理大师传来的
			List<Map<String,Object>> fields;

			fields = baseDao.selectList(params.getSqlKey(), req);
			if(fields.size()==0){
				node.setLeaf(true);
			}
			else{
				node.setExpandable(true);
				node.setExpanded(true);
			}
			for(Map<String,Object> field : fields){
				childs.add(getTreeAllData(params, field,path));
			}
		}
		if(!StringUtils.isEmpty(checked)){
			if(checked.indexOf(",")!=-1){
				for(String d : checked.split(",")){
					if(path==Integer.parseInt(d))
						node.setChecked(false);
				}
			}
			else{
				node.setChecked(false);
			}
		}
		if(icons.length>path){
			node.setIcon(icons[path]);
		}
		if(obj.containsKey("icon")&&!StringUtils.isEmpty(obj.get("icon"))){
			node.setIcon(obj.get("icon").toString());
		}else if(!StringUtils.isEmpty(params.getIcon())){
		    node.setIcon(params.getIcon());
		}
		return node;
	}

	public TreeNode queryTree(TreeQueryPO reqDto){
        Map<String,List<Map<String,Object>>> parents = new HashMap<String, List<Map<String,Object>>>();
        List<String> ids = new ArrayList<String>();
        String queryConditions = reqDto.getQueryConditions();
        List<QueryCondition> queryConditionsList = JsonUtil.toList(queryConditions, QueryCondition.class);
        Map<String,Object> conditions = DBUtils.getSelectListSql(reqDto.getTableName(), queryConditionsList);
        TreeSortComparator comparator = null;
        String sql = (String) conditions.get("sql");
//        if(!StringUtils.isEmpty(reqDto.getGroupByClause())){
//            sql += " group by " + reqDto.getGroupByClause();
//        }
        String orderByClause = reqDto.getOrderByClause();
        if(!StringUtils.isEmpty(orderByClause)){
            sql += " order by " + orderByClause;
            comparator = new TreeSortComparator();
            Map<String,Boolean> orderByClauses = comparator.getOrderByClauses();
            String[] orders = orderByClause.split(",");
            for (int i = 0; i < orders.length; i++) {
                String[] order = orders[i].trim().split("\\s+");
                String key = StringUtils.toHump(order[0]);
                boolean value = false;
                if(order.length == 2 && order[1].toLowerCase().equals("desc")){
                    value = true;
                }
                orderByClauses.put(key, value);
            }
        }
        conditions.put("sql", sql);
        List<Map<String,Object>> datas = baseDao.selectList(conditions);
        List<String> pks = new ArrayList<String>();
        setParentDatas(datas, parents, ids,pks, reqDto,true);
        if(ids.size() > 0){
            queryParents(parents, ids,pks, reqDto);
        }
        TreeNode node = new TreeNode();
        node.setId(reqDto.getRootId());
        node.setText(reqDto.getRootText());
        node.setLeaf(true);
        if(!StringUtils.isEmpty(reqDto.getIcon())){
            node.setIcon(reqDto.getIcon());
        }
        if(parents.size() > 0){
            node.setLeaf(false);
            setChildren(node, parents, reqDto,comparator);
            return node;
        }
	    return node;
	}

    public void queryParents(Map<String,List<Map<String,Object>>> parents,List<String> ids,List<String> pks,TreeQueryPO reqDto) {
        QueryCondition condition = new QueryCondition();
        condition.setParamName(reqDto.getValueField());
        condition.setParamValue(ids.toArray(new String[0]));
        condition.setOperation(ConditionOperation.In);
        List<QueryCondition> queryConditionsList = new ArrayList<QueryCondition>(1);
        queryConditionsList.add(condition);
        Map<String,Object> conditions = DBUtils.getSelectListSql(reqDto.getTableName(), queryConditionsList);
        List<Map<String,Object>> datas = baseDao.selectList(conditions);
        ids.clear();
        setParentDatas(datas, parents, ids,pks, reqDto,false);
        if(ids.size() > 0){
            queryParents(parents, ids,pks,reqDto);
        }
    }

    private void setChildren(TreeNode node,Map<String,List<Map<String,Object>>> datas,TreeQueryPO reqDto,TreeSortComparator comparator){
        String id = node.getId();
        List<Map<String,Object>> children = datas.get(id);
        if(children == null || children.size() == 0){
            node.setExpandable(false);
            node.setLeaf(true);
            return;
        }
        List<TreeNode> childs = new ArrayList<TreeNode>();
        if(comparator != null){
            Collections.sort(children, comparator);
        }
        int size = children.size();
        for (int i = 0; i < size; i++) {
            Map<String,Object> data = children.get(i);
            TreeNode nodeTemp = new TreeNode();
            nodeTemp.setId(data.get(reqDto.getValueField()) + "");
            nodeTemp.setText(data.get(reqDto.getDisplayField()) + "");
            nodeTemp.setAttributeMap(data);
            if(!StringUtils.isEmpty(reqDto.getIcon())){
                nodeTemp.setIcon(reqDto.getIcon());
            }
            setChildren(nodeTemp, datas,reqDto,comparator);
            childs.add(nodeTemp);
        }
        node.setChildren(childs);
    }

    private void setParentDatas(List<Map<String,Object>> datas,Map<String,List<Map<String,Object>>> parents,List<String> ids,List<String> pks,TreeQueryPO reqDto,boolean isFirst){
        for (int i = 0; i < datas.size(); i++) {
            Map<String,Object> data = datas.get(i);
            String id = data.get(reqDto.getParentField()).toString();
            if(isFirst){
                String pk = data.get(reqDto.getValueField()).toString();
                pks.add(pk);
            }
            if(parents.containsKey(id)){
                parents.get(id).add(data);
            }else{
                List<Map<String,Object>> temp = new ArrayList<Map<String,Object>>();
                temp.add(data);
                parents.put(id, temp);
                if(!id .equals(reqDto.getRootId())){
                    ids.add(id);
                }
            }
        }
        //由于第一次查询时有可能会同时查到父节点和子节点，因此需要把已经找到的父节点去掉
        if(ids.size() > 0){
            for (int i = 0; i < pks.size(); i++) {
                String pk = pks.get(i);
                if(ids.contains(pk)){
                    ids.remove(pk);
                }
            }
        }
    }

    public Map<String,Map<String, Object>> convertTables(String tableColumnKey){
        String[] keys = tableColumnKey.split(",");
        Map<String,Map<String, Object>> tables = new HashMap<String, Map<String, Object>>();
        for(String key : keys){
            String[] keyValue = key.split("=");
            String[] tableColumn = keyValue[0].split("\\.");
            String tableName = tableColumn[0];
            String columnName = StringUtils.toHump(tableColumn[1]);
            String value = keyValue[1];
            if (!tables.containsKey(tableName)){
                tables.put(tableName,new HashMap<String,Object>());
            }
            tables.get(tableName).put(columnName,value);
        }
        return tables;
    }
    public void delete(String tableColumnKey) throws BaseAppException {

        Map<String,Map<String, Object>> tables = convertTables(tableColumnKey);

        for(String tableName : tables.keySet()) {
            String sql = DBUtils.getDeleteSql(tableName);
            tables.get(tableName).put("sql", sql);
            baseDao.delete(tables.get(tableName));
        }
    }

    @Override
    public Map<String, Object> selectOne(String tableColumnKey) throws BaseAppException {
        Map<String,Object> result = new HashMap<String, Object>();
        Map<String,Map<String, Object>> tables = convertTables(tableColumnKey);
        for(String tableName : tables.keySet()){
            String sql = DBUtils.getSelectListSql(tableName, tables.get(tableName));
            tables.get(tableName).put("sql", sql);
            Map<String,Object> r = baseDao.selectOne(tables.get(tableName));
            for (String column : r.keySet()) {
                result.put(tableName+"."+column,r.get(column));
            }
        }

        return result;
    }

    @Override
     public Map<String, Object> selectOne(String tableName, Map<String, Object> conditions) throws BaseAppException {
        return this.selectOne(conditions, DBUtils.getSelectListSql(tableName.toUpperCase(), conditions.keySet()));
    }

    @Override
    public Map<String,Object> selectOneBySql(String sql) throws BaseAppException {
        return this.selectOne(new HashMap<>(), sql);
    }
    
    
    @Override
    public Map<String, Object> selectOne(Map<String, Object> conditions,String sql) throws BaseAppException {
    	if(conditions==null)
    		conditions = new HashMap<>();
        conditions.put("sql", sql);
        List<Map<String, Object>> list = baseDao.selectList(conditions);
        if (list.size()>0)
            return list.get(0);
        else
            return null;
    }

    @Override
    public Map<String, Object> selectHumOne( Map<String, Object> conditions,String sql) throws BaseAppException {
        return StringUtils.toHump(selectOne(conditions,sql));
    }

    @Override
    public <T> T selectOne(T record) throws BaseAppException {
        logger.debug("load Record {0}", record);
        Map<String,Object> keyValues = DBUtils.getSelectOneSql(record);
        Map<String,Object> result = baseDao.selectOne(keyValues);
        if(result==null||result.keySet().size()==0)return null;
        return (T)BeanUtils.map2Bean(result,record.getClass());
    }
    @Override
    public <T> int delete(T record) throws BaseAppException {
        logger.debug("load Record {0}", record);
        Map<String,Object> keyValues = DBUtils.getDeleteSql(record);
        return baseDao.delete(keyValues);
    }

    public <T> T insert(T record) throws BaseAppException {
        Map<String,Object> keyValues = DBUtils.getInsertSql(record);
        setSequenceGenerator(keyValues,DBUtils.getTalbePK(record),DBUtils.getTalbe(record));
        keyValues = StringUtils.toHump(keyValues);
        baseDao.insert(keyValues);
        return (T)BeanUtils.map2Bean(keyValues,record.getClass());
    }

    private Map<String,Object> setSequenceGenerator(Map<String,Object> keyValues,List<DBColumn> pks,DBTable table) throws BaseAppException{
        return this.setSequenceGenerator(keyValues,pks,table,false);
    }

    private Map<String,Object> setSequenceGenerator(Map<String,Object> keyValues,List<DBColumn> pks,DBTable table,boolean auto) throws BaseAppException {
        Map<String,Object> ps = new HashMap<String, Object>();
        for(DBColumn column : pks){
            String columnName = DBUtils.getColumnName(keyValues, column.getColumnName());
            if (column.getDbType().toUpperCase().indexOf("CHAR")!=-1||column.getDbType().toUpperCase().equals("LONG")||column.getDbType().toUpperCase().equals("INT")||column.getDbType().toUpperCase().equals("BIGINT")){
                if(columnName!=null) {
                    Object pk = keyValues.get(columnName);
                    if(pk instanceof String&&pk.equals("auto")||auto){
                        int _pk = sequenceGenerator.sequenceIntValue(column.getTableName().toUpperCase(), column.getColumnName().toUpperCase(), table.getDbName().toUpperCase());
                        keyValues.put(column.getColumnName(), _pk);
                        ps.put(column.getColumnName(),_pk);
                    }
                    else if ((pk instanceof Integer || pk instanceof Long)  && pk.equals(-1)) {
                        int _pk = sequenceGenerator.sequenceIntValue(column.getTableName().toUpperCase(), column.getColumnName().toUpperCase(), table.getDbName().toUpperCase());
                        keyValues.put(column.getColumnName(), _pk);
                        ps.put(column.getColumnName(),_pk);
                    }
                }
                else{
                    int pk = sequenceGenerator.sequenceIntValue(column.getTableName().toUpperCase(), column.getColumnName().toUpperCase(), table.getDbName().toUpperCase());
                    keyValues.put(column.getColumnName(), pk);
                    ps.put(column.getColumnName(),pk);
                }
            }else if(columnName!=null){
                ps.put(column.getColumnName(),keyValues.get(columnName));
            }
        }
        return ps;
    }

    public Map<String,Object> insert(String tableName,Map<String,Object> record) throws BaseAppException {
        return this.insert(tableName,record,false);
    }

    public Map<String,Object> insert(String tableName,Map<String,Object> record,boolean auto) throws BaseAppException {
        Map<String,Object> ids = setSequenceGenerator(record, DBUtils.getTalbePK(tableName), DBUtils.getTalbe(tableName),auto);
        String sql = DBUtils.getInsertTableSql(tableName,record);
        record.put("sql",sql);
        logger.info("insert general sql {0}", sql);
        baseDao.insert(record);
        return ids;
    }

    public String saveOrUpdate(Map<String,Object> record) throws  BaseAppException{

        StringBuffer pk = new StringBuffer();
        Map<String,Map<String, Object>> records = new HashMap<String, Map<String, Object>>();
        for (String key : record.keySet()){
            if (key.indexOf(".")==-1){
                throw new RuntimeException("表名、字段名不匹配:"+key);
            }
            String[] ks = key.split("\\.");
            if (ks.length<2){
                throw new RuntimeException("字段不许为空:"+key);
            }
            for (int i = 0;i<ks.length;i++){
                ks[i] = ks[i].toUpperCase();
            }
            if(!records.containsKey(ks[0])){
                records.put(ks[0],new HashMap<String, Object>());
            }
            records.get(ks[0]).put(ks[1], record.get(key));

        }
        for (String key : records.keySet()){
            pk.append(this.saveOrUpdate(key, records.get(key))+",");
        }
        return pk.substring(0,pk.length()-1).toString();
    }

    public String save(Map<String,Object> record) throws  BaseAppException{

        StringBuffer pk = new StringBuffer();
        Map<String,Map<String, Object>> records = new HashMap<String, Map<String, Object>>();
        for (String key : record.keySet()){
            if (key.indexOf(".")==-1){
                throw new RuntimeException("表名、字段名不匹配:"+key);
            }
            String[] ks = key.split("\\.");
            if (ks.length<2){
                throw new RuntimeException("字段不许为空:"+key);
            }
            for (int i = 0;i<ks.length;i++){
                ks[i] = ks[i].toUpperCase();
            }
            if(!records.containsKey(ks[0])){
                records.put(ks[0],new HashMap<String, Object>());
            }
            records.get(ks[0]).put(ks[1], record.get(key));

        }
        StringBuffer pkStr = new StringBuffer();

        for (String key : records.keySet()){

            Map<String,Object> pks = this.insert(key, records.get(key), true);
            for (String id_ : pks.keySet()){
                pkStr.append(key+"."+id_+"="+pks.get(id_)+",");
            }
        }
        return pkStr.substring(0,pkStr.length()-1).toString();
    }

    /**
     * 表单设计器，拖拉表单测试
     * @param record
     * @return
     * @throws BaseAppException
     */
    public String testRun(Map<String,Object> record) throws  BaseAppException{
        String tableKey = saveOrUpdate(record);
        delete(tableKey);
        return tableKey;
    }


    public String saveOrUpdate(String tableName,Map<String,Object> record) throws  BaseAppException{
        ValidformUtils.validTable(tableName,record);
        List<DBColumn> pkColumns = DBUtils.getTalbePK(tableName);
        Map<String,Object> pks = new HashMap<String, Object>();
        for(DBColumn column : pkColumns){
            String columnName = DBUtils.getColumnName(record, column.getColumnName());
            if(columnName!=null){
                Object val = record.get(columnName);
                if(val!=null&&!val.equals("auto")&&!val.equals(-1)){
                    pks.put(column.getColumnName(), record.get(columnName));
                }
            }
        }
        if (pks.keySet().size()==pkColumns.size()){
            this.update(tableName, record);
        }
        else{
            pks = this.insert(tableName,record);
        }
        StringBuffer pkStr = new StringBuffer();
        for (String key : pks.keySet()){
            pkStr.append(tableName+"."+key+"="+pks.get(key)+",");
        }
        return pkStr.substring(0,pkStr.length()-1).toString();
    }

    public <T> int update(T record) throws BaseAppException {
        Map<String,Object> keyValues = DBUtils.getUpdateSql(record);
        return baseDao.update(keyValues);
    }

    public int update(String tableName,Map<String,Object> record) throws BaseAppException {
        String sql = DBUtils.getUpdateTableSql(tableName, record);
        record.put("sql",sql);
        logger.info("update general sql {0}", sql);
        return baseDao.update(record);
    }
}
