package com.compses.dao.impl.system;

import com.compses.dao.system.IVersionDao;
import com.compses.dto.Page;
import com.compses.model.VersionInfo;
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
public class VersionInfoDao implements IVersionDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public VersionInfo getLastVersion(String type){
        String sql= DBUtils.getSql("VersionInfo", "selectLastOne");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("type",type);
        return  DBUtils.queryForObject(sql, paramMap, namedParameterJdbcTemplate, VersionInfo.class);
    }

    @Override
    public VersionInfo getById(int id) {
        String sql= DBUtils.getSql("VersionInfo", "selectOne");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("idkey", id);
        return DBUtils.queryForObject(sql, paramMap, namedParameterJdbcTemplate, VersionInfo.class);

    }

    @Override
    public Page<VersionInfo> selectDtoList(VersionInfo versionInfo, Page<VersionInfo> page) {
        String sql= DBUtils.getSql("VersionInfo", "selectList");
        return   DBUtils.queryPage(sql, page, versionInfo, namedParameterJdbcTemplate, VersionInfo.class);

    }

}
