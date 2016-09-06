package com.club.web.datamodel.controller;


import com.club.core.common.Page;
import com.club.web.datamodel.db.po.WfDbTablePO;
import com.club.web.datamodel.service.IWfDbTableService;
import com.club.framework.exception.BaseAppException;
import com.club.framework.log.ClubLogManager;
import com.club.framework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <Description>wfdbtable管理 <br>
 * 
 * @author codeCreater <br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2014年11月11日 <br>
 * @since V1.0<br>
 * @see com.club.web.datamodel.controller <br>
 */

@Controller
@RequestMapping("/datamodel/wfdbtable")
public class WfDbTableController {

    private static final ClubLogManager logger = ClubLogManager
            .getLogger(WfDbTableController.class);

    @Autowired
    private IWfDbTableService wfDbTableService;

    @RequestMapping("index")
    public String index(Model model) {
        // ///////
        // TODO 根据业务场景，进行条件分支跳转、设置页面默认值等

        // ///////

        return "datamodel/jsp/wfDbTable";
    }

    @RequestMapping("queryRecordByPage")
    @ResponseBody
    public Page<WfDbTablePO> queryRecordByPage(WfDbTablePO record,
            Page<WfDbTablePO> resultPage) throws BaseAppException {
        resultPage = wfDbTableService.selectByArgAndPage(record, resultPage);
        return resultPage;
    }

    @RequestMapping("add")
    @ResponseBody
    public WfDbTablePO add(WfDbTablePO record) throws BaseAppException {
        logger.debug("add record begin...record=[{0}]", record);
        wfDbTableService.add(record);
        return record;
    }

    @RequestMapping("update")
    @ResponseBody
    public WfDbTablePO update(WfDbTablePO record) throws BaseAppException {
        logger.debug("modify record begin...record=[{0}]", record);
        wfDbTableService.update(record);
        return record;
    }

    @RequestMapping("delete")
    @ResponseBody
    public int delete(WfDbTablePO record) throws BaseAppException {
        logger.debug("delete record begin...record=[{0}]", record);
        return wfDbTableService.delete(record);
    }

    @RequestMapping("qryRecordInfo")
    @ResponseBody
    public WfDbTablePO qryRecordInfo(@RequestParam(value = "tableName",
            required = true) Integer tableName) throws BaseAppException {
        WfDbTablePO record = wfDbTableService.selectByPrimaryKey(tableName);
        return record;
    }

    /**
     *表模型list
     * zhangxj-20150714
     * @param request
     * @return
     */
    @RequestMapping("dbTableComboBox")
    @ResponseBody
    public List<WfDbTablePO> dbTableComboBox(HttpServletRequest request) throws BaseAppException {
        WfDbTablePO po = new WfDbTablePO();
        String dbName = request.getParameter("dbName");
        if(StringUtils.isNotEmpty(dbName)){
            po.setDbName(dbName);
        }
        List<WfDbTablePO> result = wfDbTableService.selectByArg(po);
        return result;
    }

}
