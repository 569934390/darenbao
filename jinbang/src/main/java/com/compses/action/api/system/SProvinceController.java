package com.compses.action.api.system;

import com.compses.framework.ResultData;
import com.compses.model.SProvince;
import com.compses.model.TBaseProvince;
import com.compses.service.api.system.SProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by nini on 2016/4/9.
 */

@Controller
@RequestMapping("sprovince")
public class SProvinceController {

    @Autowired
    private SProvinceService sProvinceService;


    @RequestMapping(value = "getAllProvince.do", method = RequestMethod.GET)
    @ResponseBody
    public ResultData getAllProvince(){
        ResultData resultData = new ResultData();
        try {
            List<TBaseProvince> provinceList = sProvinceService.selectAll();
            resultData.putEntities(TBaseProvince.class,provinceList);
            resultData.setReturnMsg(true,"获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败！");
        }
        return resultData;
    }

    @RequestMapping(value = "get.do", method = RequestMethod.GET)
    @ResponseBody
    public List<TBaseProvince> getAll(){
        TBaseProvince sProvince = new TBaseProvince();
        return sProvinceService.selectAll();
    }
}
