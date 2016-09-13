package com.compses.action.api.Order;

import com.compses.action.common.BaseCommonController;
import com.compses.dto.Page;
import com.compses.framework.ResultData;
import com.compses.model.CommentInfo;
import com.compses.service.api.order.CommentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2016/8/14 0014.
 */
@Controller
@RequestMapping("commentInfo")
public class CommentInfoController extends BaseCommonController{
    @Autowired
    private CommentInfoService commentInfoService;

    @RequestMapping(value="save.do")
    @ResponseBody
    public ResultData save(CommentInfo commentInfo){
        ResultData resultData = new ResultData();
        try {
            commentInfo = commentInfoService.save(commentInfo);
            resultData.putEntity(CommentInfo.class,commentInfo);
            resultData.setReturnMsg(true,"评论成功!");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"评论失败!");
        }
        return resultData;
    }

    @RequestMapping(value="listByServiceId.do")
    @ResponseBody
    public ResultData listByServiceId(String serviceId){
        ResultData resultData = new ResultData();
        try {
            List<CommentInfo> commentInfoList  = commentInfoService.listByServiceId(serviceId);
            resultData.putEntities(CommentInfo.class,commentInfoList);
            resultData.setReturnMsg(true,"获取成功!");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败!");
        }
        return resultData;
    }
}
