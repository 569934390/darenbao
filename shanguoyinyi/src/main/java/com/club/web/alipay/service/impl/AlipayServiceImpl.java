package com.club.web.alipay.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.framework.exception.BaseAppException;
import com.club.web.alipay.config.AlipayConfig;
import com.club.web.alipay.dao.AlipayDao;
import com.club.web.alipay.service.AlipayService;
import com.club.web.alipay.util.UtilDate;
import com.club.web.client.service.IAccountService;
import com.club.web.client.service.IClientService;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Service("alipayService")
public class AlipayServiceImpl implements AlipayService {
	
	private Logger logger = LoggerFactory.getLogger(AlipayServiceImpl.class);
	
	@Autowired
	private AlipayDao alipayDao;
	
	@Autowired
	private IClientService iClientService;
	
	@Autowired
	private IAccountService accountService;
	
	@Override
	public String getOrderNum() {
		
		return UtilDate.getOrderNum();
	}
	
	@Override
	public String getGoodsData(String goodsId) {
		//调用DAO层，查询产品资料
		return alipayDao.getGoodsData(goodsId);
	}
	
	@Override
	public void AddOrderData() {
		alipayDao.AddOrderData();
	}

	@Override
	public int findByOrderNum(String orderNum) {
		int index = 0;
//		try {
//			index = iClientService.findByOrderNum(orderNum);
//		} catch (BaseAppException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		return index;
	}
	
	@Override
	public void updateOrderState(String body, String moneys, String buyerId, String outTradeNo,
			String tradeNo,String tradeStatus,String buyerEmail,String sellerId, String payType) {
		
		String bizId = "0";
		String flag = "U";
		String levelId = "0";
		String renewYear = "0";
		String [] paramOut = body.split(",");
		if(paramOut != null && paramOut.length > 0){
			for(String value : paramOut){
				String [] param = value.split("=");
				if(param != null && param.length == 2){
					if("bizId".equals(param[0].replace("{", "").trim())){
						bizId = param[1].replace("'", "").replace("}", "").trim();
					} else if("flag".equals(param[0].replace("{", "").trim())){
						flag = param[1].replace("'", "").replace("}", "").trim();
					} else if("unitLevel".equals(param[0].replace("{", "").trim())){
						levelId = param[1].replace("'", "").replace("}", "").trim();
					} else if("renewYear".equals(param[0].replace("{", "").trim())){
						renewYear = param[1].replace("'", "").replace("}", "").trim();
					}
				}
			}
		}
				
		int bizIdInt = 0;
		if(bizId != null && !bizId.isEmpty()){
			bizIdInt = Integer.valueOf(bizId);
		}
		
		Float moneysFlt = null;
		if(moneys != null && !moneys.isEmpty()){
			moneysFlt = Float.valueOf(moneys);
		}
//		if("U".equals(flag)){
//			int levelIdInt = Integer.valueOf(levelId);
//			try {
//				iClientService.updateClientLevel(bizIdInt, flag, levelIdInt, 0);
//			} catch (BaseAppException e) {
//				e.printStackTrace();
//			}		
//		} else if("R".equals(flag)){
//			int clientValidity = Integer.valueOf(renewYear);
//			try {
//				iClientService.updateClientLevel(bizIdInt, flag, 0, clientValidity);
//			} catch (BaseAppException e) {
//				e.printStackTrace();
//			}
//		}
		
		//记流水账
		try {
			int itemId = 0;
			if("U".equals(flag)){
				itemId = 1;
            } else{
            	itemId = 8;       	
            }
			accountService.recharge(bizIdInt,  moneysFlt, buyerId,
					outTradeNo, tradeNo, tradeStatus, AlipayConfig.seller_id, buyerEmail, itemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getPayInfo(int bizId, String MemberNo,String reachedLevel,String unitLevel,int renewYear,String flag, Long orderId) throws Exception {
		Float moneys = (float) 0;	//总金额
		//根据会员编号和当前等级获取金额
//		Map<String,Object> ClientInfo = iClientService.loadClientByPhone(MemberNo);
//		Float reachedMoney = (Float) ClientInfo.get("levelMoney");
//		String levelId = (String) ClientInfo.get("levelId");
		JSONObject jsonObject = new JSONObject();
		
		if("U".equals(flag)){	//会员升级
			//根据会员编号和目标等级获取目标等级金额
			Float unitMoney = (float) 0;
			if(unitLevel != null && !unitLevel.isEmpty()){
//				Map<String,Object> clientLevel = iClientService.selectClientLevelByBizId(Integer.valueOf(unitLevel));
//				unitMoney = (Float) clientLevel.get("LEVEL_MONEY");
			}
//			moneys = unitMoney - reachedMoney;
			jsonObject.put("unitLevel",unitLevel);
		} else if("R".equals(flag)){
//			moneys = reachedMoney * renewYear;
			jsonObject.put("renewYear",renewYear);
		}
		//自定义参数
		jsonObject.put("bizId",bizId);
		jsonObject.put("reachedLevel",reachedLevel);
		jsonObject.put("flag", flag);
		
		//支付宝参数
		String order = UtilDate.getOrderNum();
		jsonObject.put("out_trade_no", order);	//订单号
		jsonObject.put("total_fee", "0.01");	//金额
		jsonObject.put("_input_charset", AlipayConfig.input_charset);
		jsonObject.put("service", "mobile.securitypay.pay");
		jsonObject.put("partner", AlipayConfig.partner);
		jsonObject.put("seller_id", AlipayConfig.seller_id);//115.159.122.88:8080
		jsonObject.put("notify_url", "http://115.159.25.170:8080/zuitese/garagemanpay/payNotifyUrl.jsp");	//回调地址
		jsonObject.put("payment_type", "1");
		jsonObject.put("body", "bizId="+bizId+",flag="+flag+",unitLevel="+unitLevel+",renewYear="+renewYear);
		
		return jsonObject.toString();
	}

	/**
	 * json格式转成map格式
	 * 
	 * @param otherPropertyJson
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> getMapByJson(String otherPropertyJson) {
		Map<String, Object> map = new HashMap<String, Object>();
		if ("".equals(otherPropertyJson)) {
			return map;
		}
		JsonConfig config = new JsonConfig();
		JSONObject object = JSONObject.fromObject(otherPropertyJson, config);
		Set keys = object.keySet();
		Iterator obj = keys.iterator();
		while (obj.hasNext()) {
			String key = (String) obj.next();
			map.put(key, object.get(key));
		}
		
		return map;
	}
}