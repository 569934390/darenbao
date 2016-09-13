package com.compses.redis.util;

import com.compses.common.Constants;

/**
 * Created by jocelynsuebb on 16/7/28.
 */
public class RedisKeyConfig {

    public static final String USER_LOCATION_KEY="user_locatiobn";

    public static final String USER_NEARBY_KEY = "user_nearby";

    public static double USER_NEARBY_RADIUS = Double.parseDouble(Constants.getContextProperty("user_nearby_radius",1.5).toString());

    public static final String USER_PREFIX="user_";

    public static final String SERVICE_PREFIX="service_";

    public static final String REQUIRE_PREFIX="require_";

    public static final String USER_RELA_PREFIX="user_rela_";

    public static final String AGENT_PREFIX="agent_";

    public static final String DICT_PREFIX="dict_";
    //分红规则
    public static final String RULE_PREFIX="rule_";
    //省
    public static final String PROVINCE_PREFIX="province_";
    //市
    public static final String CITY_PREFIX="city_";
    //区
    public static final String AREA_PREFIX="area_";

}
