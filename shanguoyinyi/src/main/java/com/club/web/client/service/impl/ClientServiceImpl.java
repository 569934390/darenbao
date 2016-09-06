package com.club.web.client.service.impl;

import com.club.core.common.TreeNode;
import com.club.core.db.dao.BaseDao;
import com.club.framework.exception.BaseAppException;
import com.club.framework.log.ClubLogManager;
import com.club.framework.util.StringUtils;
import com.club.web.client.service.IAccountService;
import com.club.web.client.service.IClientService;
import com.club.web.common.controller.IndexController;
import com.club.web.common.service.IBaseService;
import com.club.web.common.service.impl.BaseServiceImpl;
import com.club.web.privilege.enums.OperEnum;
import com.club.web.privilege.service.IOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ClientServiceImpl implements IClientService {

    final public static String QUERY_SUB_CLIENT_SQL = "select c.*,l.name from client c left join client_level l on c.level_id=l.biz_id where c.PARENT_CLIENT_ID = #{bizId}";
    final public static String UPDATE_CLIENT_STATE_SQL = "update client set state = #{action},state_time = #{stateTime} where biz_id in( ";
    final public static String UPDATE_CLIENT_LEVEL_STATE_SQL = "update client_level set state = #{action},create_date = #{createDate} where biz_id in( ";
    final public static String QUERY_CLIENT_INTEGRAL_SQL = "select sum(l.POINT) as points from INTEGRAL l where l.INCOMING_ID = #{bizId}";
    final public static String QUERY_CLIENT_INFO_SQL = "select c.*,l.NAME from CLIENT c left join CLIENT_LEVEL l on c.LEVEL_ID = l.BIZ_ID where c.biz_id = #{bizId}";
    private static final ClubLogManager logger = ClubLogManager
            .getLogger(BaseServiceImpl.class);

    @Autowired
    private BaseDao baseDao;
    @Autowired
    private IBaseService baseService;
    @Autowired
    private IOperLogService operLogService;
    @Autowired
    private IAccountService accountService;

    

    @Override
    public Map<String,Object> saveOrUpdateClientInfo(Map<String, Object> clientMap) throws BaseAppException {

        Map<String,Object> result = new HashMap<String, Object>();
        if (!clientMap.containsKey("phone")||StringUtils.isEmpty(clientMap.get("phone"))){
            result.put("success",false);
            result.put("msg","电话号码必填，不允许为空");
            return result;
        }

        if (StringUtils.isEmpty(clientMap.get("clientNumber"))){
            result.put("success", false);
            result.put("msg","会员编号必填，不允许为空");
            return result;
        }

        if (clientMap.containsKey("verifyCode")&&clientMap.get("verifyCode").equals("-1")){

        }
        else if (clientMap.containsKey("token")&&clientMap.get("token")!=null){
            if (!clientMap.containsKey("verifyCode")){
                result.put("success",false);
                result.put("msg","请输入短信验证码");
                return result;
            }
            if (!IndexController.phoneCodeCache.containsKey(clientMap.get("phone"))){
                result.put("success",false);
                result.put("msg","还没收到短信验证码？请重新发送");
                return result;
            }
            if (!IndexController.phoneCodeCache.get(clientMap.get("phone")).equals(clientMap.get("verifyCode"))){
                result.put("success",false);
                result.put("msg","短信验证码错误");
                return result;
            }
            if (clientMap.containsKey("parentClientId")&&StringUtils.isNotObjectEmpty(clientMap.get("parentClientId"))){
                if(!clientMap.get("parentClientId").toString().equals("-1")){
                    Map<String,Object> _client = loadClientByPhone(clientMap.get("parentClientId")+"");
                    if (_client==null){
                        result.put("success",false);
                        result.put("msg","推荐人不存在");
                        return result;
                    }
                    else{
                        clientMap.put("parentClientId",_client.get("bizId"));
                    }
                }
            }
            else{
                clientMap.put("parentClientId",-1);
            }
        }
        if (StringUtils.isEmpty(clientMap.get("bizId"))) {
            result.put("action","update");
            clientMap.put("create_date",new Date());
            Map<String,Object> _client = loadClientByPhone(clientMap.get("phone")+"");
            if (_client!=null) {
                result.put("success",false);
                result.put("msg","帐号重复注册！");
                return result;
            }
            else {
                result.put("action","insert");
                clientMap.put("bizId",-1);
                clientMap.put("subNums",0);
                if(!clientMap.containsKey("clientNumber")||clientMap.get("clientNumber")==null) {
                    clientMap.put("clientNumber", clientMap.get("phone"));
                }
                result.put("bizId",baseService.insert("client", clientMap));
                operLogService.saveOperate(clientMap.get("bizId") + "", clientMap.get("state")+"", "admin", OperEnum.INSERT_CLIENT, "更新");
            }
        }
        else{
            baseService.update("client", clientMap);
            result.put("bizId", clientMap.get("bizId"));
            operLogService.saveOperate(clientMap.get("bizId")+"",clientMap.get("state")+"","admin", OperEnum.UPDATE_CLIENT,"更新");
        }
        result.put("success", true);
        return result;
    }
    
    @Override
    public Map<String,Object> saveOrUpdateSubbranchClientInfo(Map<String, Object> clientMap) throws BaseAppException {

        Map<String,Object> result = new HashMap<String, Object>();
        if (!clientMap.containsKey("phone")||StringUtils.isEmpty(clientMap.get("phone"))){
            result.put("success",false);
            result.put("msg","电话号码必填，不允许为空");
            return result;
        }
        if (StringUtils.isEmpty(clientMap.get("bizId"))) {
            result.put("action","update");
            clientMap.put("create_date",new Date());
            Map<String,Object> _client = loadClientByPhone(clientMap.get("phone")+"");
            if (_client!=null) {
                result.put("success",false);
                result.put("msg","帐号重复注册！");
                result.put("BIZ_ID", _client.get("bizId").toString());
                return result;
            }
            else {
                result.put("action","insert");
                clientMap.put("bizId",-1);
                clientMap.put("subNums",0);
                if(!clientMap.containsKey("clientNumber")||clientMap.get("clientNumber")==null) {
                    clientMap.put("clientNumber", clientMap.get("phone"));
                }
                result=baseService.insert("client", clientMap);
                operLogService.saveOperate(clientMap.get("bizId") + "", clientMap.get("state")+"", "admin", OperEnum.INSERT_CLIENT, "新增");
            }
        }
        else{
            baseService.update("client", clientMap);
            result.put("BIZ_ID", clientMap.get("bizId"));
            operLogService.saveOperate(clientMap.get("bizId")+"",clientMap.get("state")+"","admin", OperEnum.UPDATE_CLIENT,"更新");
        }
        result.put("success", true);
        return result;
    }

    @Override
    public Object updateClientState(String bizIdStr,String action,String context) throws BaseAppException {
        String[] bizIds = bizIdStr.split(",");
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("action",action);
        params.put("stateTime",new Date());
        int count = 0;
        StringBuffer sql = new StringBuffer(UPDATE_CLIENT_STATE_SQL);
        for (String bizId : bizIds){
            sql.append("#{bizId_"+count+"}");
            int bizId_ = Integer.parseInt(bizId);
            params.put("bizId_"+count,bizId_);
            count++;
            if (count<bizIds.length){
                sql.append(",");
            }
            accountService.countClientParentLevel(bizId_,action);
        }
        sql.append(" )");
        operLogService.saveOperate(bizIdStr, action, "admin", OperEnum.STATE_CLIENT, context);

        return baseDao.update(sql.toString(),params);
    }

    public Map<String,Object> loadClientByPhone(String phone) throws BaseAppException {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("clientNumber",phone);
        List<Map<String,Object>> result = baseService.selectList("client", params);
        if (result.size()>0) {
            return result.get(0);
        }
        else{
            return null;
        }
    }

    @Override
    public Object saveOrUpdateLevelInfo(Map<String, Object> record) throws BaseAppException {

        Map<String,Object> result = new HashMap<String, Object>();
        if (!record.containsKey("name")||StringUtils.isEmpty(record.get("name"))){
            throw new BaseAppException("等级名称必填，不允许为空");
        }
        if (StringUtils.isEmpty(record.get("bizId"))) {
            record.put("create_date",new Date());
            result.put("action","insert");
            record.put("bizId",-1);
            result.put("bizId",baseService.insert("client_level", record));

        }
        else{
            baseService.update("client_level", record);
            result.put("bizId", record.get("bizId"));
        }
        return result;
    }

    @Override
    public Object updateLevelState(String bizIdStr,String action) throws BaseAppException {
        String[] bizIds = bizIdStr.split(",");
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("action",action);
        params.put("createDate",new Date());
        int count = 0;
        StringBuffer sql = new StringBuffer(UPDATE_CLIENT_LEVEL_STATE_SQL);
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

    /**
     * 获取会员6层子节点
     * @param client
     * @param deep
     * @return
     */
    public TreeNode loadSubClient(Map<String,Object> client,int deep,List<Map<String,Object>> subs){
        if (deep<0){
            return null;
        }
        List<Map<String,Object>> subClients = baseDao.selectSqlList(QUERY_SUB_CLIENT_SQL, client);
        subClients = StringUtils.toHump(subClients);
        client.put("sql",null);
        subs.addAll(subClients);
        TreeNode root = new TreeNode();
        root.setChildren(new ArrayList<TreeNode>());
        Map<String,Object> _client = new HashMap<String, Object>();
        _client.putAll(client);
        root.setAttributeMap(_client);
        for (Map<String,Object> subClient : subClients){
            TreeNode children = loadSubClient(subClient, deep - 1,subs);
            if (children!=null) {
                root.getChildren().add(children);
            }
        }
        return root;
    }

    public Map<String,Object> loadClientInfo(Long bizId) throws BaseAppException {
        Map<String,Object> result = null;
        Map<String,Object> client = new HashMap<String, Object>();
        client.put("bizId",bizId);
        result = baseService.selectOne(client,QUERY_CLIENT_INFO_SQL);
        result.put("points",countIntegral(client));
        return result;
    }

    @Override
    public Map<String, Object> cash(Integer bizId, Double money) throws BaseAppException {
        Map<String,Object> client = this.loadClientInfo(bizId);

        return null;
    }

    public Object countIntegral(Map<String,Object> client) throws BaseAppException {

        Map<String,Object> integral = baseService.selectOne(client, QUERY_CLIENT_INTEGRAL_SQL);

        if (integral!=null){
            return integral.get("points");
        }
        else{
            return 0;
        }
    }

}
