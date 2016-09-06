package com.club.web.datamodel.controller;


import com.club.core.common.Page;
import com.club.web.datamodel.db.po.WfDbColumnsPO;
import com.club.web.datamodel.service.IWfDbColumnsService;
import com.club.framework.exception.BaseAppException;
import com.club.framework.log.ClubLogManager;
import com.club.framework.util.JsonUtil;
import com.club.web.common.vo.DBColumn;
import com.club.web.common.vo.DBTable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <Description>wfdbcolumns管理 <br>
 * 
 * @author codeCreater <br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2014年11月11日 <br>
 * @since V1.0<br>
 * @see com.club.web.datamodel.controller <br>
 */

@Controller
@RequestMapping("/datamodel/wfdbcolumns")
public class WfDbColumnsController {

    private static final ClubLogManager logger = ClubLogManager
            .getLogger(WfDbColumnsController.class);

    @Autowired
    private IWfDbColumnsService wfDbColumnsService ;

    @RequestMapping("index")
    public String index(Model model) {
        // ///////
        // TODO 根据业务场景，进行条件分支跳转、设置页面默认值等

        // ///////

        return "datamodel/jsp/wfDbColumns";
    }

    @RequestMapping("queryRecordByPage")
    @ResponseBody
    public Page<WfDbColumnsPO> queryRecordByPage(WfDbColumnsPO record,
            Page<WfDbColumnsPO> resultPage) throws BaseAppException {
        resultPage = wfDbColumnsService.selectByArgAndPage(record, resultPage);
        return resultPage;
    }

    /**
     * 根据数据库名称过滤过当前数据库下的所有数据表模型
     * */
    @RequestMapping("queryRecordByPageWithDbName")
    @ResponseBody
    protected Page<WfDbColumnsPO> queryRecordByPageWithDbName(WfDbColumnsPO record, Page<WfDbColumnsPO> resultPage) throws BaseAppException {
        resultPage.setLimit(1000);
        resultPage = wfDbColumnsService.queryRecordByPageWithDbName(record, resultPage);
        return resultPage;
    }

    @RequestMapping("add")
    @ResponseBody
    public WfDbColumnsPO add(WfDbColumnsPO record) throws BaseAppException {
        logger.debug("add record begin...record=[{0}]", record);
        wfDbColumnsService.add(record);
        return record;
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Map<String, Object> saveOrUpdate(WfDbColumnsPO record) throws BaseAppException {
        logger.debug("saveOrUpdate record begin...record=[{0}]", record);
        Map<String, Object> result = new HashMap<String, Object>();
        if (record == null) {
            return null;
        }
        if (StringUtils.isBlank(record.getColumnName())) {
            return null;
        }
        String columnName = record.getColumnName().substring(3);
        String recordsSplit[] = columnName.split("Y@Y");
        //取新列对象 主要为获取新表表名
        WfDbColumnsPO entity = JsonUtil.toBean(recordsSplit[0], WfDbColumnsPO.class);
        List<WfDbColumnsPO> columnsList = new ArrayList<WfDbColumnsPO>();
        DBTable dbTable = newDbTable(entity, record);

        // 收集pks，fks，columns，columnsList
        for (int i = 0; i < recordsSplit.length; i++) {
            if (StringUtils.isNotBlank(recordsSplit[i])) {
                WfDbColumnsPO po = wfDbColumnsService.convertWfDbColumnsPO(recordsSplit[i], dbTable);
                columnsList.add(po);
            }
        }
        if (CollectionUtils.isEmpty(columnsList)) {
            return null;
        }
        try {
            //销毁存在数据库中的表，并且创建新表
            wfDbColumnsService.createTable(columnsList, entity, record);
            //更新存放表和列信息的表;设置cache
            wfDbColumnsService.saveTableAndColumns(columnsList, record, dbTable);
            result.put("success", true);
        } catch (Exception e) {
            result.put("message", "建表失败，列名请尽量使用字母加下划线，请删除该表重新建表！");
            throw new BaseAppException();
        }
        return result;
    }

    private DBTable newDbTable(WfDbColumnsPO entity, WfDbColumnsPO record) {
        DBTable dbTable = new DBTable();
        dbTable.setTableName(entity.getTableName().toUpperCase());
        dbTable.setRemarks(record.getDisplayName());
        // 初始化
        dbTable.setPks(new ArrayList<String>() );
        dbTable.setFks(new ArrayList<String>());
        dbTable.setColumns(new ArrayList<DBColumn>());
        return dbTable;
    }

    @RequestMapping("isExist")
    @ResponseBody
    public int isExist(WfDbColumnsPO record) throws BaseAppException {
        logger.debug("isExist record begin...record=[{0}]", record);
        return wfDbColumnsService.isExist(record);
    }

    @RequestMapping("update")
    @ResponseBody
    public WfDbColumnsPO update(WfDbColumnsPO record) throws BaseAppException {
        logger.debug("modify record begin...record=[{0}]", record);
        wfDbColumnsService.update(record);
        return record;
    }

    @RequestMapping("delete")
    @ResponseBody
    public int delete(WfDbColumnsPO record) throws BaseAppException {
        logger.debug("delete record begin...record=[{0}]", record);
        return wfDbColumnsService.delete(record);
    }

    @RequestMapping("deleteBatch")
    @ResponseBody
    public int deleteBatch(String deleteRecords) throws BaseAppException {
        logger.debug("deleteBatch record begin...deleteRecords={0}", deleteRecords);
        return wfDbColumnsService.deleteBatch(deleteRecords);
    }

    @RequestMapping("qryRecordInfo")
    @ResponseBody
    public WfDbColumnsPO qryRecordInfo(@RequestParam(value = "tableName",
            required = true) String tableName) throws BaseAppException {
        WfDbColumnsPO record = wfDbColumnsService.selectByPrimaryKey(tableName);
        return record;
    }

}
