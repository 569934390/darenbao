package com.compses.dao.impl.statistics;

import com.compses.dao.impl.BaseCommonDAO;
import com.compses.dao.statistics.IOrderReturnRuleDao;
import com.compses.dto.Page;
import com.compses.model.OrderReturnRule;
import com.compses.util.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
@Repository
public class OrderReturnRuleDao extends BaseCommonDAO implements IOrderReturnRuleDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Page<OrderReturnRule> getPage(OrderReturnRule rule,Page<OrderReturnRule> page){
        String sql = DBUtils.getSql("OrderReturnRule","selectList");
        return DBUtils.queryPage(sql,page,rule,namedParameterJdbcTemplate,OrderReturnRule.class);
    }


}
