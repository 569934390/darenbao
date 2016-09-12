package com.compses.action.api.system;

import com.compses.action.common.BaseCommonController;
import com.compses.framework.ResultData;
import com.compses.model.SCity;
import com.compses.model.TBaseCity;
import com.compses.model.TBaseProvince;
import com.compses.service.api.system.SCityService;
import com.compses.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by nini on 2016/4/9.
 */

@Controller
@RequestMapping("scity")
public class SCityController extends BaseCommonController{

    @Autowired
    private SCityService sCityService;

    /**
     * 手机端获取选择城市列表(已启用)
     * @return
     */
    @RequestMapping(value = "getAllCity.do", method = RequestMethod.GET)
    @ResponseBody
    public ResultData getAllCity(){
        ResultData resultData = new ResultData();
        try {
            TBaseCity sCity = new TBaseCity();
            List<TBaseCity> sCities = sCityService.selectList(sCity);
            resultData.putEntities(TBaseCity.class,sCities);
            resultData.setReturnMsg(true, "获取成功！");
        }catch (Exception e){
            resultData.setReturnMsg(false,"获取失败！");
        }
        return resultData;
    }

    /**
     * 根据省份获取城市列表
     * @return
     */
    @RequestMapping(value = "getListByProviceId.do", method = RequestMethod.GET)
    @ResponseBody
    public ResultData getListByprovinceId(HttpServletRequest request){
        ResultData resultData = new ResultData();
        try {
            String provinceCode = request.getParameter("provinceCode");
            TBaseCity sCity = new TBaseCity();
            sCity.setProvinceCode(provinceCode);
            resultData.setReturnMsg(true,"获取成功！");
            resultData.putEntities(TBaseProvince.class, sCityService.selectList(sCity));
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败！");
        }
        return resultData;
    }

    /**
     * 根据id获取记录
     * @param cityCode
     * @return
     */
    @RequestMapping(value = "getById.do", method = RequestMethod.GET)
    @ResponseBody
    public TBaseCity getById(String cityCode){
        return sCityService.getByCode(cityCode);
    }


    /**
     * 根据省份获取城市列表
     * @return
     */
    @RequestMapping(value = "getListByProviceCode.do", method = RequestMethod.GET)
    @ResponseBody
    public List<TBaseCity> getListByProviceCode(HttpServletRequest request){
        String provinceCode = request.getParameter("provinceCode");
        TBaseCity sCity = new TBaseCity();
        sCity.setProvinceCode(provinceCode);
        return sCityService.selectList(sCity);
    }
}
