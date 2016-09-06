/**
 *
 */
package com.club.web.datamodel.service.impl;


import com.club.core.common.Page;
import com.club.core.convert.IArgConversionService;
import com.club.core.db.dao.BaseDao;
import com.club.core.idproduce.ISequenceGenerator;
import com.club.web.datamodel.db.arg.WfDbColumnsArg;
import com.club.web.datamodel.db.arg.WfDbTableArg;
import com.club.web.datamodel.db.dao.WfDbColumnsDao;
import com.club.web.datamodel.db.dao.WfDbTableDao;
import com.club.web.datamodel.db.po.WfDbColumnsPO;
import com.club.web.datamodel.db.po.WfDbTablePO;
import com.club.web.datamodel.service.IWfDbColumnsService;
import com.club.framework.exception.BaseAppException;
import com.club.framework.log.ClubLogManager;
import com.club.framework.util.DBUtils;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.Utils;
import com.club.web.common.cache.DBMetaCache;
import com.club.web.common.service.IBaseService;
import com.club.web.common.vo.DBColumn;
import com.club.web.common.vo.DBMeta;
import com.club.web.common.vo.DBTable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * <Description> <br>
 *
 * @author codeCreater<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2014年11月11日 <br>
 * @see com.club.web.datamodel.service.impl <br>
 * @since V1.0<br>
 */
@Service("wfDbColumnsService")
public class WfDbColumnsServiceImpl implements IWfDbColumnsService {

    private static final ClubLogManager logger = ClubLogManager
            .getLogger(WfDbColumnsServiceImpl.class);

    @Autowired
    private WfDbColumnsDao wfDbColumnsDao;
    @Autowired
    private WfDbTableDao wfDbTableDao;
    @Autowired
    private BaseDao baseDao;
    @Autowired
    private IBaseService baseService;

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
    public WfDbColumnsPO selectByPrimaryKey(String key) throws BaseAppException {
        return wfDbColumnsDao.selectByPrimaryKey(key);
    }

    @Override
    public List<WfDbColumnsPO> selectByArg(WfDbColumnsPO record) throws BaseAppException {
        logger.debug("selectByArg begin...record={0}", record);

        // 第一种方式：自己创建arg，自行设置查询条件及操作符
        //WfDbColumnsArg arg = new WfDbColumnsArg();
        //WfDbColumnsArg.WfDbColumnsCriteria criteria = arg.createCriteria();

        // 第二种方式：利用arg转换服务，转换出arg，带上查询条件及操作符，
        // 转换后，还可以自行对arg进行设置修改
        WfDbColumnsArg arg = argConversionService.invokeArg(WfDbColumnsArg.class, record);

        // ///////
        // TODO 根据业务场景，设置查询条件，示例
        // if (Utils.notEmpty(record.getUserName())) {
        // criteria.andUserNameLike(record.getUserName());
        // }
        // ///////

        return wfDbColumnsDao.selectByArg(arg);
    }

    @Override
    public Page<WfDbColumnsPO> selectByArgAndPage(WfDbColumnsPO record, Page<WfDbColumnsPO> resultPage)
            throws BaseAppException {
        logger.debug("selectByArgAndPage begin...record={0}", record);

        // 第一种方式：自己创建arg，自行设置查询条件及操作符
        // WfDbColumnsArg arg = new WfDbColumnsArg();
        // //TODO 根据业务场景，设置查询条件，示例
        // WfDbColumnsCriteria criteria = arg.createCriteria();
        // if (Utils.notEmpty(record.getUserName())) {
        // criteria.andUserNameLike(record.getUserName());
        // }

        // 第二种方式：利用arg转换服务，转换出arg，带上查询条件及操作符，
        // 转换后，还可以自行对arg进行设置修改
        WfDbColumnsArg arg = argConversionService.invokeArg(WfDbColumnsArg.class, record);

        resultPage = wfDbColumnsDao.selectByArgAndPage(arg, resultPage);


        return resultPage;
    }

