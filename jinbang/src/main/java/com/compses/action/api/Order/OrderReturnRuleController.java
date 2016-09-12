package com.compses.action.api.Order;

import com.compses.action.common.BaseCommonController;
import com.compses.dto.Page;
import com.compses.model.OrderReturnRule;
import com.compses.service.api.order.OrderInfoService;
import com.compses.service.api.statistics.OrderReturnRuleService;
import com.compses.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/9/10 0010.
 */

@Controller
@RequestMapping("rule")
public class OrderReturnRuleController extends BaseCommonController{
    @Autowired
    private OrderReturnRuleService ruleService;

    @RequestMapping("getRulePage.do")
    @ResponseBody
    public Page<OrderReturnRule> getRulePage(Page<OrderReturnRule> page,String conditionsStr) {
        if(conditionsStr!=null)
            page.setConditions(JsonUtils.toMap(conditionsStr));
        OrderReturnRule rule = new OrderReturnRule();
        return ruleService.getPage(rule,page);
    }
}
