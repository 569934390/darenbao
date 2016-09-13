package com.compses.action.api.base;

import com.compses.common.Constants;
import com.compses.framework.ResultData;
import com.compses.model.AgentInfo;
import com.compses.service.api.base.AgentInfoService;
import com.compses.util.StringUtils;
import com.compses.util.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/2 0002.
 */

@Controller
@RequestMapping("agentInfo")
public class AgentInfoController {

    @Autowired
    private AgentInfoService agentInfoService;

    @RequestMapping(value="addOrUpdate.do" )
    @ResponseBody
    public ResultData addOrUpdate(AgentInfo agentInfo){
        ResultData resultData = new ResultData();
        try {
            if (StringUtils.isEmpty(agentInfo.getAgentId())){
                agentInfo = agentInfoService.add(agentInfo);
            }else {
                agentInfo = agentInfoService.update(agentInfo);
            }
            resultData.putEntity(AgentInfo.class,agentInfo);
            resultData.setReturnMsg(true,"保存成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"保存失败！");
        }
        return resultData;
    }

    @RequestMapping(value="getById.do" )
    @ResponseBody
    public ResultData getById(String id){
        ResultData resultData = new ResultData();
        try {
            AgentInfo agentInfo = agentInfoService.getById(id);
            resultData.putEntity(AgentInfo.class,agentInfo);
            resultData.setReturnMsg(true,"获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败！");
        }
        return resultData;
    }

    @RequestMapping(value="deleteById.do" )
    @ResponseBody
    public ResultData deleteById(String id){
        ResultData resultData = new ResultData();
        try {
            agentInfoService.deleteById(id);
            resultData.setReturnMsg(true,"删除成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"删除失败！");
        }
        return resultData;
    }

    @RequestMapping(value="uploadLicense.do")
    @ResponseBody
    public Map<String,Object> uploadLicense(HttpServletRequest request){
        Map<String,Object> result = new HashMap<String, Object>();
        try {
            String agentPath = Constants.getContextProperty("agentPic").toString();
            String totalPath = File.separator+agentPath+ File.separator;
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            //获取文件集合
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            String fileName = SystemUtil.uploadMultipart(fileMap, totalPath);
            result.put("iconname", fileName);
            result.put("iconpath", totalPath);
            result.put("success", true);
        }catch (Exception e){
            result.put("success", false);
            throw new RuntimeException(e);
        }
        return result;
    }
}
