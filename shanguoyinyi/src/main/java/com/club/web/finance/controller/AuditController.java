package com.club.web.finance.controller;

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
import com.club.web.finance.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

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
@RequestMapping("/audit")
public class AuditController {

    private static final ClubLogManager logger = ClubLogManager
            .getLogger(AuditController.class);

	@Autowired
	private AuditService auditService;

	@RequestMapping("income")
	@ResponseBody
	public List<Map<String,Object>> income(String startTime,String endTime) throws BaseAppException {
		logger.debug("income ");

		List<Map<String,Object>> incomes= new ArrayList<Map<String, Object>>();

		for (int i=0;i<50;i++){
			Map<String,Object> income = new HashMap<String, Object>();
			income.put("orderId",i);
			income.put("money",100* new Random().nextInt());
			if (i%2==1) {
				income.put("consumer", 555);
			}
			income.put("type",1);//1：应收，2应付
			if (i%2==0) {
				income.put("recommend", 555);
			}
			income.put("orderDate",new Date());
			incomes.add(income);
		}

		return incomes;
	}

	@RequestMapping("updateAccountState")
	@ResponseBody
	public Map<String,Object> updateAccountState(
			@RequestParam(value = "bizIdStr",required = true) String bizIdStr,
			@RequestParam(value = "action",required = true) String action) {
		logger.debug("updateClientState ");
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			result.put("data", auditService.updateAccountState(bizIdStr,action));
			result.put("success",true);
		} catch (BaseAppException e) {
			result.put("success",false);
			result.put("msg", e.getMessage() + e.toString());
		}
		return result;
	}

	@RequestMapping("updateBillState")
	@ResponseBody
	public Map<String,Object> updateBillState(
			@RequestParam(value = "bizIdStr",required = true) String bizIdStr,
			@RequestParam(value = "action",required = true) String action) {
		logger.debug("updateClientState ");
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			result.put("data", auditService.updateBillState(bizIdStr, action));
			result.put("success",true);
		} catch (BaseAppException e) {
			result.put("success",false);
			result.put("msg", e.getMessage() + e.toString());
		}
		return result;
	}

	@RequestMapping("updateItemState")
	@ResponseBody
	public Map<String,Object> updateItemState(
			@RequestParam(value = "bizIdStr",required = true) String bizIdStr,
			@RequestParam(value = "action",required = true) String action) {
		logger.debug("updateClientState ");
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			result.put("data", auditService.updateItemState(bizIdStr,action));
			result.put("success",true);
		} catch (BaseAppException e) {
			result.put("success",false);
			result.put("msg", e.getMessage() + e.toString());
		}
		return result;
	}

	@RequestMapping("updateClientExchangeState")
	@ResponseBody
	public Map<String,Object> updateClientExchangeState(
			@RequestParam(value = "bizIdStr",required = true) String bizIdStr,
			@RequestParam(value = "action",required = true) String action) {
		logger.debug("updateClientState ");
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			result.put("data", auditService.updateClientExchangeState(bizIdStr,action));
			result.put("success",true);
		} catch (BaseAppException e) {
			result.put("success",false);
			result.put("msg", e.getMessage() + e.toString());
		}
		return result;
	}

	@RequestMapping("saveOrUpdateAccount")
	@ResponseBody
	public Map<String,Object> saveOrUpdateAccount(String modelJson) {
		logger.debug("saveOrUpdateAccount ");
		Map<String,Object> result = new HashMap<String, Object>();

		if (modelJson!=null) {
			Map<String, Object> clientMap = JsonUtil.toMap(modelJson);
			clientMap.put("state_time",new Date());
			try {
				result.put("data", auditService.saveOrUpdateAccount(clientMap));
				result.put("success",true);
			} catch (BaseAppException e) {
				result.put("success",false);
				result.put("msg", e.getMessage() + e.toString());
			}
		}
		else{
			result.put("success",false);
			result.put("msg","对象为空，请确认modelJson参数不为空，且为json格式");
		}

		return result;
	}

	@RequestMapping("saveOrUpdateBill")
	@ResponseBody
	public Map<String,Object> saveOrUpdateBill(String modelJson) {
		logger.debug("saveOrUpdateBill ");
		Map<String,Object> result = new HashMap<String, Object>();

		if (modelJson!=null) {
			Map<String, Object> clientMap = JsonUtil.toMap(modelJson);
			clientMap.put("state_time",new Date());
			try {
				result.put("data", auditService.saveOrUpdateBill(clientMap));
				result.put("success",true);
			} catch (BaseAppException e) {
				result.put("success",false);
				result.put("msg", e.getMessage() + e.toString());
			}
		}
		else{
			result.put("success",false);
			result.put("msg","对象为空，请确认modelJson参数不为空，且为json格式");
		}

		return result;
	}

	@RequestMapping("saveOrUpdateItem")
	@ResponseBody
	public Map<String,Object> saveOrUpdateItem(String modelJson) {
		logger.debug("saveOrUpdateItem ");
		Map<String,Object> result = new HashMap<String, Object>();

		if (modelJson!=null) {
			Map<String, Object> clientMap = JsonUtil.toMap(modelJson);
			clientMap.put("state_time",new Date());
			try {
				result.put("data", auditService.saveOrUpdateItem(clientMap));
				result.put("success",true);
			} catch (BaseAppException e) {
				result.put("success",false);
				result.put("msg", e.getMessage() + e.toString());
			}
		}
		else{
			result.put("success",false);
			result.put("msg","对象为空，请确认modelJson参数不为空，且为json格式");
		}

		return result;
	}

}
