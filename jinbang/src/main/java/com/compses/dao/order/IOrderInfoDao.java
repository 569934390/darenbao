package com.compses.dao.order;

import com.compses.dao.IBaseCommonDAO;
import com.compses.dto.Page;
import com.compses.model.OrderInfo;
import com.compses.model.ServiceInfo;

/**
 * Created by jocelynsuebb on 16/7/31.
 */
public interface IOrderInfoDao extends IBaseCommonDAO {

    public Page<OrderInfo> findUserOrdersByPage(OrderInfo orderInfo, Page<OrderInfo> page);

    OrderInfo getByOrderCode(String orderCode);
}
