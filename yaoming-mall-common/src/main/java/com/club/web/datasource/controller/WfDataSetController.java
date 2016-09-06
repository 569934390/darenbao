package com.club.web.datasource.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.club.framework.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.web.datamodel.service.IWfDbTableService;
import com.club.web.datasource.db.dao.WfDataSetDao;
import com.club.web.datasource.db.po.WfDataSetPO;
import com.club.web.datasource.service.IWfDataSetService;
import com.club.framework.exception.BaseAppException;
import com.club.framework.log.ClubLogManager;
import com.club.web.common.service.IBaseService;

/**
 * <Description>wfdataset管理 <br>
 * 
 * @author codeCreater <br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2014年11月11日 <br>
 * @since V1.0<br>
 * @see com.club.web.datasource.controller <br>
 */

@Controller
@RequestMapping("/datasource/wfdataset")
public class WfDataSetController {

    private static final ClubLogManager logger = ClubLogManager
            .getLogger(WfDataSetController.class);

    @Autowired
    private IWfDataSetService wfDataSetService;
    @Autowired
    private WfDataSetDao dataSetDao;
    @Autowired
    private IBaseService baseService;
    @Autowired
    private IWfDbTableService wfDbTableService ;

    @RequestMapping("index")
    public String index(Model model) {
        // ///////
        // TODO 根据业务场景，进行条件分支跳转、设置页面默认值等

        // ///////

        return "datasource/jsp/wfDataSet";
    }

    @RequestMapping("queryRecordByPage")
    @ResponseBody
    public Page<WfDataSetPO> queryRecordByPage(WfDataSetPO record,
            Page<WfDataSetPO> resultPage) throws BaseAppException {
        resultPage = wfDataSetService.selectByArgAndPage(record, resultPage);
        return resultPage;
    }

    @RequestMapping("add")
    @ResponseBody
    public WfDataSetPO add(WfDataSetPO record,HttpServletRequest request) throws BaseAppException {
        logger.debug("add record begin...record=[{0}]", record);
        wfDataSetService.add(record,request);
        return record;
    }

    @RequestMapping("verifyDataSetCode")
    @ResponseBody
    public WfDataSetPO verifyDataSetCode(WfDataSetPO record) throws BaseAppException {
        logger.debug("verifyDataSetCode record begin...record=[{0}]", record);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("name", record.getName());
        map.put("status","0");
        List<WfDataSetPO> pos = dataSetDao.selectByMap(map);
        if(pos != null && pos.size() > 0) {
            if(!pos.get(0).getDataSetId().equals(record.getDataSetId())){
                return pos.get(0);
            }
        }
        record.setName("");
        return record;
    }

    @RequestMapping("update")
    @ResponseBody
    public WfDataSetPO update(WfDataSetPO record,HttpServletRequest request) throws BaseAppException {
        logger.debug("modify record begin...record=[{0}]", record);
        wfDataSetService.update(record, request);
        return record;
    }

    @RequestMapping("delete")
    @ResponseBody
    public int delete(WfDataSetPO record,HttpServletRequest request) throws BaseAppException {
        logger.debug("delete record begin...record=[{0}]", record);
        return wfDataSetService.delete(record,request);
    }

    @RequestMapping("qryRecordInfo")
    @ResponseBody
    public WfDataSetPO qryRecordInfo(@RequestParam(value = "dataSetId",
            required = true) Integer dataSetId) throws BaseAppException {
        WfDataSetPO record = wfDataSetService.selectByPrimaryKey(dataSetId);
        return record;
    }


    @RequestMapping("getDataByDataSetId")
    @ResponseBody
    public Page<Map<String,Object>> getDataByDataSetId(@RequestParam(value = "dataSetId",
            required = true) Integer dataSetId,Page<Map<String,Object>> page,HttpServletResponse response) throws BaseAppException {
        page = wfDbTableService.getData(dataSetId, page,response);
        return page;
    }
    //获取用户等级列表信息
    @RequestMapping("getDataByName")
    @ResponseBody
    public Page<Map<String,Object>> getDataByName(@RequestParam(value = "name",
            required = true) String name,Page<Map<String,Object>> page,String conditionStr,HttpServletResponse response) throws BaseAppException {
        if (conditionStr!=null){
            page.setConditons(JsonUtil.toMap(conditionStr));
        }
        page = wfDbTableService.getData(name, page,response);
        return page;
    }

    @RequestMapping("getDataByNameNoHump")
    @ResponseBody
    public Page<Map<String,Object>> getDataByNameNoHump(@RequestParam(value = "name",
            required = true) String name,Page<Map<String,Object>> page,HttpServletResponse response) throws BaseAppException {
        page = wfDbTableService.getDataNoHump(name, page, response);
        return page;
    }

    @RequestMapping("getTableCombobox")
    @ResponseBody
    public List<Map<String,Object>> getTableCombobox() throws BaseAppException {
        List<Map<String,Object>> results = baseService.selectList("wf_db_table",new HashMap<String, Object>());
        return results;
        /*List<Map<String,String>> results = wfDataSetService.selectTableName(dbLinkId);
		return results;*/
    	
    }
/*    @RequestMapping("getTableCombobox1")
    @ResponseBody
    public List<Map<String,Object>> getTableCombobox1(@RequestParam(value = "id",
            required = true) Integer dbLinkId) throws BaseAppException {
        List<Map<String,Object>> results = wfDataSetService.selectTableName(dbLinkId);
		return results;
    	
    }*/

    @RequestMapping("getTableColumnCombobox")
    @ResponseBody
    public List<Map<String,Object>> getTableColumnCombobox(@RequestParam(value = "tableName",
            required = true) String tableName) throws BaseAppException {
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("tableName",tableName);
        List<Map<String,Object>> results = baseService.selectList("wf_db_columns",searchParams);
        return results;
    }
/*    @RequestMapping("getTableColumnCombobox1")
    @ResponseBody
    public List<Map<String,Object>> getTableColumnCombobox1(@RequestParam(value = "id",
            required = true) Integer dbLinkId, @RequestParam(value = "tableName",
    required = true) String tableName) throws BaseAppException {
    	List<Map<String,Object>> results = wfDataSetService.selectTableColumns(dbLinkId, tableName);
    	return results;
    }*/
    
/*	@RequestMapping("getDataSetList")
	@ResponseBody
	public List<Map<Integer,String>> getDataSetList() throws BaseAppException {
		List<Map<Integer,String>> results = wfDataSetService.selectDataInfo();
		return results;
	}*/
	@RequestMapping("getDataListByIdAndColumns")
	@ResponseBody
	public List<Object> getDataListByIdAndColumns(@RequestParam(value = "name",
        required = true) String name, @RequestParam(value = "columns",
                    required = true) String columns) throws BaseAppException {
        List<Object> results = wfDbTableService.getDataListByIdAndColumns(name, columns);

        return results;
    }

    @RequestMapping("getDataPageByIdAndColumns")
    @ResponseBody
    public Page<Map<String,Object>> getDataPageByIdAndColumns(@RequestParam(value = "name",
            required = true) String name, @RequestParam(value = "columns",
            required = true) String columns,Page<Map<String,Object>> page) throws BaseAppException {
        return wfDbTableService.getDataListByIdAndColumns(name, columns,page);
    }
}
