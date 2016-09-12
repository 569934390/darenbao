package com.compses.action.api.system;
import com.compses.common.Constants;
import com.compses.framework.ResultData;
import com.compses.model.AdviceInfo;
import com.compses.model.DopPrivilegeUser;
import com.compses.service.api.system.AdviceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by nini on 2016/3/19.
 */


@Controller
@RequestMapping("adviceInfo")
public class AdviceInfoController {

    @Autowired
    private AdviceInfoService adviceInfoService;


    @RequestMapping(value = "addNewAdvice.do" ,  method = RequestMethod.POST)
    @ResponseBody
    public ResultData addNewAdvice(AdviceInfo adviceInfo,HttpServletRequest request){
        ResultData resultData = new ResultData();
        try {
            DopPrivilegeUser user = (DopPrivilegeUser) request.getSession().getAttribute(Constants.STAFF);
            adviceInfo.setAdverUserName(user.getName());
            adviceInfoService.addNewAdvice(adviceInfo);
            resultData.setReturnMsg(true,"反馈成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"反馈失败！");
        }
        return resultData;
    }



}
