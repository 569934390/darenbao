package com.compses.dao.impl.order;

import com.compses.dao.impl.BaseCommonDAO;
import com.compses.dao.order.IOrderDetailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/8/13 0013.
 */
@Repository
public class OrderDetailDao extends BaseCommonDAO implements IOrderDetailDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
}
