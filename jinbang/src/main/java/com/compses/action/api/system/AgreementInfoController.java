package com.compses.action.api.system;

import com.compses.framework.ResultData;
import com.compses.model.AgreementInfo;
import com.compses.service.api.system.AgreementInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by nini on 2016/3/19.
 */

@Controller
@RequestMapping("agreementInfo")
public class AgreementInfoController {

    @Autowired
    private AgreementInfoService agreementInfoService;


    @RequestMapping(value = "selectOneByVersion.do" ,  method = RequestMethod.GET)
    @ResponseBody
    public ResultData selectOneByVersion(String versionId,String type){
        ResultData resultData = new ResultData();
        try {
            AgreementInfo agreementInfo = agreementInfoService.selectOneByVersion(versionId,type);
            resultData.setReturnMsg(true,"获取成功！");
            resultData.putEntity(AgreementInfo.class,agreementInfo);
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败！");
        }
        return resultData;
    }

}
