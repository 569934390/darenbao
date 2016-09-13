package com.compses.action.api.provider;

import com.compses.action.common.BaseCommonController;
import com.compses.action.common.Result;
import com.compses.constants.ServiceConstants;
import com.compses.dto.Page;
import com.compses.framework.ResultData;
import com.compses.model.ServiceInfo;
import com.compses.model.ServiceInfoDTO;
import com.compses.service.api.provider.ServiceInfoService;
import com.compses.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2016/7/25 0025.
 */

@Controller
@RequestMapping("serviceInfo")
public class ServiceInfoController extends BaseCommonController{

    @Autowired
    private ServiceInfoService serviceInfoService;


    @RequestMapping(value="publishService.do" )
    @ResponseBody
    public ResultData publishService(ServiceInfo serviceInfo){
        ResultData resultData = new ResultData();
        try {
            //判断用户是否有该类型的服务,一个用户同一个类型的服务只能一个
            if(StringUtils.isEmpty(serviceInfo.getServiceId())){
                ServiceInfo userService = serviceInfoService.getByUserIdAndCategory(serviceInfo.getPublisherId(),serviceInfo.getServiceCategoryName());
                if(null != userService){
                    resultData.setReturnMsg(false,"您已存在该类型的服务,请重新选择服务类型！");
                    return resultData;
                }
            }
            serviceInfo = serviceInfoService.addOrUpdateService(serviceInfo);
            resultData.putEntity(ServiceInfo.class,serviceInfo);
            resultData.setReturnMsg(true,"发布服务成功！");

        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"发布服务失败！");
        }
        return resultData;
    }

    @RequestMapping(value="listServiceByPublisherId.do" )
    @ResponseBody
    public ResultData listServiceByPublisherId(String publisherId,Page<ServiceInfo> page){
        ResultData resultData =null;
        try {
            Page<ServiceInfo> serviceInfoPage = serviceInfoService.listServiceByPublisherId(publisherId,page);
            resultData = new ResultData(ServiceInfo.class,serviceInfoPage);
            resultData.setReturnMsg(true,"获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败！");
        }
        return resultData;
    }

    @RequestMapping(value="getServiceInfoById.do" )
    @ResponseBody
    public ResultData getServiceInfoById(String serviceId,String userId){
        ResultData resultData = new ResultData();
        try {
            ServiceInfo serviceInfo = serviceInfoService.getServiceInfoById(serviceId,userId);
            resultData.putEntity(ServiceInfo.class,serviceInfo);
            resultData.setReturnMsg(true,"获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败！");
        }
        return resultData;
    }



    @RequestMapping(value="remove.do" )
    @ResponseBody
    public ResultData removeServiceInfo(ServiceInfo serviceInfo){
        ResultData resultData = new ResultData();
        try {
            serviceInfoService.removeServiceInfo(serviceInfo);
            resultData.setReturnMsg(true,"服务已删除！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"服务删除失败！");
        }
        return resultData;
    }

    @RequestMapping(value="last.do" )
    @ResponseBody
    public ResultData findLastServiceInfos(String userId, String cityName, String categoryNames, Page<ServiceInfoDTO> page){
        ResultData resultData =null;
        try {
            Page<ServiceInfoDTO> lastServiceInfos = serviceInfoService.findLastServiceInfos(userId,cityName,categoryNames,page);
            resultData = new ResultData(ServiceInfoDTO.class,lastServiceInfos);
            resultData.setReturnMsg(true,"获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败！");
        }
        return resultData;
    }


    @RequestMapping(value="lastAndroid.do")
    @ResponseBody
    public ResultData findLastServiceInfosAndroid(String userId, String cityId, String categoryNames, Page<ServiceInfoDTO> page){
        ResultData resultData =null;
        try {
            Page<ServiceInfoDTO> lastServiceInfos = serviceInfoService.findLastServiceInfos(userId,cityId,categoryNames,page);
            resultData = new ResultData(ServiceInfoDTO.class,lastServiceInfos);
            resultData.setReturnMsg(true,"获取成功！");
//            List<ServiceInfoDTO> result = lastServiceInfos.getResult();
//            resultData = new ResultData();
//            resultData.put("lastService",result);
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败！");
        }
        return resultData;
    }

    @RequestMapping(value="shelves.do" )
    @ResponseBody
    public ResultData updateServiceShelvesStatus(ServiceInfo serviceinfo){
        ResultData resultData = new ResultData();
        try {
            serviceInfoService.updateServiceShelvesStatus(serviceinfo);
            if (serviceinfo.getShelfStatus().equals(ServiceConstants.SERVICE_SHELFSTATUS_DOWN)){
                resultData.setReturnMsg(true,"已下架！");
            }else {
                resultData.setReturnMsg(true,"已上架！");
            }
        }catch (Exception e){
            e.printStackTrace();
            if (serviceinfo.getShelfStatus().equals(ServiceConstants.SERVICE_SHELFSTATUS_DOWN)){
                resultData.setReturnMsg(false,"下架失败！");
            }else {
                resultData.setReturnMsg(false,"上架失败！");
            }
        }
        return resultData;
    }

    @RequestMapping(value="getByUserIdAndCategory.do" )
    @ResponseBody
    public ResultData getByUserIdAndCategory(String userId,String categoryName){
        ResultData resultData = new ResultData();
        try {
            ServiceInfo serviceInfo = serviceInfoService.getByUserIdAndCategory(userId,categoryName);
            resultData.putEntity(ServiceInfo.class,serviceInfo);
            resultData.setReturnMsg(true,"获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败！");
        }
        return resultData;
    }



}
