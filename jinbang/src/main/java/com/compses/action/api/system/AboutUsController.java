package com.compses.action.api.system;

import com.compses.framework.ResultData;
import com.compses.model.AboutUs;
import com.compses.service.api.system.AboutUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by nini on 2016/3/19.
 */

@Controller
@RequestMapping("aboutUs")
public class AboutUsController {

    @Autowired
    private AboutUsService aboutUsService;


    @RequestMapping(value = "selectOneByVersion.do" , method = RequestMethod.GET)
    @ResponseBody
    public ResultData selectOneByVersion(String versionId,String type){
        ResultData resultData = new ResultData();
        try {
            AboutUs aboutUs = aboutUsService.selectOneByVersion(versionId,type);
            resultData.setReturnMsg(true,"获取成功！");
            resultData.putEntity(AboutUs.class,aboutUs);
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败！");
        }
        return resultData;
    }

}
