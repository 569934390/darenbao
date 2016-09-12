package com.compses.dao.impl.system;

import com.compses.dao.system.IAboutUsDao;
import com.compses.model.AboutUs;
import com.compses.util.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nini on 2016/3/19.
 */

@Repository
public class AboutUsDao implements IAboutUsDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AboutUs selectOneByVersion(String versionId,String type){
        String sql= DBUtils.getSql("AboutUs", "selectOneByVersion");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("versionId", versionId);
        paramMap.put("type", type);
        return DBUtils.queryForObject(sql, paramMap, namedParameterJdbcTemplate, AboutUs.class);
    }
}
