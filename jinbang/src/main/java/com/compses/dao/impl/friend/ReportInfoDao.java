package com.compses.dao.impl.friend;

import com.compses.dao.friend.IReportInfoDao;
import com.compses.dao.impl.BaseCommonDAO;
import com.compses.dto.Page;
import com.compses.model.ReportInfo;
import com.compses.util.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
@Repository
public class ReportInfoDao extends BaseCommonDAO implements IReportInfoDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Page<ReportInfo> listPageForReport(String userId, Page<ReportInfo> page){
        String sql= DBUtils.getSql("ReportInfo", "selectList");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("userId", userId);
        return  DBUtils.queryPage(sql, page, paramMap, namedParameterJdbcTemplate, ReportInfo.class);
    }


}
