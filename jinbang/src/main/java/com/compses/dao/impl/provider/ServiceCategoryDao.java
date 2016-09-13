package com.compses.dao.impl.provider;

import com.compses.dao.impl.BaseCommonDAO;
import com.compses.dao.provider.IServiceCategoryDao;
import com.compses.model.ServiceCategory;
import com.compses.util.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/30 0030.
 */
@Repository
public class ServiceCategoryDao extends BaseCommonDAO implements IServiceCategoryDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

//    public ServiceCategory getById(String serviceCategoryId){
//        String sql= DBUtils.getSql("ServiceCategory", "getById");
//        Map<String,Object> paramMap=new HashMap<String, Object>();
//        paramMap.put("serviceCategoryId", serviceCategoryId);
//        return  DBUtils.queryForObject(sql,  paramMap, namedParameterJdbcTemplate, ServiceCategory.class);
//    }
}
