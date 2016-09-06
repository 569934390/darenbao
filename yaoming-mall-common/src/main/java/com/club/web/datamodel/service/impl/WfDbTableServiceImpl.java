package com.club.web.datamodel.service.impl;


import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.club.framework.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.core.convert.IArgConversionService;
import com.club.core.db.dao.BaseDao;
import com.club.core.idproduce.ISequenceGenerator;
import com.club.web.datamodel.db.arg.WfDbColumnsArg;
import com.club.web.datamodel.db.arg.WfDbTableArg;
import com.club.web.datamodel.db.dao.WfDbColumnsDao;
import com.club.web.datamodel.db.dao.WfDbTableDao;
import com.club.web.datamodel.db.po.WfDbTablePO;
import com.club.web.datamodel.service.IWfDbTableService;
import com.club.web.datasource.db.dao.WfDataSetDao;
import com.club.web.datasource.db.po.WfDataSetPO;
import com.club.framework.exception.BaseAppException;
import com.club.framework.log.ClubLogManager;
import com.club.framework.util.DBUtils;
import com.club.framework.util.StringUtils;
import com.club.web.common.cache.DBMetaCache;
import com.club.web.common.service.IBaseService;
import com.club.web.common.vo.DBMeta;
import com.club.web.common.vo.DBTable;
import scala.util.parsing.combinator.testing.Str;

/**
 * <Description> <br>
 * 
 * @author codeCreater<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2014年11月11日 <br>
 * @since V1.0<br>
 * @see com.club.web.datamodel.service.impl <br>
 */

@Service("wfDbTableService")
public class WfDbTableServiceImpl implements IWfDbTableService {

    private static final ClubLogManager logger = ClubLogManager
            .getLogger(WfDbTableServiceImpl.class);

    @Autowired
    private WfDbTableDao wfDbTableDao;
    @Autowired
    private WfDbColumnsDao wfDbColumnsDao;
    @Autowired
    private IBaseService baseService ;
    @Autowired
    private BaseDao baseDao;
    @Autowired
    private WfDataSetDao wfDataSetDao;
    /**
     * 查询条件转换成Arg类的服务接口
     */
    @Resource(name = "defaultArgConversionService")
    private IArgConversionService argConversionService;

    /**
     * 主键生成器
     */
    @Resource(name = "sequenceProcGenerator")
    private ISequenceGenerator sequenceGenerator;
    

    @Override
    public WfDbTablePO selectByPrimaryKey(Integer key) throws BaseAppException {
        return wfDbTableDao.selectByPrimaryKey(key);
    }

    @Override
    public List<WfDbTablePO> selectByArg(WfDbTablePO record) throws BaseAppException {
        logger.debug("selectByArg begin...record={0}", record);

        // 第一种方式：自己创建arg，自行设置查询条件及操作符
        WfDbTableArg arg = new WfDbTableArg();
        WfDbTableArg.WfDbTableCriteria criteria = arg.createCriteria();
        if(StringUtils.isNotEmpty(record.getTableName())) {
            criteria.andTableNameEqualTo(record.getTableName());
        }
        // 第二种方式：利用arg转换服务，转换出arg，带上查询条件及操作符，
        // 转换后，还可以自行对arg进行设置修改
        //WfDbTableArg arg = argConversionService.invokeArg(WfDbTableArg.class, record);

        // ///////
        // 根据业务场景，设置查询条件，示例
        // if (Utils.notEmpty(record.getUserName())) {
        // criteria.andUserNameLike(record.getUserName());
        // }
        // ///////

        return wfDbTableDao.selectByArg(arg);
    }

    @Override
    public Page<WfDbTablePO> selectByArgAndPage(WfDbTablePO record, Page<WfDbTablePO> resultPage)
            throws BaseAppException {
        logger.debug("selectByArgAndPage begin...record={0}", record);

        // 第一种方式：自己创建arg，自行设置查询条件及操作符
        // WfDbTableArg arg = new WfDbTableArg();
        //  根据业务场景，设置查询条件，示例
        // WfDbTableCriteria criteria = arg.createCriteria();
        // if (Utils.notEmpty(record.getUserName())) {
        // criteria.andUserNameLike(record.getUserName());
        // }

        // 第二种方式：利用arg转换服务，转换出arg，带上查询条件及操作符，
        // 转换后，还可以自行对arg进行设置修改
        WfDbTableArg arg = argConversionService.invokeArg(WfDbTableArg.class, record);

        resultPage = wfDbTableDao.selectByArgAndPage(arg, resultPage);


        return resultPage;
    }
    
