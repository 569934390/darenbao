package com.compses.dao.impl.base;

import com.compses.dao.base.IUserRelaDao;
import com.compses.dao.impl.BaseCommonDAO;
import com.compses.model.UserRela;
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
public class UserRelaDao extends BaseCommonDAO implements IUserRelaDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserRela getByUserId(String userId){
        String sql= DBUtils.getSql("UserRela", "getByUserId");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("userId", userId);
        return  DBUtils.queryForObject(sql,  paramMap, namedParameterJdbcTemplate, UserRela.class);
    }
}
