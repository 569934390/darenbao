package com.club.web.client.service.impl;

import com.club.core.common.Page;
import com.club.core.db.dao.BaseDao;
import com.club.core.spring.context.SpringApplicationContextHolder;
import com.club.framework.exception.BaseAppException;
import com.club.framework.log.ClubLogManager;
import com.club.framework.util.HttpUtils;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;
import com.club.web.client.service.IAccountService;
import com.club.web.client.service.IClientService;
import com.club.web.client.service.IIntegralMangerService;
import com.club.web.common.service.IBaseService;
import com.club.web.finance.service.AuditService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;
import scala.Int;

import java.net.URLEncoder;
import java.util.*;


@Service
public class AccountServiceImpl implements IAccountService {

    private static final ClubLogManager logger = ClubLogManager
            .getLogger(AccountServiceImpl.class);

    public static final String QUERY_CLIENT_LEVEL_SQL = "select c.BIZ_ID as clientId,c.parent_client_id,l.* from client c left join client_level l on c.level_id = l.biz_id where c.PARENT_CLIENT_ID = #{bizId} ";

    final public static String QUERY_SUB_CLIENT_COUNT_SQL = "select count(1) as total from client c where c.PARENT_CLIENT_ID = #{bizId}";

    final public static String QUERY_ONE_CLIENT_AND_LEVEL_SQL = "select c.*,l.name,l.ORDER_SORT,l.lng,l.lat,l.LNG_NUM,l.LAT_NUM from client c left join client_level l on c.level_id=l.biz_id  where c.biz_id = #{bizId}";

    final public static String QUERY_UP_CLIENT_LEVEL_SQL = "select l.biz_id from client_level l where l.order_sort< #{orderSort} order by l.order_sort DESC";

    final public static String QUERY_CLIENT_EXCHANGE_SQL = "select t.*,c.bank_number,c.bank_area from client_exchange t left join client c on t.client_id=c.biz_id where t.client_id = #{bizId} order by t.PER_RAGE desc";

    @Autowired
    private BaseDao baseDao;
    @Autowired
    private IBaseService baseService;
    @Autowired
    private IClientService clientService;
    @Autowired
    private AuditService auditService;

    public void countClientParentIntegeral(Map<String,Object> client,Integer clientId,Double money,int deep,String orderId,Map<String,Object> ruleInfo) throws BaseAppException {

        double payforRate1 = Double.parseDouble(ruleInfo.get("rechargeIntegral1") + "");
        double payforRate2 = Double.parseDouble(ruleInfo.get("rechargeIntegral2") +"");
        double payforrate = payforRate2/payforRate1;
        /**
         * 充值送积分
         */
        if (deep==1){
            Map<String,Object> integral = new HashMap<String, Object>();
            integral.put("consumerId",clientId);
            integral.put("incomingId",clientId);
            integral.put("rul_rule_id",2);
            integral.put("createDate",new Date());
            integral.put("orderId",orderId);
            integral.put("point",Math.round(payforrate*money));
            integral.put("money",money);
            integral.put("payFor",1);
            integral.put("state","00A");
            baseService.insert("integral",integral);
        }

        /**
         * 根据层级送积分
         */
        if (!client.get("parentClientId").equals("-1")&&deep<=6){
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("bizId",client.get("parentClientId"));
            List<Map<String,Object>> clients = baseService.selectList("client", params);
            if (clients.size()>0) {
                countClientParentIntegeral(StringUtils.toHump(clients.get(0)),clientId, money, deep+1, orderId, ruleInfo);

                Long point = 0L;
                if (deep>3){
                    point = Long.parseLong(ruleInfo.get("manage" + (deep-3))+"");
                }
                else {
                    Map<String,Object> level = JsonUtil.toMap(ruleInfo.get("level"+deep)+"");
                    Double rate = Double.parseDouble(level.get("levelRate_" + clients.get(0).get("levelId")) + "");
                    point = Math.round(rate/100*payforrate*money);
                }

                Map<String,Object> integral = new HashMap<String, Object>();
                integral.put("consumerId",clientId);
                integral.put("incomingId",client.get("parentClientId"));
                integral.put("rul_rule_id",2);
                integral.put("createDate",new Date());
                integral.put("orderId",orderId);
                integral.put("point",point);
                integral.put("money",money);
                integral.put("payFor",1);
                integral.put("state","00A");
                baseService.insert("integral",integral);
            }
        }

    }

