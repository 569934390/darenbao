package com.club.web.client.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.exception.BaseAppException;
import com.club.framework.log.ClubLogManager;
import com.club.framework.util.JsonUtil;
import com.club.web.client.service.IClientService;
import com.club.web.common.controller.IndexController;
import com.club.web.common.service.IBaseService;
import com.club.web.datamodel.service.IWfDbTableService;

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
@RequestMapping("/client")
public class ClientController {

    private static final ClubLogManager logger = ClubLogManager
            .getLogger(ClientController.class);

	@Autowired
	private IBaseService baseService;
	@Autowired
	private IClientService clientService;
	@Autowired
	private IWfDbTableService wfDbTableService;

	@RequestMapping("saveOrUpdateClientInfo")
	@ResponseBody
	public Map<String,Object> saveOrUpdateClientInfo(String clientJson,String token) {
		logger.debug("saveOrUpdateClientInfo ");
		Map<String,Object> result = new HashMap<String, Object>();

		if (clientJson!=null) {
			Map<String, Object> clientMap = JsonUtil.toMap(clientJson);
			clientMap.put("state_time",new Date());
			clientMap.put("token",token);
			try {
				result.putAll(clientService.saveOrUpdateClientInfo(clientMap));
			} catch (BaseAppException e) {
				result.put("success",false);
				result.put("msg", e.getMessage());
			}
		}
		else{
			result.put("success",false);
			result.put("msg","会员对象为空，请确认clinetJson参数不为空，且为json格式");
		}

		return result;
	}

	@RequestMapping("login")
	@ResponseBody
	public Map<String,Object> login(@RequestParam(value = "clientNumber",
			required = true) String clientNumber,@RequestParam(value = "password",
			required = true) String password) {
		logger.debug("login ");
		Map<String,Object> result = new HashMap<String, Object>();

		try {
			Map<String, Object> clientMap = new HashMap<String, Object>();
			clientMap.put("clientNumber",clientNumber);
			clientMap.put("password",password);

			List<Map<String,Object>> clients = baseService.selectList("client",clientMap);
			if (clients.size()==0){
				result.put("success",false);
				result.put("msg","会员编号或密码错误");
			}
			else {
				result.put("success",true);
				result.put("client",clients.get(0));
			}
		} catch (BaseAppException e) {
			result.put("success",false);
			result.put("msg", e.getMessage() + e.toString());
		}

		return result;
	}

	@RequestMapping("resetPassword")
	@ResponseBody
	public Map<String,Object> login(@RequestParam(value = "verifyCode",
			required = true) String verifyCode,@RequestParam(value = "phone",
			required = true) String phone,@RequestParam(value = "password",
			required = true) String password,String token) {
		logger.debug("resetPassword ");
		Map<String,Object> result = new HashMap<String, Object>();

		try {
			if (!IndexController.phoneCodeCache.containsKey(phone)){
				result.put("success",false);
				result.put("msg","还没收到短信验证码？请重新发送");
			}
			else if (!IndexController.phoneCodeCache.get(phone).equals(verifyCode)){
				result.put("msg","短信验证码错误");
				result.put("success",false);
			}
			else {
				Map<String, Object> clientMap = new HashMap<String, Object>();
				clientMap.put("clientNumber", phone);

				List<Map<String, Object>> clients = baseService.selectList("client", clientMap);
				if (clients.size() == 0) {
					result.put("success", false);
					result.put("msg", "会员编号不存在");
				} else {
					result.put("success", true);
					Map<String, Object> client = clients.get(0);
					client.put("password", password);
					result.put("client", clientService.saveOrUpdateClientInfo(client));
				}
			}
		} catch (BaseAppException e) {
			result.put("success",false);
			result.put("msg", e.getMessage());
		}

		return result;
	}

