package com.compses.dao.impl.base;

import com.compses.dao.base.IBankCardInfoDao;
import com.compses.dao.impl.BaseCommonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
@Repository
public class BankCardInfoDao extends BaseCommonDAO implements IBankCardInfoDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
}
