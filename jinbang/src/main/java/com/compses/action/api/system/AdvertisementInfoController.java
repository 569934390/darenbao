package com.compses.action.api.system;

import com.compses.common.Constants;
import com.compses.framework.ResultData;
import com.compses.model.AdvertisementInfo;
import com.compses.service.api.system.AdvertisementInfoService;
import com.compses.util.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nini on 2016/4/4.
 */

@Controller
@RequestMapping("advertisementInfo")
public class AdvertisementInfoController {

    @Autowired
    private AdvertisementInfoService advertisementInfoService;

    @RequestMapping(value = "listAdvertisement.do" , method = RequestMethod.GET)
    @ResponseBody
    public ResultData listAdvertisement(String appType){
        ResultData resultData = new ResultData();
        try {
            AdvertisementInfo advertisementInfo = new AdvertisementInfo();
            advertisementInfo.setAdverType(appType);
            advertisementInfo.setAdverStatus("1");
            List<AdvertisementInfo> advertisementInfos = advertisementInfoService.loadData(advertisementInfo);
            resultData.putEntities(AdvertisementInfo.class,advertisementInfos);
            resultData.setReturnMsg(true,"获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败！");
        }
        return resultData;
    }


    @RequestMapping(value = "uploadPhoto.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadPhoto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String businessPhotoPath = Constants.getContextProperty("advertisementPhotoPath").toString();
            String totalPath = businessPhotoPath + File.separator ;
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            //获取文件集合
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            String fileName = SystemUtil.uploadMultipart(fileMap, totalPath);
            result.put("iconname", fileName);
            result.put("iconpath", totalPath);
            result.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @RequestMapping(value = "addOrUpdate.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultData addOrUpdate(AdvertisementInfo advertisementInfo){
        ResultData resultData = new ResultData();
        try {
            advertisementInfoService.addOrUpdate(advertisementInfo);
            resultData.setReturnMsg(true,"获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败！");
        }
        return resultData;
    }

}
