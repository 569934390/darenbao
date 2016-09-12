package com.compses.dao.impl.system;

import com.compses.dao.impl.BaseCommonDAO;
import com.compses.dao.system.ISDistrictDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/7/26 0026.
 */

@Repository
public class SDistrictDao extends BaseCommonDAO implements ISDistrictDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
}