    @Override
    public List<Object> getDataListByIdAndColumns
    (String name, String columns)  throws BaseAppException {
        logger.debug("getData begin...dataSetId={0}", name+columns);
        List<Object> listObj = new ArrayList<Object>();
        String sql = this.getDataSetSql(name,true);
        String sqlNew = "select ";
        String columnMessage = "";
        if(StringUtils.isNotEmpty(columns)){
            String columnArray[] = columns.split(",");
            for(int i=0;i<columnArray.length;i++) {
                 columnMessage = columnMessage  + ",t."  +columnArray[i];
            }
            columnMessage = columnMessage.substring(1);
        }else{
        	columnMessage = " t.* ";
        }
        sqlNew = sqlNew + columnMessage + " from (" + sql + ") t";
        Map<String,Object> conditions = new HashMap<String, Object>();
        conditions.put("sql", DBUtils.getCountSql(sql));
        Map<String,Object> r = baseDao.selectOne(conditions);
        int count = Integer.parseInt(r.get("count").toString());
        conditions.put("sql",sqlNew);
        conditions.put("start",0);
        conditions.put("limit",count);
        List<Map<String,Object>> result = baseService.selectPage(conditions, true).getResultList();
        if(result.size()>0&&result!=null){
        	for (Map<String, Object> map : result) {
        		listObj.add(map.get(columns));
        	}
        }
        return listObj;
    }

    @Override
    public Page<Map<String,Object>> getDataListByIdAndColumns
            (String name, String columns, Page<Map<String,Object>> resultPage)  throws BaseAppException {
        logger.debug("getData begin...dataSetId={0}", name+columns);
        String sql = this.getDataSetSql(name,true);
        if(StringUtils.isEmpty(sql)){
            return resultPage;
        }
        String sqlNew = "select ";
        String columnMessage = "";
        if(StringUtils.isNotEmpty(columns)){
            String columnArray[] = columns.split(",");
            for(int i=0;i<columnArray.length;i++) {
                columnMessage = columnMessage  + ",t."  +columnArray[i]+" as "+columnArray[i];
            }
            columnMessage = columnMessage.substring(1);
        }else{
            columnMessage = " t.* ";
        }
        sqlNew = sqlNew + columnMessage + " from (" + sql + ") t";
        Map<String,Object> conditions = new HashMap<String, Object>();
        conditions.put("sql",sqlNew);
        conditions.put("start",resultPage.getStart());
        conditions.put("limit",resultPage.getLimit());
        resultPage = baseService.selectPage(conditions, true);
        return resultPage;
    }

    @Override
    public Page<Map<String,Object>> getData(Integer dataSetId, Page<Map<String,Object>> resultPage,HttpServletResponse response)
            throws BaseAppException {
        logger.debug("getData begin...dataSetId={0}", dataSetId);

        Map<String,Object> conditions = new HashMap<String, Object>();
        conditions.put("sql",this.getDataSetSql(dataSetId, false));
        conditions.put("start",resultPage.getStart());
        conditions.put("limit",resultPage.getLimit());
        Page<Map<String, Object>> result = baseService.selectPage(conditions, true);
        return result;
    }

    @Override
    public Page<Map<String,Object>> getData(String name, Page<Map<String,Object>> resultPage,HttpServletResponse response)
            throws BaseAppException {
    	
       
		logger.debug("getData begin...dataSetId={0}", name);
        Page<Map<String, Object>> result = baseService.selectPage(buildConditions(name, resultPage, response), true);
        return result;
    }

    @Override
    public Page<Map<String,Object>> getDataNoHump(String name, Page<Map<String,Object>> resultPage,HttpServletResponse response)
            throws BaseAppException {
        logger.debug("getDataNoHump begin...dataSetId={0}", name);
        Page<Map<String, Object>> result = baseService.selectPage(buildConditions(name, resultPage, response), false);
        return result;
    }