	@RequestMapping("updateClientState")
	@ResponseBody
	public Map<String,Object> updateClientState(
			@RequestParam(value = "bizIdStr",required = true) String bizIdStr,
			@RequestParam(value = "action",required = true) String action,String context) {
		logger.debug("updateClientState ");
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			result.put("data", clientService.updateClientState(bizIdStr,action,context));
			result.put("success",true);
		} catch (BaseAppException e) {
			result.put("success",false);
			result.put("msg", e.getMessage() + e.toString());
		}
		return result;
	}

	@RequestMapping("clientInfoPage")
	 @ResponseBody
	 public Page<Map<String,Object>> clientInfoPage(@RequestParam(value = "name",
			required = true) String name,Page<Map<String,Object>> page,String conditionStr,HttpServletResponse response) throws BaseAppException {
		logger.debug("clientInfoPage ");

		if (conditionStr!=null){
			page.setConditons(JsonUtil.toMap(conditionStr));
		}
		page = wfDbTableService.getData(name, page,response);

//		for (Map<String,Object> record : page.getResultList()){
//			List<Map<String,Object>> subs = new ArrayList<Map<String, Object>>();
//			record.put("subClient",clientService.loadSubClient(record,6,subs));
//			float sellSum = 0 ;
//			float payfor = 0 ;
//			int point = 0;
//			int abspoint = 0;
//			for (Map<String,Object> sub : subs){
//				if (sub.containsKey("money")) {
//					sellSum += Float.parseFloat(sub.get("money") + "");
//				}
//				if (sub.containsKey("payfor")) {
//					payfor += Float.parseFloat(sub.get("payfor") + "");
//				}
//				if (sub.containsKey("point")) {
//					point += Integer.parseInt(sub.get("point") + "");
//					if (sub.containsKey("state")&&!sub.get("state").equals("00C")){
//						abspoint+= Integer.parseInt(sub.get("point") + "");
//					}
//				}
//
//			}
//			record.put("perRange",subs.size()+1);
//			record.put("sellSum",sellSum);
//			if (sellSum!=0) {
//				record.put("sellAvg", sellSum / (subs.size() + 1));
//			}
//			else{
//
//				record.put("sellAvg", 0);
//			}
//			record.put("integralSum",point);
//			record.put("integralLast",abspoint);
//			record.put("payfor",payfor);
//		}
		return page;
	}

	@RequestMapping("clientInfoAppPage")
	@ResponseBody
	public Page<Map<String,Object>> clientInfoAppPage(Page<Map<String,Object>> page,Integer bizId,HttpServletResponse response,String state) throws BaseAppException {
		logger.debug("clientInfoPage ");

		Map<String,Object> conditions = new HashMap<String, Object>();
		conditions.put("bizId",bizId);
		conditions.put("state",state);
		page.setConditons(conditions);
		page = wfDbTableService.getData("client_app", page,response);

		return page;
	}

	@RequestMapping("subClientsPage")
	@ResponseBody
	public Map<String,Object> subClientsPage(@RequestParam(value = "clientId",
			required = true) Integer clientId) throws BaseAppException {

		logger.debug("subClientsPage ");

		Map<String,Object> result = new HashMap<String, Object>();

		result.put("bizId",clientId);
		List<Map<String,Object>> subs = new ArrayList<Map<String, Object>>();
		result.put("subClient",clientService.loadSubClient(result,6,subs));
		return result;
	}

	@RequestMapping("saveOrUpdateLevelInfo")
	@ResponseBody
	public Map<String,Object> saveOrUpdateLevelInfo(String clientJson) {
		logger.debug("saveOrUpdateLevelInfo ");
		Map<String,Object> result = new HashMap<String, Object>();

		if (clientJson!=null) {
			Map<String, Object> clientMap = JsonUtil.toMap(clientJson);
			clientMap.put("state_time",new Date());
			try {
				result.put("data", clientService.saveOrUpdateLevelInfo(clientMap));
				result.put("success",true);
			} catch (BaseAppException e) {
				result.put("success",false);
				result.put("msg", e.getMessage() + e.toString());
			}
		}
		else{
			result.put("success",false);
			result.put("msg","会员对象为空，请确认clinetJson参数不为空，且为json格式");
		}

		return result;
	}

	@RequestMapping("updateLevelState")
	 @ResponseBody
	 public Map<String,Object> updateLevelState(
			@RequestParam(value = "bizIdStr",required = true) String bizIdStr,
			@RequestParam(value = "action",required = true) String action) {
		logger.debug("updateClientState ");
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			result.put("data", clientService.updateLevelState(bizIdStr, action));
			result.put("success",true);
		} catch (BaseAppException e) {
			result.put("success",false);
			result.put("msg", e.getMessage() + e.toString());
		}
		return result;
	}

	@RequestMapping("loadClientInfo")
	@ResponseBody
	public Map<String,Object> loadClientInfo(
			@RequestParam(value = "bizId",required = true) Integer bizId) {
		logger.debug("loadClientInfo ");
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			result.put("data", clientService.loadClientInfo(bizId));
			result.put("success",true);
		} catch (BaseAppException e) {
			result.put("success",false);
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
			result.put("data", clientService.cash(bizId,money));
			result.put("success",true);
		} catch (BaseAppException e) {
			result.put("success",false);
			result.put("msg", e.getMessage() + e.toString());
		}
		return result;
	}

	@RequestMapping("loginName")
	@ResponseBody
	public Map<String,Object> loginName() {
		logger.debug("loginName");
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			result.put("data", ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
			result.put("success",true);
		} catch (Exception e) {
			result.put("success",false);
			result.put("msg", e.getMessage() + e.toString());
		}
		return result;
	}
}
