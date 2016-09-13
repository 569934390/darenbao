package com.compses.dao.impl.statistics;

import com.compses.dao.impl.BaseCommonDAO;
import com.compses.dao.statistics.IBillRecordDao;
import com.compses.model.BillRecord;
import com.compses.util.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
@Repository
public class BillRecordDao extends BaseCommonDAO implements IBillRecordDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<BillRecord> listAllRecord(BillRecord billRecord){
        String sql= DBUtils.getSql("BillRecord", "listAllRecord");
        return  DBUtils.query(sql,  billRecord, namedParameterJdbcTemplate, BillRecord.class);
    }
}
