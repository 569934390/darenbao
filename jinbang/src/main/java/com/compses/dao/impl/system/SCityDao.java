package com.compses.dao.impl.system;

import com.compses.dao.impl.BaseCommonDAO;
import com.compses.dao.system.IsCityDao;
import com.compses.model.TBaseCity;
import com.compses.util.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
@Repository
public class SCityDao extends BaseCommonDAO implements IsCityDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TBaseCity getByName(String cityName){
        String sql= DBUtils.getSql("TBaseCity", "getByName");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("cityName", cityName);
        return DBUtils.queryForObject(sql, paramMap, namedParameterJdbcTemplate, TBaseCity.class);
    }
}
