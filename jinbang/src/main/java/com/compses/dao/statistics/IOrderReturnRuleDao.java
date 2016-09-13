package com.compses.dao.statistics;

import com.compses.dao.IBaseCommonDAO;
import com.compses.dto.Page;
import com.compses.model.OrderReturnRule;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public interface IOrderReturnRuleDao extends IBaseCommonDAO{

    Page<OrderReturnRule> getPage(OrderReturnRule rule,Page<OrderReturnRule> page);

}
