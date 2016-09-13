package com.compses.service.api.order;

import com.compses.dao.order.IOrderDetailDao;
import com.compses.model.OrderDetail;
import com.compses.model.OrderInfo;
import com.compses.service.common.BaseCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.ServiceMode;
import java.util.List;

/**
 * Created by Administrator on 2016/8/13 0013.
 */
@Service
@Transactional
public class OrderDetailService extends BaseCommonService{
    @Autowired
    private IOrderDetailDao OrderDetailDao;
    @Autowired
    private OrderInfoService orderInfoService;

    public List<OrderDetail> listByConditions(String orderId ,String orderCategory){
        //获取订单信息
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(orderId);
        orderInfo = orderInfoService.getOrderById(orderInfo);
        OrderDetail searchConditions = new OrderDetail();
        searchConditions.setOrderStatus(orderInfo.getOrderStatus());
        searchConditions.setOrderCategory(orderCategory);
        List<OrderDetail> result = loadData(searchConditions);
        for (int i=0 ; i<result.size();i++){
            result.get(0).setModifyTime(orderInfo.getOrderDate());//订单生成
            switch (Integer.parseInt(orderInfo.getOrderStatus())){
                case 2:
                    result.get(1).setModifyTime(orderInfo.getPayDate());//支付
                break;
                case 3:
                    result.get(1).setModifyTime(orderInfo.getPayDate());//支付时间
                    result.get(2).setModifyTime(orderInfo.getServiceStartDate());//服务开始
                    break;
                case 4:
                    result.get(1).setModifyTime(orderInfo.getPayDate());//支付
                    result.get(2).setModifyTime(orderInfo.getServiceEndDate());//服务结束
                break;
                case 5:
                    result.get(1).setModifyTime(orderInfo.getPayDate());//支付
                    result.get(2).setModifyTime(orderInfo.getServiceEndDate());//服务结束
                    result.get(3).setModifyTime(orderInfo.getOrderEndDate());//已评价
                    result.get(4).setModifyTime(orderInfo.getOrderEndDate());
                break;
                case -1:
                    result.get(1).setModifyTime(orderInfo.getPayDate());//支付
                    result.get(2).setModifyTime(orderInfo.getCancelDate());//退款申请时间
                break;
                case -2:
                    result.get(1).setModifyTime(orderInfo.getPayDate());//支付
                    result.get(2).setModifyTime(orderInfo.getCancelDate());//退款申请时间
                    result.get(3).setModifyTime(orderInfo.getOrderEndDate());//退款成功
                break;
                case -3:
                    result.get(1).setModifyTime(orderInfo.getOrderEndDate());//取消
                    result.get(2).setModifyTime(orderInfo.getOrderEndDate());//结束
                break;
                case -4:
                    result.get(1).setModifyTime(orderInfo.getOrderEndDate());//超时
                    result.get(2).setModifyTime(orderInfo.getOrderEndDate());//结束
                break;
            }
        }
        return result;
    }

}
