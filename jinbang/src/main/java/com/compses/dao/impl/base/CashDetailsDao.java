package com.compses.dao.impl.base;

import com.compses.dao.base.ICashDetailsDao;
import com.compses.dao.impl.BaseCommonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/8/29 0029.
 */

@Repository
public class CashDetailsDao extends BaseCommonDAO implements ICashDetailsDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
}
