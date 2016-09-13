package com.compses.action.api.friend;

import com.compses.action.common.BaseCommonController;
import com.compses.dto.Page;
import com.compses.framework.ResultData;
import com.compses.model.ReportInfo;
import com.compses.service.api.friend.ReportInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/8/2 0002.
 */

@Controller
@RequestMapping("report")
public class ReportInfoController extends BaseCommonController{

    @Autowired
    private ReportInfoService reportInfoService;

    @RequestMapping(value="addNewReoprt.do" )
    @ResponseBody
    public ResultData addNewReoprt(ReportInfo reportInfo){
        ResultData resultData = new ResultData();
        try {
            reportInfo = reportInfoService.addReportInfo(reportInfo);
            resultData.putEntity(ReportInfo.class,reportInfo);
            resultData.setReturnMsg(true, "举报成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"举报失败！");
        }
        return resultData;
    }


    @RequestMapping(value="listPageForReport.do" )
    @ResponseBody
    public ResultData listPageForReport(String userId,Page<ReportInfo> page){
        ResultData resultData = null;
        try {
            Page<ReportInfo> reportInfoPage = reportInfoService.listPageForReport(userId,page);
            resultData = new ResultData(ReportInfo.class,reportInfoPage);
            resultData.setReturnMsg(true, "获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败！");
        }
        return resultData;
    }
}