    @Override
    public Page<WfDbColumnsPO> queryRecordByPageWithDbName(WfDbColumnsPO record, Page<WfDbColumnsPO> resultPage) throws BaseAppException {
        logger.debug("queryRecordByPageWithDbName begin...record={0}", record);
        WfDbColumnsArg arg = new WfDbColumnsArg();
        WfDbColumnsArg.WfDbColumnsCriteria criteria = arg.createCriteria();
        arg.setIsSearch(null);//判断是否需要添加查询条件
        if (Utils.notEmpty(record.getTableName())) {
            criteria.andTableNameEqualTo(record.getTableName());
            arg.setIsSearch("yes");
        }
        if (Utils.notEmpty(record.getColumnName())) {
            criteria.andColumnNameEqualTo(record.getColumnName());
            arg.setIsSearch("yes");
        }
        if (Utils.notEmpty(record.getDisplayName())) {
            criteria.andDisplayNameEqualTo(record.getDisplayName());
            arg.setIsSearch("yes");
        }
        arg.setDbNameFlag(record.getDbName());
        resultPage = wfDbColumnsDao.queryRecordByPageWithDbName(arg, resultPage);
        return resultPage;
    }

    @Override
    public int add(WfDbColumnsPO record) throws BaseAppException {
        logger.debug("add begin...record={0}", record);

        // ///////
        // TODO 根据业务场景，进行重名校验、设置主键、设置属性默认值等
        // 获取主键
        // int pkId = sequenceGenerator.sequenceIntValue("表名","主键名");
        // record.setUserId(pkId);
        // record.setCreatedDate(new Date());
        // ///////

        return wfDbColumnsDao.insertSelective(record);
    }

