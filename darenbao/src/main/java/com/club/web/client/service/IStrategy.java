package com.club.web.client.service;


import java.util.Map;

/**
 * 积分计算策略
 * Created by lyhcn on 16/2/8.
 */
public interface IStrategy {

    int compute(Map<String,Object> params);
}
