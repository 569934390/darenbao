package com.compses.action.api.system;

import com.compses.framework.ResultData;
import com.compses.model.SystemConfig;
import com.compses.service.api.system.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by nini on 2016/4/4.
 */

@Controller
@RequestMapping("systemConfig")
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;

    @RequestMapping(value = "listAllSystemconfig.do", method = RequestMethod.GET)
    @ResponseBody
    public ResultData listAllSystemconfig(String type){
        ResultData resultData = new ResultData();
        try {
            List<SystemConfig> systemConfigList = systemConfigService.listAllSystemconfig(type);
            resultData.setReturnMsg(true,"获取成功！");
            resultData.putEntities(SystemConfig.class,systemConfigList);
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败！");
        }
        return  resultData;
    }

    @RequestMapping(value = "updateConfig.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultData updateConfig(SystemConfig systemConfig){
        ResultData resultData = new ResultData();
        try {
            systemConfig = systemConfigService.update(systemConfig);
            resultData.setReturnMsg(true,"更新成功！");
            resultData.putEntity(SystemConfig.class, systemConfig);
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"更新失败！");
        }
        return  resultData;
    }




}
