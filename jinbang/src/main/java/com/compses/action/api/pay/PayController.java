package com.compses.action.api.pay;

import com.compses.framework.ResultData;
import com.compses.model.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jocelynsuebb on 2016/5/22.
 */

@Controller
@RequestMapping("refund")
public class PayController {

//    @Autowired
//    private PayService payService;
//
//    @RequestMapping(value = "payRefund.do")
//    @ResponseBody
//    public ResultData payRefund(OrderInfo orderInfo){
//        ResultData resultData = new ResultData();
//        try{
//            Map<String,Object> result = payService.payRefund(orderInfo);
//            OrderInfo order = (OrderInfo) result.get("orderInfo");
//            Object flag = result.get("flag");
//            boolean flag1 = (Boolean) flag;
//            if(flag1){
//                resultData.setReturnMsg(true, "退款成功！");
//            }else{
//                resultData.setReturnMsg(false, "退款失败！");
//            }
//            resultData.putEntity(OrderInfo.class, order);
//        }catch (Exception e){
//            e.printStackTrace();
//            resultData.setReturnMsg(true, "退款失败！");
//        }
//        return resultData;
//    }


//    @RequestMapping(value = "payRefundWeb.do"  ,method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String,Object> payRefundWeb(int  id){
////        ResultData resultData = new ResultData();
//        Map<String,Object> resultData = new HashMap<String, Object>();
//        try{
//            OrderInfo orderInfo = new OrderInfo();
//            orderInfo.setId(id);
//            Map<String,Object> result = payService.payRefund(orderInfo);
//            OrderInfo order = (OrderInfo) result.get("orderInfo");
//            Object flag = result.get("flag");
//            boolean flag1 = (Boolean) flag;
//            if(flag1){
////                resultData.setReturnMsg(true, "退款成功！");
//                resultData.put("success", true);
//                resultData.put("msg", "退款成功！");
//            }else{
////                resultData.setReturnMsg(false, "退款失败！");
//                resultData.put("success", false);
//                resultData.put("msg", "退款失败！");
//            }
////            resultData.putEntity(OrderInfo.class, order);
//        }catch (Exception e){
//            e.printStackTrace();
//            resultData.put("success", false);
//            resultData.put("msg", "退款失败！");
//        }
//        return resultData;
//    }
}