    @Override
    public int update(WfDbColumnsPO record) throws BaseAppException {
        logger.debug("update begin...record={0}", record);

        return wfDbColumnsDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int isExist(WfDbColumnsPO record) throws BaseAppException {
        logger.debug("update begin...record={0}", record);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tableName", record.getTableName().toUpperCase());
        List<WfDbTablePO> pos = wfDbTableDao.selectByMap(map);
        if (pos != null && pos.size() > 0) {
            return 3; //3-已存在该表
        }
        return 0;
    }

    @Override
    public WfDbColumnsPO convertWfDbColumnsPO(String content, DBTable dbTable) {
        WfDbColumnsPO po = JsonUtil.toBean(content, WfDbColumnsPO.class);
        if ("pk".equals(po.getType())) {
            dbTable.getPks().add(po.getColumnName().toUpperCase());
        } else if ("fk".equals(po.getType())) {
            dbTable.getFks().add(po.getColumnName().toUpperCase());
        }
        //转大写
        DBColumn dbColumn = JsonUtil.toBean(content, DBColumn.class);
        dbColumn.setTableName(dbColumn.getTableName().toUpperCase());
        dbColumn.setColumnName(dbColumn.getColumnName().toUpperCase());
        if (StringUtils.isEmpty(dbColumn.getLength() + "")) {
            dbColumn.setLength(Integer.parseInt(DBUtils.defaultLength(dbColumn.getDbType())));
        }
        dbTable.getColumns().add(dbColumn);
        po.setTableName(po.getTableName().toUpperCase());
        po.setColumnName(po.getColumnName().toUpperCase());
        DBUtils.defaultLength(po.getDbType());
        if (StringUtils.isEmpty(po.getLength() + "")) {
            po.setLength(Integer.parseInt(DBUtils.defaultLength(po.getDbType())));
        }
        return po;
    }

    @Override
    public void createTable(List<WfDbColumnsPO> columnsList, WfDbColumnsPO entity, WfDbColumnsPO record) throws Exception {
        String primaryKey = "", columnLength = "";
        Map<String, Object> insertMap = new HashMap<String, Object>();
        insertMap.put("sql", "drop table if exists " + entity.getTableName().toUpperCase() + ';');
        baseDao.update(insertMap);
        insertMap.put("sql", "drop table if exists " + record.getTableName().toUpperCase() + ';');
        baseDao.update(insertMap);
        //取新列对象 主要为获取新表表名
        List<com.club.web.common.db.po.WfDbColumnsPO> transPoList = new ArrayList<com.club.web.common.db.po.WfDbColumnsPO>();
        for (WfDbColumnsPO column : columnsList) {
            if ("pk".equals(column.getType()) && ("INT".equals(column.getDbType()) || "BIGINT".equals(column.getDbType()) || "TINYINT".equals(column.getDbType()))) {
                primaryKey = column.getColumnName().toUpperCase();
                if (column.getLength() == null || column.getLength() == 0) {
                    columnLength = DBUtils.defaultLength(column.getDbType());
                } else {
                    columnLength = column.getLength().toString();
                }
            }
            transPoList.add(convert(column));
        }
        String sql = DBUtils.generalSql(transPoList);
        insertMap.put("sql", sql);
        baseDao.update(insertMap);
        if (StringUtils.isNotBlank(primaryKey)) {
            insertMap.put("sql", "delete from init_id  where  table_name= '" + entity.getTableName().toUpperCase() + "' and schema_name='" + record.getDbName().toUpperCase() + "'; ");
            baseDao.update(insertMap);
            insertMap.put("sql", "delete from init_id  where  table_name= '" + record.getTableName().toUpperCase() + "' and schema_name='" + record.getDbName().toUpperCase() + "'; ");
            baseDao.update(insertMap);
            baseService.insert("INIT_ID", convertInitIdData(record, entity, primaryKey, columnLength));
        }
    }

    private com.club.web.common.db.po.WfDbColumnsPO convert(WfDbColumnsPO entity) {
        com.club.web.common.db.po.WfDbColumnsPO po = new com.club.web.common.db.po.WfDbColumnsPO();
        po.setColumnName(entity.getColumnName().toUpperCase());
        po.setDbType(entity.getDbType());
        po.setDefaultValue(entity.getDefaultValue());
        po.setDisplayName(entity.getDisplayName());
        po.setIsNull(entity.getIsNull());
        po.setTableName(entity.getTableName().toUpperCase());
        po.setType(entity.getType());
        po.setLength(entity.getLength());
        return po;
    }

    private Map<String, Object> convertInitIdData(WfDbColumnsPO record, WfDbColumnsPO entity, String primaryKey, String columnLength) {
        Map<String, Object> initEntityMap = new HashMap<String, Object>();
        initEntityMap.put("tableName", entity.getTableName().toUpperCase());
        initEntityMap.put("columnName", primaryKey.toUpperCase());
        initEntityMap.put("schemaName", record.getDbName().toUpperCase());
        initEntityMap.put("dataType", "1");
        initEntityMap.put("idLength", columnLength);
        initEntityMap.put("refCode", "1");
        initEntityMap.put("refType", "1");
        initEntityMap.put("currentValue", "0");
        initEntityMap.put("idStep", "1");
        return initEntityMap;
    }

    @Override
    public void saveTableAndColumns(List<WfDbColumnsPO> columns, WfDbColumnsPO record, DBTable dbTable) throws Exception {
        if (StringUtils.isNotBlank(record.getTableName())) {
            //先删除列数据
            WfDbColumnsArg arg = new WfDbColumnsArg();
            WfDbColumnsArg.WfDbColumnsCriteria criteria = arg.createCriteria();
            criteria.andTableNameEqualTo(record.getTableName());
            wfDbColumnsDao.deleteByArg(arg);
        }
        if (!record.getTableName().toUpperCase().trim().equals(dbTable.getTableName()) && StringUtils.isNotBlank(dbTable.getTableName())) {
            //先删除列数据
            WfDbColumnsArg arg = new WfDbColumnsArg();
            WfDbColumnsArg.WfDbColumnsCriteria criteria = arg.createCriteria();
            criteria.andTableNameEqualTo(dbTable.getTableName());
            wfDbColumnsDao.deleteByArg(arg);
        }
        getWfDbColumnsPOs(record, dbTable);
        wfDbColumnsDao.insertBatch(columns);
    }

    private void getWfDbColumnsPOs(WfDbColumnsPO record, DBTable dbTable) {
        Map<String, Object> map = new HashMap<String, Object>();
        String oldName = record.getTableName().toUpperCase();
        String newName = dbTable.getTableName();
        map.put("tableName", oldName);
        List<WfDbTablePO> pos = wfDbTableDao.selectByMap(map);
        if (!CollectionUtils.isEmpty(pos)) { //更新旧表的表名
            WfDbTablePO table = pos.get(0);
            String newRemarks = record.getDisplayName();//新表名备注
            if (!newName.equals(table.getTableName().toUpperCase()) || !newRemarks.equals(table.getRemarks())) {
                WfDbTableArg wfDbTableArg = new WfDbTableArg();
                WfDbTableArg.WfDbTableCriteria wfDbTableCriteria = wfDbTableArg.createCriteria();
                wfDbTableCriteria.andTableNameEqualTo(table.getTableName().toUpperCase());
                table.setTableName(newName);
                table.setRemarks(record.getDisplayName());
                wfDbTableDao.updateByArg(table, wfDbTableArg);
            }
            convertDbMeta(dbTable, oldName);
        } else { //插入一张新表
            WfDbTablePO po = convertWfDbTablePO(record, newName);
            wfDbTableDao.insert(po);
            //往cache插入一张表
            parse(po, dbTable);
            DBMeta cacheMeta = DBMetaCache.getMeta();
            cacheMeta.getTables().add(dbTable);
            DBMetaCache.setMeta(cacheMeta);
        }
    }

    // 创建实体对象
    private WfDbTablePO convertWfDbTablePO(WfDbColumnsPO record, String tableName) {
        WfDbTablePO po = new WfDbTablePO();
        po.setTableName(tableName);
        po.setDbName(record.getDbName().toUpperCase());
        po.setSource("USR");
        po.setRemarks(record.getDisplayName());
        po.setModifyTime(new Date());
        return po;
    }

    //创建缓存对象
    private void parse(WfDbTablePO po, DBTable dbTable) {
        dbTable.setDbName(po.getDbName());
        dbTable.setSource(po.getSource());
        dbTable.setRemarks(po.getRemarks());
        dbTable.setModifyTime(po.getModifyTime());
        for (DBColumn dbColumn1 : dbTable.getColumns()) {
            dbTable.getColumnMap().put(com.club.framework.util.StringUtils.toHump(dbColumn1.getColumnName().toUpperCase()), dbColumn1);
            dbTable.getColumnMap().put(dbColumn1.getColumnName().toUpperCase(), dbColumn1);
            dbTable.getColumnMap().put(dbColumn1.getColumnName(), dbColumn1);
        }
    }

    private void convertDbMeta(DBTable dbTable, String tableName) {
        DBMeta cacheMeta = DBMetaCache.getMeta();
        List<DBTable> cacheMetaTables = cacheMeta.getTables();
        DBTable tempTable = null;
        if (!CollectionUtils.isEmpty(cacheMetaTables)) {
            for (DBTable dbTablePo2 : cacheMetaTables) {
                if (dbTablePo2.getTableName().toUpperCase().equals(tableName)) {
                    tempTable = dbTablePo2;
                    break;
                }
            }
        }
        if (tempTable != null) {
            cacheMetaTables.remove(tempTable);
            tempTable.getColumnMap().clear();
            tempTable.setPks(dbTable.getPks());
            tempTable.setFks(dbTable.getFks());
            tempTable.setColumns(dbTable.getColumns());
            tempTable.setTableName(dbTable.getTableName());
            tempTable.setRemarks(dbTable.getRemarks());
            for (DBColumn dbColumn1 : dbTable.getColumns()) {
                tempTable.getColumnMap().put(com.club.framework.util.StringUtils.toHump(dbColumn1.getColumnName().toUpperCase()), dbColumn1);
                tempTable.getColumnMap().put(dbColumn1.getColumnName().toUpperCase(), dbColumn1);
                tempTable.getColumnMap().put(dbColumn1.getColumnName(), dbColumn1);
            }
            cacheMetaTables.add(tempTable);
            cacheMeta.setTables(cacheMetaTables);
        }
        DBMetaCache.setMeta(cacheMeta);
    }

    @Override
    public int delete(WfDbColumnsPO record) throws BaseAppException {
        logger.debug("delete begin...record={0}", record);
        return wfDbColumnsDao.deleteByPrimaryKey(record.getTableName());
    }

    @Override
    public int deleteBatch(String deleteRecords) throws BaseAppException {
        logger.debug("deleteBatch record begin...deleteRecords={0}", deleteRecords);
        if (StringUtils.isNotBlank(deleteRecords)) {
            deleteRecords = deleteRecords.substring(1, deleteRecords.length() - 1);
            String records[] = deleteRecords.split("},");
            Map<String, Object> map = null;
            WfDbColumnsArg arg = null;
            WfDbColumnsArg.WfDbColumnsCriteria criteria = null;
            for (int i = 0; i < records.length; i++) {
                if ((records.length == 1) || (records.length > 1 && records.length == (i + 1))) {
                    map = JsonUtil.toMap(records[i]);
                } else {
                    map = JsonUtil.toMap(records[i] + "}");
                }
                arg = new WfDbColumnsArg();
                criteria = arg.createCriteria();
                criteria.andTableNameEqualTo((String) map.get("tableName"));
                criteria.andColumnNameEqualTo((String) map.get("columnName"));
                wfDbColumnsDao.deleteByArg(arg);
            }
        }
        return 1;
    }


}
