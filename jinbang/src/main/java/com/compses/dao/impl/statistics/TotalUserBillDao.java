package com.compses.dao.impl.statistics;

import com.compses.dao.impl.BaseCommonDAO;
import com.compses.dao.statistics.ITotalUserBillDao;
import com.compses.model.TotalUserBill;
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
public class TotalUserBillDao extends BaseCommonDAO implements ITotalUserBillDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TotalUserBill getByUserId(String userId){
        String sql= DBUtils.getSql("TotalUserBill", "getByUserId");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("userId", userId);
        return  DBUtils.queryForObject(sql,  paramMap, namedParameterJdbcTemplate, TotalUserBill.class);
    }
}