    //返回码取值：00A:已审核00B:待充值00C:待审核00D:禁言00X:已删除
    public void countClientParentLevel(Integer clientId,String action) throws BaseAppException{
        if (action.equals("00D"))return;

        Map<String,Object> client = new HashMap<String, Object>();
        client.put("bizId",clientId);
        countClientParentLevel(client,action);
    }

    public Integer getUpLevel(String orderSort) throws BaseAppException {
        Map<String,Object> level = new HashMap<String, Object>();
        level.put("orderSort",orderSort);
        List<Map<String,Object>> list = baseService.selectList(level,QUERY_UP_CLIENT_LEVEL_SQL);
        if (list.size()>0){
            list = StringUtils.toHump(list);
            return Integer.parseInt(list.get(0).get("bizId") + "");
        }
        return null;
    }
    public void countClientParentLevel(Map<String,Object> client,String action) throws BaseAppException {

        client = baseService.selectOne(client,QUERY_ONE_CLIENT_AND_LEVEL_SQL);

        client = StringUtils.toHump(client);

        if (client.get("state").toString().equals("00A")&&action.equals("00A")){
            return;
        }
        if (!client.get("state").toString().equals("00A")&&!action.equals("00A")){
            return;
        }
        client.put("state",action);

        int subNums = 0;

        if (client.containsKey("subNums")){
            subNums = Integer.parseInt(client.get("subNums") + "");
        }

        if (action.equals("00A")){
            subNums+=1;
        }
        else {
            subNums-=1;
        }

        Map<String,Object> _c = new HashMap<String, Object>();

        _c.put("bizId",client.get("bizId"));
        _c.put("subNums",subNums);

        boolean upFlag = false;

        if (client.get("lng").toString().equals("1")){//横向
            Map<String,Object> counts = baseService.selectOne(client,QUERY_SUB_CLIENT_COUNT_SQL);
            int lngNum = Integer.parseInt(client.get("lngNum")+"");
            int count = Integer.parseInt(counts.get("total")+"");
            if (count>=lngNum){
                upFlag = true;
            }
        }
        if (client.get("lat").toString().equals("2")){
            int latNum = Integer.parseInt(client.get("latNum")+"");
            if (subNums>latNum){
                upFlag = true;
            }
        }

        if (upFlag) {
            _c.put("levelId", getUpLevel(client.get("orderSort") + ""));
        }

        baseService.update("client",_c);

        /**
         * 根据层级送积分
         */
        if (!client.get("parentClientId").equals("-1")){
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("bizId",client.get("parentClientId"));

            countClientParentLevel(params,action);
        }

    }

    protected void messageTest() {
        System.out.println(new Date());
        String token = "3J0dwS8FDs6aD1HLKMhHWkYLr3Zarbky";
        String verifyCode = "【凯渥会员】验证码 1234";
        String phoneCode = "15160034743";

        String query = "/msgservice/sms/sendmsg?userId=212558791940096000&mobile="+phoneCode+"&content="+verifyCode;
        String sign = StringUtils.md5(query+token);
        String querys = URLEncoder.encode("userId=212558791940096000&mobile=" + phoneCode + "&content=" + verifyCode + "&sing=" + sign);
        System.out.println(querys);
        String result = HttpUtils.sendGet("http://115.159.25.170:8080/msgservice/sms/sendmsg", "userId=212558791940096000&mobile=" + phoneCode + "&content=" + verifyCode + "&sign=" + sign);

        System.out.println(result);

    }