    private Map<String,Object> buildConditions (String name, Page<Map<String,Object>> resultPage, HttpServletResponse response)  throws BaseAppException{
        String resultStr = getDataSetSql(name, true);
        if(resultStr.indexOf("http://") > -1 || resultStr.indexOf("https://") > -1){
            try {
                response.sendRedirect(resultStr);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        resultPage.getConditons().put("sql", this.getDataSetSql(name, true));
        resultPage.getConditons().put("start", resultPage.getStart());
        resultPage.getConditons().put("limit", resultPage.getLimit());
        return resultPage.getConditons();
    }
    /**
    * nameOrId false - 使用name  true - 使用dataSetId
     * dataSetIdOrName name 或者dataSetId 的值
    * */
    @Override
    public String getDataSetSql(Object dataSetIdOrName,boolean nameOrId) throws BaseAppException {
        WfDataSetPO dataSetPO = null ;
        if(nameOrId){
            Map<String,Object> searchMap = new HashMap<String, Object>();
            searchMap.put("name", (String) dataSetIdOrName);
            searchMap.put("status", "0");
            List<WfDataSetPO> dataSetPOs = wfDataSetDao.selectByMap(searchMap);
            if(dataSetPOs != null && dataSetPOs.size() > 0) {
                dataSetPO = dataSetPOs.get(0);
            }else{
                return  "";
            }
        }else{
            dataSetPO = wfDataSetDao.selectByPrimaryKey((Integer)dataSetIdOrName);
            if(dataSetPO == null){
                return "" ;
            }
        }
        String sql = "" ; //最终结果
        String columnMessage = "" ;//列字符串
        String tableMessage = "" ; //表名及left join on
        if("1".equals(dataSetPO.getDataSetType())){ //数据表配置数据源
            //展示列
            String columns = dataSetPO.getColumns();
            String displayColumns = dataSetPO.getDisplayColumns();
            if(StringUtils.isNotEmpty(columns)){
                String columnArray[] = columns.split(",");
                String displayColumnArray[] = displayColumns.split(",");
                for(int i=0;i<columnArray.length;i++) {
                    /*if(columnArray[i].equals(displayColumnArray[i])){
                        columnMessage = columnMessage + "," +columnArray[i] ;
                    }else{*/
                        columnMessage = columnMessage  + ","  +columnArray[i] +" as '" + displayColumnArray[i] +"'";
                    //}
                }
                columnMessage = columnMessage.substring(1);
            }
            //表条件
            String tables = dataSetPO.getTableGroup();
            String conditions = dataSetPO.getJoinGroup();
            String onConditions = dataSetPO.getOnGroup();
            String whereParams = dataSetPO.getWhereParams();
            if (whereParams!=null&&whereParams.length()>0){
                whereParams = " where "+whereParams;
            }

            if(StringUtils.isNotEmpty(tables)) {
                String tableArray[] = tables.split(",");
                String conditionArray[] = conditions.split(",");
                String onConditionArray[] = onConditions.split(",");
                if(tables.indexOf(",") == -1){ //一张表
                    tableMessage = tables ;
                }else{
                    tableMessage = tableArray[0] + " ";
                    for(int j=0;j<conditionArray.length;j++) {
                        tableMessage = tableMessage + " "+ conditionArray[j] + " " + tableArray[j+1] + " on " + onConditionArray[j] +" ";
                    }
                }
            }
            if (columnMessage.length()==0) columnMessage = "*";
            sql = "select " + columnMessage + " from  " + tableMessage + whereParams;
        }else{
            sql = dataSetPO.getDataUrl();
        }
        return sql;
    }

    @Override
    public int add(WfDbTablePO record) throws BaseAppException {
        logger.debug("add begin...record={0}", record);

        // ///////
        //  根据业务场景，进行重名校验、设置主键、设置属性默认值等
        // 获取主键
        // int pkId = sequenceGenerator.sequenceIntValue("表名","主键名");
        // record.setUserId(pkId);
        // record.setCreatedDate(new Date());
        // ///////

        return wfDbTableDao.insertSelective(record);
    }

    @Override
    public int update(WfDbTablePO record) throws BaseAppException {
        logger.debug("update begin...record={0}", record);

        // ///////
        //  根据业务场景，进行重名校验、数据有效性校验、设置属性默认值等

        // ///////

        return wfDbTableDao.updateByPrimaryKeySelective(record);
    }


    @Override
    public int delete(WfDbTablePO record) throws BaseAppException {
        logger.debug("delete begin...record={0}", record);

        WfDbColumnsArg arg = new WfDbColumnsArg();
        WfDbColumnsArg.WfDbColumnsCriteria criteria = arg.createCriteria();
        criteria.andTableNameEqualTo(record.getTableName());
        wfDbColumnsDao.deleteByArg(arg);
        wfDbTableDao.deleteByPrimaryKey(record.getTableName());
        Map<String,Object> insertMap = new HashMap<String, Object>();
        insertMap.put("sql", "drop table if exists " + record.getTableName() + " ;");
        baseDao.update(insertMap);
        insertMap.put("sql", "delete from init_id where schema_name='"+record.getDbName().toUpperCase()+"' and table_name='"+record.getTableName().toUpperCase()+"';");
        baseDao.update(insertMap);
        DBMeta cacheMeta = DBMetaCache.getMeta();
        List<DBTable> cacheMetaTables = cacheMeta.getTables();
        if(cacheMetaTables != null && cacheMetaTables.size() > 0){
            String left = "" ,right = "";
            right = record.getTableName();
            for(DBTable dbTablePo2 : cacheMetaTables){
                left = dbTablePo2.getTableName() ;
                if(left.equals(right)){
                    cacheMetaTables.remove(dbTablePo2);
                    break;
                }
            }
            cacheMeta.setTables(cacheMetaTables);
        }
        DBMetaCache.setMeta(cacheMeta);
        return 9;
    }

}
