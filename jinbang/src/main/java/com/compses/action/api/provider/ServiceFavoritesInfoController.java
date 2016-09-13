package com.compses.action.api.provider;

import com.compses.action.common.BaseCommonController;
import com.compses.dto.Page;
import com.compses.dto.ServiceFavoritesInfoDto;
import com.compses.framework.ResultData;
import com.compses.model.ServiceFavoritesInfo;
import com.compses.service.api.provider.ServiceFavoritesInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/7/26 0026.
 */

@Controller
@RequestMapping("serviceFavoritesInfo")
public class ServiceFavoritesInfoController extends BaseCommonController{

    @Autowired
    private ServiceFavoritesInfoService serviceFavoritesInfoService;


    @RequestMapping(value="addOrDelFavoriteService.do" )
    @ResponseBody
    public ResultData addFavoriteService(String userId,String serviceId){
        ResultData resultData = new ResultData();
        try {
            serviceFavoritesInfoService.addOrDelFavoriteService(userId, serviceId);
            resultData.setReturnMsg(true,"操作成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"操作失败！");
        }
        return resultData;
    }

    @RequestMapping(value="listPage.do" )
    @ResponseBody
    public ResultData listPage(String userId,Page<ServiceFavoritesInfoDto> page){
        ResultData resultData = null;
        try {
            Page<ServiceFavoritesInfoDto> serviceFavoritesInfoPage =serviceFavoritesInfoService.listPage(userId, page);
            resultData = new ResultData(ServiceFavoritesInfoDto.class,serviceFavoritesInfoPage);
            resultData.setReturnMsg(true,"获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败！");
        }
        return resultData;
    }
}
