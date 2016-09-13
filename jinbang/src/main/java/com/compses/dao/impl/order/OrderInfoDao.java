package com.compses.dao.impl.order;

import com.compses.dao.impl.BaseCommonDAO;
import com.compses.dao.order.IOrderInfoDao;
import com.compses.dto.Page;
import com.compses.model.OrderInfo;
import com.compses.util.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jocelynsuebb on 16/7/31.
 */
@Repository
public class OrderInfoDao extends BaseCommonDAO implements IOrderInfoDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public Page<OrderInfo> findUserOrdersByPage(OrderInfo orderInfo, Page<OrderInfo> page) {
        String sql = DBUtils.getSql("OrderInfo","selectUserOrdersForApp");
        return DBUtils.queryPage(sql,page,orderInfo,namedParameterJdbcTemplate,OrderInfo.class);
    }

    public OrderInfo getByOrderCode(String orderCode) {
        String sql= DBUtils.getSql("OrderInfo", "getByOrderCode");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("orderCode", orderCode);
        return  DBUtils.queryForObject(sql, paramMap, namedParameterJdbcTemplate, OrderInfo.class);
    }

}
