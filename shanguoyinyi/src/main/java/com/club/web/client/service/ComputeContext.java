package com.club.web.client.service;

import com.club.framework.exception.BaseAppException;

import java.util.Map;

/**
 * 积分计算策略上下文
 * Created by lyhcn on 16/2/9.
 */
public class ComputeContext {

     private IStrategy strategy;

     public int execute(String className,Map<String,Object> params) throws ClassNotFoundException, IllegalAccessException, InstantiationException, BaseAppException {
          if (verify(params)){
               throw new BaseAppException("接口参数校验出错");
          }
          strategy = (IStrategy) Class.forName("com.club.web.client.service.impl." + className+"Strategy").newInstance();
          return strategy.compute(params);
     }

     public boolean verify(Map<String,Object> params){
          if (!params.containsKey("point"))return false;
          return  true;
     }

}
