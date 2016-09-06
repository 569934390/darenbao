package com.club.web.alipay.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.web.alipay.service.AlipayService;
import com.club.web.store.service.RechargeNoteService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/aliPayController")
public class AliPayController {
	
	@Autowired 
	private AlipayService alipayService;
	@Autowired 
	private RechargeNoteService rechargeNoteService;
	
	/**
	 * 生成支付宝订单
	 * @param bizId 会员ID
	 * @param MemberNo 会员编号
	 * @param reachedLevel 当前等级
	 * @param unitLevel 目标等级
	 * @param renewYear 续费年数
	 * @param flag 标记位  U为会员升级  R为会员续费  O为订单
	 * @return 订单信息
	 * @throws IOException
	 */
	@RequestMapping("/alipay")
	@ResponseBody
	public String getPayInfo(@RequestParam(value="bizId",defaultValue="0")int bizId, String MemberNo,String reachedLevel,
			String unitLevel,String renewYear,String flag,@RequestParam(value="orderId",defaultValue="0")Long orderId,
			HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		int renewYearInt = 0;
		if(renewYear != null && !renewYear.isEmpty()){
			renewYearInt = Integer.valueOf(renewYear);			
		}
		JSONObject jsonObject = new JSONObject();
		String jsonParam = "";
		try {
			jsonParam = alipayService.getPayInfo(bizId, MemberNo, reachedLevel, unitLevel, renewYearInt, flag,orderId);
			jsonObject.put("param", jsonParam);
			jsonObject.put("code", 1);
			jsonObject.put("msg", "数据返回成功");
		} catch (Exception e) {
			jsonObject.put("code", 0);
			jsonObject.put("msg", e.getMessage());
		}
		
		return jsonObject.toString();
	}
	
	/**
	 * 测试方法，之后删除
	 * @param response
	 */
	@RequestMapping("/updateOrderState")
	@ResponseBody
	public void updateOrderState(HttpServletResponse response) {
//		String bizId = "1022";
//		String flag = "U";
//		String levelId = "2";
//		String renewYear = "0";
		String moneys = "0.01";
		String buyerId = "buyerId2233DD";
		String outTradeNo = "236215862005948416";
		String tradeNo = "12345678";
		String tradeStatus = "TRADE_FINISHED";
		String buyerEmail = "23677@QQ.COM";
		String body = "bizId=1343,flag=U,unitLevel=2,renewYear=3";//,unitLevel=2,renewYear=3
		try {
			alipayService.updateOrderState(body, moneys, buyerId, outTradeNo, tradeNo, tradeStatus, buyerEmail,"卖家id" ,"2");			
		} catch (Exception e) {
			System.out.println("回调失败："+e.getMessage());
		}
	}
	
	/**
	 * 测试方法，之后删除
	 * @param response
	 */
	@RequestMapping("/findByOrderNum")
	@ResponseBody
	public void findByOrderNum(HttpServletResponse response) {
		String outTradeNo = "201604121048541";
		try {
			alipayService.findByOrderNum(outTradeNo);			
		} catch (Exception e) {
			System.out.println("查询是否存在订单异常："+e.getMessage());
		}
	}
	
	@RequestMapping("/addRechargeNote")
	@ResponseBody
	public void addRechargeNote(HttpServletResponse response) {
		String paymentAmount = "0.51";
		String buyerClient = "buyerId2233DD";
		String buyerAccount = "23677@QQ.COM";
		String indentId = "236215862005948416";
		String payType = "2";
		String body = "bizId=1343,flag=U,unitLevel=2,renewYear=3";//,unitLevel=2,renewYear=3
		try {
			rechargeNoteService.addRechargeNote(body, indentId, paymentAmount, payType, buyerClient, buyerAccount);	
		} catch (Exception e) {
			System.out.println("查询是否存在订单异常："+e.getMessage());
		}
	}
}