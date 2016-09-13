package com.compses.strategy.impl;

import com.compses.model.OrderInfo;
import com.compses.strategy.IStrategy;

/**
 * Created by Administrator on 2016/8/6 0006.
 */
public class StatisticsStrategy {

    private IStrategy strategy;

    public StatisticsStrategy(IStrategy strategy){
        this.strategy = strategy;
    }

    public void statistics(OrderInfo orderInfo){
        this.strategy.statistics(orderInfo);
    }



}
