package com.club.web.datasource.controller;


import com.club.core.common.Page;
import com.club.framework.log.ClubLogManager;
import com.club.web.datasource.db.arg.WfGeneralTableArg;
import com.club.web.datasource.db.dao.WfGeneralTableDao;
import com.club.web.datasource.db.po.WfGeneralTablePO;
import com.club.web.datasource.service.IWfGeneralTableService;
import com.club.framework.exception.BaseAppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <Description>wfgeneraltable管理 <br>
 * 
 * @author codeCreater <br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2014年11月11日 <br>
 * @since V1.0<br>
 * @see com.club.web.datasource.controller <br>
 */

@Controller
@RequestMapping("/datasource/wfgeneraltable")
public class WfGeneralTableController {

    private static final ClubLogManager logger = ClubLogManager
            .getLogger(WfGeneralTableController.class);

    @Autowired
    private IWfGeneralTableService wfGeneralTableService;
    @Autowired
    private WfGeneralTableDao wfGeneralTableDao;

    @RequestMapping("index")
    public String index(Model model) {
        // ///////
        // TODO 根据业务场景，进行条件分支跳转、设置页面默认值等

        // ///////

        return "datasource/jsp/wfGeneralTable";
    }

    @RequestMapping("queryRecordByPage")
    @ResponseBody
    public Page<WfGeneralTablePO> queryRecordByPage(WfGeneralTablePO record,
            Page<WfGeneralTablePO> resultPage) throws BaseAppException {
        resultPage = wfGeneralTableService.selectByArgAndPage(record, resultPage);
        return resultPage;
    }

    @RequestMapping("add")
    @ResponseBody
    public WfGeneralTablePO add(WfGeneralTablePO record) throws BaseAppException {
        logger.debug("add record begin...record=[{0}]", record);
        wfGeneralTableService.add(record);
        return record;
    }

    @RequestMapping("update")
    @ResponseBody
    public WfGeneralTablePO update(WfGeneralTablePO record) throws BaseAppException {
        logger.debug("modify record begin...record=[{0}]", record);
        wfGeneralTableService.update(record);
        return record;
    }

    @RequestMapping("delete")
    @ResponseBody
    public int delete(WfGeneralTablePO record) throws BaseAppException {
        logger.debug("delete record begin...record=[{0}]", record);
        return wfGeneralTableService.delete(record);
    }

    @RequestMapping("qryRecordInfo")
    @ResponseBody
    public WfGeneralTablePO qryRecordInfo(@RequestParam(value = "tableId",
            required = true) Integer tableId) throws BaseAppException {
        WfGeneralTablePO record = wfGeneralTableService.selectByPrimaryKey(tableId);
        return record;
    }
    /**
     *通用表查询list
     * zhangxj-20150701
     * @param request
     * @return
     */
    @RequestMapping("generalTableComboBox")
    @ResponseBody
    public List<WfGeneralTablePO> generalTableComboBox(HttpServletRequest request) throws BaseAppException {
        WfGeneralTableArg arg = new WfGeneralTableArg();
        WfGeneralTableArg.WfGeneralTableCriteria criteria = arg.createCriteria();
        criteria.andIsPageEqualTo("0");
        List<WfGeneralTablePO> result = wfGeneralTableDao.selectByArg(arg);
        return result;
    }
}
