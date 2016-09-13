package com.compses.action.api.system;

import com.compses.dto.Page;
import com.compses.framework.ResultData;
import com.compses.model.VersionInfo;
import com.compses.service.api.system.VersionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by nini on 2016/3/19.
 */

@Controller
@RequestMapping("version")
public class VersionInfoController {

    @Autowired
    private VersionInfoService versionInfoService;


    @RequestMapping(value = "getLastVersion.do", method = RequestMethod.GET)
    @ResponseBody
    public VersionInfo getLastVersion(String type){
        VersionInfo version = versionInfoService.getLastVersion(type);
//        resultData.putEntity(VersionInfo.class,version);
//        resultData.setReturnMsg(true,"获取成功！");
        return version;
    }

    @RequestMapping(value = "getLastVersionByType.do", method = RequestMethod.GET)
    @ResponseBody
    public ResultData getLastVersionByType(String type){
        ResultData resultData = new ResultData();
        try {
            VersionInfo version = versionInfoService.getLastVersion(type);
            resultData.putEntity(VersionInfo.class,version);
            resultData.setReturnMsg(true,"获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false, "获取失败！");
        }
        return resultData;
    }


    @RequestMapping(value = "selectDtoList.do" ,  method = RequestMethod.GET)
    @ResponseBody
    public ResultData selectDtoList(VersionInfo versionInfo,Page<VersionInfo> page){
        ResultData resultData = new ResultData();
        Page<VersionInfo> versionInfoPage = versionInfoService.selectDtoList(versionInfo,page);
        resultData = new ResultData(VersionInfo.class,versionInfoPage);
        resultData.setReturnMsg(true,"获取成功！");
        return  resultData;
    }

}
