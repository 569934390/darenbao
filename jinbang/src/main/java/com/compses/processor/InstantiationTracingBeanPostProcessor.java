package com.compses.processor;

import com.compses.model.*;
import com.compses.redis.util.RedisKeyConfig;
import com.compses.redis.util.RedisSystemParameterUtil;
import com.compses.service.api.statistics.OrderReturnRuleService;
import com.compses.service.api.system.DictService;
import com.compses.service.api.system.SCityService;
import com.compses.service.api.system.SDistrictService;
import com.compses.service.api.system.SProvinceService;
import com.compses.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private DictService dictService;
    @Autowired
    private OrderReturnRuleService orderReturnRuleService;
    @Autowired
    private SProvinceService provinceService;
    @Autowired
    private SCityService cityService;
    @Autowired
    private SDistrictService districtService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //root application context
        if(event.getApplicationContext().getParent() == null){
            //加载系统变量到缓存之中
            List<Dict> dictList = dictService.selectAll();
//            for (Dict dict: dictList){
//                if (!RedisSystemParameterUtil.exists(RedisKeyConfig.DICT_PREFIX+dict.getTypes())){
//                    RedisSystemParameterUtil.hset(RedisKeyConfig.DICT_PREFIX+dict.getTypes(),dict.getValueField(),dict.getDisplayField());
//                }
//            }
            //加载省份
//            List<TBaseProvince> provinceList = provinceService.selectAll();
//            for (TBaseProvince province:provinceList){
//                if (!RedisSystemParameterUtil.exists(RedisKeyConfig.PROVINCE_PREFIX+province.getProvinceCode())){
//                    RedisSystemParameterUtil.set(RedisKeyConfig.PROVINCE_PREFIX+province.getProvinceCode(),province.getProvinceName());
//                }
//            }
//            //加载城市
//            List<TBaseCity> cityList = cityService.selectAll();
//            for (TBaseCity city:cityList){
//                if (!RedisSystemParameterUtil.exists(RedisKeyConfig.CITY_PREFIX+city.getCityCode())){
//                    RedisSystemParameterUtil.set(RedisKeyConfig.CITY_PREFIX+city.getCityCode(),city.getCityName());
//                }
//            }
////            加载地区
//            List<TBaseArea> areaList = districtService.selectAll();
//            for (TBaseArea area :areaList){
//                if (!RedisSystemParameterUtil.exists(RedisKeyConfig.AREA_PREFIX+area.getAreaCode())){
//                    RedisSystemParameterUtil.set(RedisKeyConfig.AREA_PREFIX+area.getAreaCode(),area.getAreaName());
//                }
//            }
            //加载分红规则
            List<OrderReturnRule> ruleList = orderReturnRuleService.getAll();
            for (OrderReturnRule rule:ruleList){
                RedisSystemParameterUtil.set(RedisKeyConfig.RULE_PREFIX+rule.getOrderCategory(),JsonUtils.toJson(rule));
            }

        }
    }
}
