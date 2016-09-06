package com.club.web.client.controller;

import com.alibaba.fastjson.JSON;
import com.club.core.common.Page;
import com.club.framework.exception.BaseAppException;
import com.club.framework.log.ClubLogManager;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;
import com.club.web.client.service.IAccountService;
import com.club.web.client.service.IClientService;
import com.club.web.client.service.IIntegralMangerService;
import com.club.web.common.service.IBaseService;
import com.club.web.datamodel.service.IWfDbTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <Description>自动生成代码 <br>
 * 
 * @author lifei<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2014年12月11日 <br>
 * @since V1.0<br>
 */

@Controller
@RequestMapping("/accountManger")
public class AccountController {

    private static final ClubLogManager logger = ClubLogManager
            .getLogger(StaffController.class);

	@Autowired
	private IBaseService baseService;
	@Autowired
	private IClientService clientService;
	@Autowired
	private IWfDbTableService wfDbTableService;
	@Autowired
	private IIntegralMangerService integralMangerService;
	@Autowired
	private IAccountService accountService;

	@RequestMapping("exchangeInfoPage")
	@ResponseBody
	public Page<Map<String,Object>> exchangeInfoPage(Page<Map<String,Object>> page,String conditionStr) throws BaseAppException {
		logger.debug("exchangeInfoPage ");

		if (conditionStr!=null){
			page.setConditons(JsonUtil.toMap(conditionStr));
		}

		page = baseService.selectPage("client_exchange",page.getConditons());

		return page;
	}

	/**
	 * 会员提现/充值接口
	 * @return
	 * @throws BaseAppException
	 */
	@RequestMapping("recharge")
	@ResponseBody
	public Map<String,Object> recharge(Integer clientId, Float total_fee,String subject,
									   String out_trade_no,String trade_no,String trade_status,String seller_email,String buyer_email) throws BaseAppException {
		logger.debug("recharge ");
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			accountService.recharge( clientId,  total_fee, subject,
					 out_trade_no, trade_no, trade_status, seller_email, buyer_email,1);
			result.put("success", true);

		} catch (BaseAppException e) {
			result.put("success", false);
			result.put("msg", e.getMessage() + e.toString());
		}
		return result;
	}

	@RequestMapping("cash")
	@ResponseBody
	public Map<String,Object> cash(
			@RequestParam(value = "bizId",required = true) Integer bizId,@RequestParam(value = "money",required = true) Double money) {
		logger.debug("loadClientInfo ");
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			result.putAll(accountService.cash(bizId,money));
			result.put("success",true);
		} catch (BaseAppException e) {
			result.put("success",false);
			result.put("msg", e.getMessage() + e.toString());
		}
		return result;
	}


	@RequestMapping("queryClientExchange")
	@ResponseBody
	public Page<Map<String,Object>> queryClientExchange(Integer bizId,Integer start,Integer limit) throws BaseAppException{
		return accountService.queryClientExchange(bizId,start,limit);
	}

}
