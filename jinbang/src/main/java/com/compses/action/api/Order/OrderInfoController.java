package com.compses.action.api.Order;

import com.compses.action.common.BaseCommonController;
import com.compses.constants.OrderConstants;
import com.compses.dto.Page;
import com.compses.framework.ResultData;
import com.compses.model.OrderDetail;
import com.compses.model.OrderInfo;
import com.compses.service.api.order.OrderDetailService;
import com.compses.service.api.order.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Created by jocelynsuebb on 16/8/1.
 */
@Controller
@RequestMapping("order")
public class OrderInfoController extends BaseCommonController {

    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private OrderDetailService orderDetailService;

    @RequestMapping(value="findByUserIdAndStatus.do")
    @ResponseBody
    public ResultData findByUserIdAndStatus(OrderInfo orderInfo, Page<OrderInfo> page){
        ResultData resultData = null;
        try {
            Page<OrderInfo> userOrders = orderInfoService.findByUserIdAndStatus(orderInfo,page);
            resultData = new ResultData(OrderInfo.class,userOrders);
            resultData.setReturnMsg(true,"获取订单列表成功!");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取订单列表失败!");
        }
        return resultData;

    }

    @RequestMapping(value="update.do")
    @ResponseBody
    public ResultData update(OrderInfo orderInfo){
        ResultData resultData = new ResultData();
        try {
            orderInfoService.update(orderInfo);
            resultData.setReturnMsg(true,"更新订单成功!");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"更新订单失败!");
        }
        return resultData;
    }

    @RequestMapping(value="getOrder.do")
    @ResponseBody
    public ResultData getOrder(OrderInfo orderInfo){
        ResultData resultData = new ResultData();
        try {
            OrderInfo order = orderInfoService.getOrderById(orderInfo);
            resultData.putEntity(OrderInfo.class,order);
            resultData.setReturnMsg(true,"获取订单成功!");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取订单失败!");
        }
        return resultData;
    }

    @RequestMapping(value="save.do")
    @ResponseBody
    public ResultData save(OrderInfo orderInfo){
        ResultData resultData = new ResultData();
        try {
            orderInfo =orderInfoService.save(orderInfo);
            resultData.putEntity(OrderInfo.class,orderInfo);
            resultData.setReturnMsg(true,"下单成功!");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"下单失败!");
        }
        return resultData;
    }

    @RequestMapping(value="getByOrderCode.do")
    @ResponseBody
    public ResultData getByOrderCode(String orderCode){
        ResultData resultData = new ResultData();
        try {
            OrderInfo order = orderInfoService.getByOrderCode(orderCode);
            resultData.putEntity(OrderInfo.class,order);
            resultData.setReturnMsg(true,"获取订单成功!");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取订单失败!");
        }
        return resultData;
    }

    @RequestMapping(value="cancelOrder.do")
    @ResponseBody
    public ResultData cancelOrder(String orderId,String cancelReason){
        ResultData resultData = new ResultData();
        try {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderId(orderId);
            orderInfo = orderInfoService.getOrderById(orderInfo);
            orderInfo.setOrderStatus(OrderConstants.ORDER_STATUS_CANCEL_ZD);
            orderInfo.setCancelReason(cancelReason);
            orderInfo.setOrderEndDate(new Date());
            orderInfoService.update(orderInfo);
            resultData.putEntity(OrderInfo.class,orderInfo);
            resultData.setReturnMsg(true,"取消订单成功!");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"取消订单失败!");
        }
        return resultData;
    }

    @RequestMapping(value="getOrderDetails.do")
    @ResponseBody
    public ResultData getOrderDetails(String orderId,String orderCategory){
        ResultData resultData = new ResultData();
        try {
            List<OrderDetail> resultList = orderDetailService.listByConditions(orderId,orderCategory);
            resultData.putEntities(OrderDetail.class,resultList);
            resultData.setReturnMsg(true,"获取订单明细成功!");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取订单明细失败!");
        }
        return resultData;
    }

    @RequestMapping(value="refund.do")
    @ResponseBody
    public ResultData refund(OrderInfo orderInfo){
        ResultData resultData = new ResultData();
        try {
            orderInfo.setOrderStatus(OrderConstants.ORDER_STATUS_REFUNDING);
            orderInfo.setCancelDate(new Date());
            orderInfoService.update(orderInfo);
            resultData.setReturnMsg(true,"退款申请成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"退款申请失败！");
        }
        return resultData;
    }

    @RequestMapping(value="applyRefund.do")
    @ResponseBody
    public ResultData applyRefund(OrderInfo orderInfo){
        ResultData resultData = new ResultData();
        try {
            orderInfo.setOrderStatus(OrderConstants.ORDER_STATUS_REFUND);
            orderInfo.setOrderEndDate(new Date());
            orderInfoService.updateRefundOrder(orderInfo);
            resultData.setReturnMsg(true,"操作成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"操作失败！");
        }
        return resultData;
    }
}