    protected void executeAnaysisSale() {

        IIntegralMangerService integralMangerService = SpringApplicationContextHolder.getBean(IIntegralMangerService.class);

        Map<String, Object> rule = (Map<String, Object>) integralMangerService.loadGenralSql();

        Map<String, Object> ruleInfo = JsonUtil.toMap(rule.get("RULE_INFO") + "");
        double payforRate1 = Double.parseDouble(ruleInfo.get("rechargeIntegral1") + "");
        double payforRate2 = Double.parseDouble(ruleInfo.get("rechargeIntegral2") +"");
        double payforrate = payforRate2/payforRate1;

        String startTime = new Date().getTime()+"";
        String endTime = new Date().getTime()+"";
        String result = HttpUtils.sendGet("http://localhost:38080/iframe/audit/income.do", "startTime=" + startTime + "&endTime=" + endTime);

        List<Map<String,Object>> results = JsonUtil.toBean(result, ArrayList.class);

        System.out.println(results);
        try {
            for (Map<String,Object> income:results){
                int money = Integer.parseInt(income.get("money")+"");
                if (income.containsKey("consumer")){
                    System.out.println("自消费");
                    Map<String,Object> client = new HashMap<String, Object>();
                    client.put("bizId",income.get("consumer"));
                    for (int i=0;i<3;i++) {//计算三层
                        double sales = Double.parseDouble(ruleInfo.get("sales" + (i + 1)) + "");
                        Map<String,Object> integral = new HashMap<String, Object>();
                        integral.put("consumerId",client.get("bizId"));
                        integral.put("incomingId",client.get("parentClientId"));
                        integral.put("rul_rule_id",2);
                        integral.put("createDate",new Date());
                        integral.put("orderId",income.get("orderId"));
                        integral.put("point",Math.round(payforrate * sales* money));
                        integral.put("money",money);
                        integral.put("payFor",1);
                        integral.put("state","00A");
                        baseService.insert("integral",integral);
                        Map<String, Object> parent = baseService.selectOne(client, QUERY_CLIENT_LEVEL_SQL);
                        if (parent==null)break;
                        client.put("bizId",parent.get("bizId"));
                    }

                }
                else{
                    System.out.println("推荐消费");
//                    Map<String,Object> integral = new HashMap<String, Object>();
//                    integral.put("consumerId",client.get("bizId"));
//                    integral.put("incomingId",client.get("parentClientId"));
//                    integral.put("rul_rule_id",2);
//                    integral.put("createDate",new Date());
//                    integral.put("orderId",orderId);
//                    integral.put("point",point);
//                    integral.put("money",money);
//                    integral.put("payFor",1);
//                    integral.put("state","00A");
//                    baseService.insert("integral",integral);
//                    Map<String,Object> client = new HashMap<String, Object>();
//                    client.put("bizId",income.get("consumer"));
//                    Map<String, Object> parent = baseService.selectOne(client, QUERY_CLIENT_LEVEL_SQL);
//                    Map<String,Object> integral = new HashMap<String, Object>();
//                    integral.put("consumerId",clientId);
//                    integral.put("incomingId",client.get("parentClientId"));
//                    integral.put("rul_rule_id",2);
//                    integral.put("createDate",new Date());
//                    integral.put("orderId",orderId);
//                    integral.put("point",point);
//                    integral.put("money",money);
//                    integral.put("payFor",1);
//                    integral.put("state","00A");
//                    baseService.insert("integral",integral);

                }
            }
        } catch (BaseAppException e) {
            e.printStackTrace();
        }

    }

