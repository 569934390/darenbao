package com.compses.action.api.friend;

import com.compses.action.common.BaseCommonController;
import com.compses.dto.FriendRelaDTO;
import com.compses.framework.ResultData;
import com.compses.model.FriendRela;
import com.compses.service.api.service.FriendRelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2016/7/27 0027.
 */


@Controller
@RequestMapping("friend")
public class FriendRelaController extends BaseCommonController{

    @Autowired
    private FriendRelaService friendRelaService;

    //新增好友、拉黑（status 1好友 -1黑名单）
    @RequestMapping(value="add.do")
    @ResponseBody
    public ResultData add(FriendRela friendRela){
        ResultData resultData = new ResultData();
        try {
            int res = friendRelaService.add(friendRela);
            if (res == 1){
                resultData.setReturnMsg(true,"操作成功！");
            }else {
                resultData.setReturnMsg(false, "已添加该好友，请不要重复添加！");
            }
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false, "操作失败！");
        }
        return resultData;
    }

    //删除好友、黑名单
    @RequestMapping(value="del.do" )
    @ResponseBody
    public ResultData del(FriendRela friendRela){
        ResultData resultData = new ResultData();
        try {
            friendRelaService.del(friendRela);
            resultData.setReturnMsg(true,"操作成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false, "操作失败！");
        }
        return resultData;
    }

    //好友列表（status=1）、黑名单列表（黑名单目前不显示）
    @RequestMapping(value="listFriend.do" )
    @ResponseBody
    public ResultData listFriend(String userId,String status){
        ResultData resultData = new ResultData();
        try {
            List<FriendRelaDTO> friendRelaDTOList = friendRelaService.listFriend(userId,status);
            resultData.putEntities(FriendRela.class, friendRelaDTOList);
            resultData.setReturnMsg(true,"操作成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"操作失败！");
        }
        return resultData;
    }

    //根据条件查找好友列表
    @RequestMapping(value="selectListByConditions.do" )
    @ResponseBody
    public ResultData selectListByConditions(String condition){
        ResultData resultData = new ResultData();
        try {
            List<FriendRelaDTO> friendRelaDTOList = friendRelaService.findUserByConditions(condition);
            resultData.putEntities(FriendRela.class, friendRelaDTOList);
            resultData.setReturnMsg(true,"操作成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"操作失败！");
        }
        return resultData;
    }

    @RequestMapping(value="findUserByConditions.do")
    @ResponseBody
    public ResultData findUserByConditions(String condition){
        ResultData resultData = new ResultData();
        try {
            List<FriendRelaDTO> friendRelaDTOList = friendRelaService.findUserByConditions(condition);
            resultData.putEntities(FriendRela.class, friendRelaDTOList);
            resultData.setReturnMsg(true,"操作成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"操作失败！");
        }
        return resultData;
    }
}
