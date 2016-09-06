package com.club.web.datasource.controller;


import com.club.core.common.Page;
import com.club.web.datasource.db.arg.WfGeneralTableArg;
import com.club.web.datasource.db.arg.WfGeneralTableColumnsArg;
import com.club.web.datasource.db.dao.WfGeneralTableColumnsDao;
import com.club.web.datasource.db.po.WfGeneralTableColumnsPO;
import com.club.web.datasource.db.po.WfGeneralTablePO;
import com.club.web.datasource.service.IWfGeneralTableColumnsService;
import com.club.framework.exception.BaseAppException;
import com.club.framework.log.ClubLogManager;
import com.club.framework.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <Description>wfgeneraltablecolumns管理 <br>
 * 
 * @author codeCreater <br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2014年11月11日 <br>
 * @since V1.0<br>
 * @see com.club.web.datasource.controller <br>
 */

@Controller
@RequestMapping("/datasource/wfgeneraltablecolumns")
public class WfGeneralTableColumnsController {

    private static final ClubLogManager logger = ClubLogManager
            .getLogger(WfGeneralTableColumnsController.class);

    @Autowired
    private IWfGeneralTableColumnsService wfGeneralTableColumnsService;
    @Autowired
    private WfGeneralTableColumnsDao generalTableColumnsDao ;

    @RequestMapping("index")
    public String index(Model model) {
        // ///////
        // TODO 根据业务场景，进行条件分支跳转、设置页面默认值等

        // ///////

        return "datasource/jsp/wfGeneralTableColumns";
    }

    @RequestMapping("queryRecordByPage")
    @ResponseBody
    public Page<WfGeneralTableColumnsPO> queryRecordByPage(WfGeneralTableColumnsPO record,
            Page<WfGeneralTableColumnsPO> resultPage) throws BaseAppException {
        resultPage = wfGeneralTableColumnsService.selectByArgAndPage(record, resultPage);
        return resultPage;
    }

    @RequestMapping("add")
    @ResponseBody
    public WfGeneralTableColumnsPO add(WfGeneralTableColumnsPO record) throws BaseAppException {
        logger.debug("add record begin...record=[{0}]", record);
        wfGeneralTableColumnsService.add(record);
        return record;
    }

    @RequestMapping("update")
    @ResponseBody
    public WfGeneralTableColumnsPO update(WfGeneralTableColumnsPO record) throws BaseAppException {
        logger.debug("modify record begin...record=[{0}]", record);
        wfGeneralTableColumnsService.update(record);
        return record;
    }

    @RequestMapping("delete")
    @ResponseBody
    public int delete(WfGeneralTableColumnsPO record) throws BaseAppException {
        logger.debug("delete record begin...record=[{0}]", record);
        return wfGeneralTableColumnsService.delete(record);
    }

    @RequestMapping("qryRecordInfo")
    @ResponseBody
    public WfGeneralTableColumnsPO qryRecordInfo(@RequestParam(value = "columnId",
            required = true) Integer columnId) throws BaseAppException {
        WfGeneralTableColumnsPO record = wfGeneralTableColumnsService.selectByPrimaryKey(columnId);
        return record;
    }
    /**
     *通用表列查询list
     * zhangxj-20150702
     * @param request
     * @return
     */
    @RequestMapping("generalTableColumnsList")
    @ResponseBody
    public List<WfGeneralTableColumnsPO> generalTableColumnsList(HttpServletRequest request) throws BaseAppException {
        WfGeneralTableColumnsArg arg = new WfGeneralTableColumnsArg();
        WfGeneralTableColumnsArg.WfGeneralTableColumnsCriteria criteria = arg.createCriteria();
        criteria.andHiddenEqualTo("0");
        String tableId = request.getParameter("tableId");
        if(Utils.isNumber(tableId)){
            criteria.andTableIdEqualTo(Integer.parseInt(tableId));
        }
        List<WfGeneralTableColumnsPO> result = generalTableColumnsDao.selectByArg(arg);
        return result;
    }
}
