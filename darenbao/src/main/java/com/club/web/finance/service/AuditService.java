package com.club.web.finance.service;

import com.club.core.common.TreeNode;
import com.club.core.db.dao.BaseDao;
import com.club.framework.exception.BaseAppException;
import com.club.framework.log.ClubLogManager;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;
import com.club.web.client.service.IAccountService;
import com.club.web.client.service.IClientService;
import com.club.web.client.service.IIntegralMangerService;
import com.club.web.common.controller.IndexController;
import com.club.web.common.service.IBaseService;
import com.club.web.common.service.impl.BaseServiceImpl;
import com.club.web.privilege.enums.OperEnum;
import com.club.web.privilege.service.IOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Int;

import java.util.*;


@Service
public class AuditService {

    private static final ClubLogManager logger = ClubLogManager
            .getLogger(AuditService.class);
    final public static String UPDATE_ACCOUNT_STATE_SQL = "update account set state = #{action},modify_time = #{modifyTime} where account_id in( ";
    final public static String UPDATE_ITEM_STATE_SQL = "update FINANCE_ITEM set state = #{action},modify_time = #{modifyTime} where item_id in( ";
    final public static String UPDATE_BILL_STATE_SQL = "update BILL set state = #{action},modify_time = #{modifyTime} where biz_id in( ";
    final public static String UPDATE_CLIENT_EXCHANGE_STATE_SQL = "update client_exchange set state = #{action},RECHARGE_RAGE = #{modifyTime} where biz_id in( ";

    @Autowired
    private BaseDao baseDao;
    @Autowired
    private IBaseService baseService;
    @Autowired
    private IOperLogService operLogService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IIntegralMangerService integralMangerService;

    public Object saveOrUpdateBill(Map<String, Object> record) throws BaseAppException {

        Map<String,Object> result = new HashMap<String, Object>();
        record.put("modifyTime",new Date());
        if (!StringUtils.isEmpty(record.get("bizId"))) {
            record.put("create_date",new Date());
            record.put("bizId",-1);
            result.put("bizId",baseService.insert("bill", record));

        }
        else{
            baseService.update("bill", record);
            result.put("bizId", record.get("bizId"));
        }
        return result;
    }

    public Object saveOrUpdateItem(Map<String, Object> record) throws BaseAppException {

        Map<String,Object> result = new HashMap<String, Object>();
        record.put("modifyTime",new Date());
        if (StringUtils.isEmpty(record.get("itemId"))) {
            record.put("create_date",new Date());
            record.put("itemId",-1);
            result.put("itemId",baseService.insert("finance_item", record));

        }
        else{
            baseService.update("finance_item", record);
            result.put("itemId", record.get("itemId"));
        }
        return result;
    }

    public Object saveOrUpdateAccount(Map<String, Object> record) throws BaseAppException {

        Map<String,Object> result = new HashMap<String, Object>();
        record.put("modifyTime",new Date());
        if (StringUtils.isEmpty(record.get("accountId"))) {
            record.put("create_date",new Date());
            record.put("accountId",-1);
            result.put("accountId",baseService.insert("account", record));
        }
        else{
            baseService.update("account", record);
            result.put("accountId", record.get("accountId"));
        }
        return result;
    }

    public Object updateAccountState(String bizIdStr,String action) throws BaseAppException {
        String[] bizIds = bizIdStr.split(",");
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("action",action);
        params.put("modifyTime",new Date());
        int count = 0;
        StringBuffer sql = new StringBuffer(UPDATE_ACCOUNT_STATE_SQL);
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

    public Object updateBillState(String bizIdStr,String action) throws BaseAppException {
        String[] bizIds = bizIdStr.split(",");
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("action",action);
        params.put("modifyTime",new Date());
        int count = 0;
        StringBuffer sql = new StringBuffer(UPDATE_BILL_STATE_SQL);
        Map<String, Object> rule = (Map<String, Object>) integralMangerService.loadGenralSql();
        for (String bizId : bizIds){
            sql.append("#{bizId_"+count+"}");
            params.put("bizId_"+count,Integer.parseInt(bizId));
            count++;
            if (count<bizIds.length){
                sql.append(",");
            }

            Map<String,Object> billConditon = new HashMap<String, Object>();
            billConditon.put("bizId",bizId);
            List<Map<String,Object>> bills = baseService.selectList("bill", billConditon);
            if (bills.size()>0) {
                if (bills.get(0).get("itemId").toString().equals("1")) {
                    Map<String, Object> client = new HashMap<String, Object>();
                    Integer clientId = Integer.parseInt(bills.get(0).get("outClient") + "");
                    Double money = Double.parseDouble(bills.get(0).get("money") + "");
                    client.put("bizId", clientId);
                    List<Map<String, Object>> clients = baseService.selectList("client", params);
                    if (clients.size() > 0) {
                        accountService.countClientParentIntegeral(StringUtils.toHump(clients.get(0)), clientId, money, 1, bizId, JsonUtil.toMap(rule.get("RULE_INFO") + ""));
                    }
                }
            }
        }
        sql.append(" )");
        return baseDao.update(sql.toString(),params);
    }

    public Object updateItemState(String bizIdStr,String action) throws BaseAppException {
        String[] bizIds = bizIdStr.split(",");
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("action",action);
        params.put("modifyTime",new Date());
        int count = 0;
        StringBuffer sql = new StringBuffer(UPDATE_ITEM_STATE_SQL);
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

    public Object saveOrUpdateClientExchange(Map<String, Object> record) throws BaseAppException {

        Map<String,Object> result = new HashMap<String, Object>();
        record.put("modifyTime",new Date());
        if (StringUtils.isEmpty(record.get("bizId"))) {
            record.put("create_date",new Date());
            record.put("bizId",-1);
            result.put("bizId",baseService.insert("client_exchange", record));
        }
        else{
            baseService.update("client_exchange", record);
            result.put("client_exchange", record.get("bizId"));
        }
        return result;
    }

    public Object updateClientExchangeState(String bizIdStr,String action) throws BaseAppException {
        String[] bizIds = bizIdStr.split(",");
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("action",action);
        params.put("modifyTime",new Date());
        int count = 0;
        StringBuffer sql = new StringBuffer(UPDATE_CLIENT_EXCHANGE_STATE_SQL);
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
}
