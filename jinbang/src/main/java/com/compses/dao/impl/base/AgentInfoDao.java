package com.compses.dao.impl.base;

import com.compses.dao.base.IAgentInfoDao;
import com.compses.dao.impl.BaseCommonDAO;
import com.compses.model.AgentInfo;
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
public class AgentInfoDao extends BaseCommonDAO implements IAgentInfoDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AgentInfo getByParentId(String parentId){
        String sql= DBUtils.getSql("AgentInfo", "getByParentId");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("parentId", parentId);
        return  DBUtils.queryForObject(sql,  paramMap, namedParameterJdbcTemplate, AgentInfo.class);
    }

    public AgentInfo getParent(String agentId){
        String sql= DBUtils.getSql("AgentInfo", "getParent");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("agentId", agentId);
        return  DBUtils.queryForObject(sql,  paramMap, namedParameterJdbcTemplate, AgentInfo.class);
    }

    public AgentInfo getByMobile(String mobile){
        String sql= DBUtils.getSql("AgentInfo", "getByMobile");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("mobile", mobile);
        return  DBUtils.queryForObject(sql,  paramMap, namedParameterJdbcTemplate, AgentInfo.class);
    }
}
