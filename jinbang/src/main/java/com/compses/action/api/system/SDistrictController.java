package com.compses.action.api.system;

import com.compses.model.SDistrict;
import com.compses.model.TBaseArea;
import com.compses.service.api.system.SDistrictService;
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
@RequestMapping("sdistrict")
public class SDistrictController {

    @Autowired
    private SDistrictService sDistrictService;


    @RequestMapping(value = "getListByCityCode.do", method = RequestMethod.GET)
    @ResponseBody
    public List<TBaseArea> getListByCityId(HttpServletRequest request){
       String cityCode = request.getParameter("cityCode");
        TBaseArea sDistrict = new TBaseArea();
        sDistrict.setCityCode(cityCode);
        return sDistrictService.selectList(sDistrict);
    }
}
