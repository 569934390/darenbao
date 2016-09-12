package com.compses.action.api.provider;

import com.compses.action.common.BaseCommonController;
import com.compses.framework.ResultData;
import com.compses.model.ServiceCategory;
import com.compses.service.api.provider.ServiceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2016/7/24 0024.
 */

@Controller
@RequestMapping("serviceCategory")
public class ServiceCategoryController extends BaseCommonController{

    @Autowired
    private ServiceCategoryService serviceCategoryService;

    @RequestMapping(value="listAllCategory.do" )
    @ResponseBody
    public ResultData listAllCategory(){
        ResultData resultData = new ResultData();
        try {
            ServiceCategory category = new ServiceCategory();
            List<ServiceCategory> categoryList = serviceCategoryService.listCategoryByConditions(category);
            resultData.putEntities(ServiceCategory.class,categoryList);
            resultData.setReturnMsg(true,"获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败！");
        }
        return resultData;
    }

    @RequestMapping(value="listCategoryByConditions.do" )
    @ResponseBody
    public ResultData listCategoryByConditions(String cityId){
        ResultData resultData = new ResultData();
        try {
            ServiceCategory category = new ServiceCategory();
            category.setNativeCityId(cityId);
            List<ServiceCategory> categoryList = serviceCategoryService.listCategoryByConditions(category);
            resultData.putEntities(ServiceCategory.class,categoryList);
            resultData.setReturnMsg(true,"获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败！");
        }
        return resultData;
    }
}
