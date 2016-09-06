package com.club.web.client.service.impl;

import com.club.core.db.dao.BaseDao;
import com.club.framework.exception.BaseAppException;
import com.club.framework.log.ClubLogManager;
import com.club.framework.util.StringUtils;
import com.club.web.client.service.IClientService;
import com.club.web.client.service.IIntegralMangerService;
import com.club.web.common.service.IBaseService;
import com.club.web.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class InegralMangerServiceImpl implements IIntegralMangerService {

    private static final ClubLogManager logger = ClubLogManager
            .getLogger(InegralMangerServiceImpl.class);

    final public static String QUERY_RULE_SET_GENGEAL_SQL = "select * from rule_set t  where t.RULE_TYPE = -1";


    @Autowired
    private BaseDao baseDao;
    @Autowired
    private IBaseService baseService;


    @Override
    public Object saveOrUpdateInfo(Map<String, Object> record) throws BaseAppException {

        Map<String,Object> result = new HashMap<String, Object>();
        if (!record.containsKey("ruleName")||StringUtils.isEmpty(record.get("ruleName"))){
            throw new BaseAppException("规则名称必填，不允许为空");
        }
        if (StringUtils.isEmpty(record.get("bizId"))) {
            record.put("create_date",new Date());
            record.put("bizId",-1);
            result.put("bizId",baseService.insert("rule_set", record));
        }
        else{
            baseService.update("rule_set", record);
            result.put("bizId", record.get("bizId"));
        }
        return result;
    }

    @Override
    public Object updateState(String bizIdStr,String action) throws BaseAppException {
        String[] bizIds = bizIdStr.split(",");
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("action",action);
        int count = 0;
        StringBuffer sql = new StringBuffer("update rule_set set state = #{action} where biz_id in( ");
        for (String bizId : bizIds){
            sql.append("#{bizId_"+count+"}");
            params.put("bizId_"+count,Integer.parseInt(bizId));
            count++;
            if (count<bizIds.length){
                sql.append(",");
            }
        }
        sql.append(" )");
        return baseDao.update(sql.toString(),params);
    }

    public Object loadGenralSql(){
        return baseDao.selectOne(QUERY_RULE_SET_GENGEAL_SQL,null);
    }
}
