package com.compses.dao.impl.system;

import com.compses.dao.system.IDictDao;
import com.compses.model.Dict;
import com.compses.util.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nini on 2016/3/11.
 */

@Repository
public class DictDao implements IDictDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Dict> selectDictList(String type,int pid){
        String sql= DBUtils.getSql("Dict", "selectDictList");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("types", type);
        paramMap.put("pid", pid);
        return DBUtils.query(sql, paramMap, namedParameterJdbcTemplate, Dict.class);
    }

    public List<Dict> selectAll(){
        String sql= DBUtils.getSql("Dict", "selectAll");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        return DBUtils.query(sql, paramMap, namedParameterJdbcTemplate, Dict.class);
    }

}
