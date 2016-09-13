package com.compses.dao.impl.base;

import com.compses.dao.base.IUserCardPwdDao;
import com.compses.dao.impl.BaseCommonDAO;
import com.compses.model.UserCardPwd;
import com.compses.util.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
@Repository
public class UserCardPwdDao extends BaseCommonDAO implements IUserCardPwdDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserCardPwd getByUserId(String userId){
        String sql= DBUtils.getSql("UserCardPwd", "getByUserId");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("userId", userId);
        return  DBUtils.queryForObject(sql,  paramMap, namedParameterJdbcTemplate, UserCardPwd.class);
    }

    public UserCardPwd getByMobile(String mobile){
        String sql= DBUtils.getSql("UserCardPwd", "getByMobile");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("mobile", mobile);
        return  DBUtils.queryForObject(sql,  paramMap, namedParameterJdbcTemplate, UserCardPwd.class);
    }
}
