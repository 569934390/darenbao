package com.compses.service.api.order;

import com.compses.constants.OrderConstants;
import com.compses.dao.order.ICommentInfoDao;
import com.compses.model.CommentInfo;
import com.compses.model.OrderInfo;
import com.compses.service.common.BaseCommonService;
import com.compses.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/8/14 0014.
 */
@Service
@Transactional
public class CommentInfoService extends BaseCommonService{
    @Autowired
    private ICommentInfoDao commentInfoDao;
    @Autowired
    private OrderInfoService orderInfoService;

    public CommentInfo save(CommentInfo commentInfo){
        commentInfo.setCommentId(UUIDUtils.getUUID());
        commentInfoDao.saveForUUid(commentInfo);
        //修改订单状态
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(commentInfo.getOrderId());
        orderInfo.setOrderStatus(OrderConstants.ORDER_STATUS_END);
        orderInfoService.update(orderInfo);
        return commentInfo;
    }

    public List<CommentInfo> listByServiceId(String serviceId){
        CommentInfo commentInfo = new CommentInfo();
        commentInfo.setServiceId(serviceId);
        return commentInfoDao.loadData(commentInfo);
    }
}