    /**
     * 会员充值接口
     * @param clientId
     * @param total_fee
     * @param subject
     * @param out_trade_no
     * @param trade_no
     * @param trade_status
     * @param seller_email
     * @param buyer_email
     * @return
     * @throws BaseAppException
     */
    @Override
    public Object recharge(Integer clientId, Float total_fee,String subject, String out_trade_no,String trade_no,
                           String trade_status,String seller_email,String buyer_email,int itemId) throws BaseAppException {
        if ("TRADE_FINISHED".equals(trade_status)||("TRADE_SUCCESS".equals(trade_status))) {
            Map<String, Object> bill = new HashMap<String, Object>();
            bill.put("bizId", clientId);
            bill.put("accountId", 1);
            bill.put("itemId", itemId);
            bill.put("type", 1);
            bill.put("inClient", -1);
            bill.put("outClient", subject);//付款方
            bill.put("outAccount", buyer_email);//付款账号
            bill.put("orderCredit", trade_no);//交易凭证
            bill.put("outClient", seller_email);
            bill.put("orderId", out_trade_no);
            bill.put("money", total_fee);
            bill.put("createDate", new Date());
            bill.put("modifyTime", new Date());
            bill.put("state", "00A");
            Map<String, Object> clientVo = clientService.loadClientInfo(clientId);
            this.countClientParentLevel(clientId, clientVo.get("STATE").toString());

            return auditService.saveOrUpdateBill(bill);
        }
        return "支付未完成，请重新支付！";
    }
    /**
     * 会员提现接口
     * @param bizId
     * @param money
     * @return
     * @throws BaseAppException
     */
    @Override
    public Map<String, Object> cash(Integer bizId, Double money) throws BaseAppException {
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("success",false);
        Map<String,Object> client = clientService.loadClientInfo(bizId);
        client = StringUtils.toHump(client);
        if (client.containsKey("money")&&StringUtils.isNotObjectEmpty(client.get("money"))){
            Float clientMoney = Float.parseFloat(client.get("money").toString());
            if (money>clientMoney){
                result.put("msg","余额不足，提现失败");
            }
            else{
                Map<String,Object> clientExchange = new HashMap<String, Object>();
                clientExchange.put("clientId",bizId);
                clientExchange.put("applyMoney",money);
                clientExchange.put("countMoney",client.get("money"));
                clientExchange.put("perRage",new Date());
                clientExchange.put("rechargeRage",new Date());
                clientExchange.put("createDate",new Date());
                clientExchange.put("state","00B");//待审核
                clientExchange.put("creater", bizId);
                auditService.saveOrUpdateClientExchange(clientExchange);
                result.put("success",true);
            }
        }
        return result;
    }
    @Override
    public String upLevel(Integer clientId, Integer levelId) throws BaseAppException {
        Map<String,Object> client = new HashMap<String, Object>();
        client.put("bizId",clientId);
        client.put("levelId",levelId);
        return baseService.saveOrUpdate(client);
    }

    public Page<Map<String,Object>> queryClientExchange(Integer bizId,Integer start,Integer limit) throws BaseAppException {
        Map<String,Object> clientExchange = new HashMap<String, Object>();
        clientExchange.put("sql",QUERY_CLIENT_EXCHANGE_SQL);
        clientExchange.put("bizId",bizId);
        if (start==null) start = 0;
        if (limit==null) limit = 20;
        clientExchange.put("start",start);
        clientExchange.put("limit",limit);
        Page<Map<String,Object>> result = baseService.selectPage(clientExchange,true);
        for (Map<String,Object> exchage : result.getResultList()){
            if (exchage.containsKey("bankNumber")&&StringUtils.isNotObjectEmpty(exchage.get("bankNumber"))) {
                String bankNumber = exchage.get("bankNumber")+"";
                if (bankNumber.length()>4)
                    exchage.put("bankNumber", bankNumber.substring(bankNumber.length()-4));
            }
        }
        return result;
    }

    public static void main(String[] args){
        AccountServiceImpl imp = new AccountServiceImpl();
        imp.executeAnaysisSale();
    }

}
