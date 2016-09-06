package com.club.web.client.controller;

import com.club.core.common.Page;
import com.club.framework.exception.BaseAppException;
import com.club.framework.log.ClubLogManager;
import com.club.framework.util.JsonUtil;
import com.club.web.client.service.IClientService;
import com.club.web.client.service.IIntegralMangerService;
import com.club.web.common.service.IBaseService;
import com.club.web.datamodel.service.IWfDbTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
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
@RequestMapping("/integralManger")
public class IntegralManagerController {

    private static final ClubLogManager logger = ClubLogManager
            .getLogger(IntegralManagerController.class);

	@Autowired
	private IBaseService baseService;
	@Autowired
	private IIntegralMangerService integralMangerService;
	@Autowired
	private IWfDbTableService wfDbTableService;

	@RequestMapping("ruleSetPage")
	@ResponseBody
	public Page<Map<String,Object>> ruleSetPage(Page<Map<String,Object>> page,String conditionStr,HttpServletResponse response) throws BaseAppException {
		logger.debug("ruleSetPage ");

		if (conditionStr!=null){
			page.setConditons(JsonUtil.toMap(conditionStr));
		}
		page = wfDbTableService.getData("rule_set", page,response);

		return page;
	}

	@RequestMapping("integralInfoPage")
	@ResponseBody
	public Page<Map<String,Object>> integralInfoPage(Page<Map<String,Object>> page,String conditionStr,HttpServletResponse response) throws BaseAppException {
		logger.debug("integralInfoPage ");

		if (conditionStr!=null){
			page.setConditons(JsonUtil.toMap(conditionStr));
		}
		page = wfDbTableService.getData("integral", page, response);

		return page;
	}

	@RequestMapping("updateState")
	@ResponseBody
	public Map<String,Object> updateState(
			@RequestParam(value = "bizIdStr",required = true) String bizIdStr,
			@RequestParam(value = "action",required = true) String action) {
		logger.debug("updateState ");
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			result.put("data", integralMangerService.updateState(bizIdStr, action));
			result.put("success",true);
		} catch (BaseAppException e) {
			result.put("success",false);
			result.put("msg", e.getMessage() + e.toString());
		}
		return result;
	}

	@RequestMapping("loadGeneralRule")
	@ResponseBody
	public Object loadGeneralRule() {
		logger.debug("loadGeneralRule ");
		return integralMangerService.loadGenralSql();
	}

	@RequestMapping("saveOrUpdateRuleInfo")
	@ResponseBody
	public Map<String,Object> saveOrUpdateRuleInfo(String clientJson) {
		logger.debug("saveOrUpdateClientInfo ");
		Map<String,Object> result = new HashMap<String, Object>();

		if (clientJson!=null) {
			Map<String, Object> clientMap = JsonUtil.toMap(clientJson);
			clientMap.put("modify_time",new Date());
			try {
				result.put("data", integralMangerService.saveOrUpdateInfo(clientMap));
				result.put("success",true);
			} catch (BaseAppException e) {
				result.put("success",false);
				result.put("msg", e.getMessage() + e.toString());
			}
		}
		else{
			result.put("success",false);
			result.put("msg","规则对象为空，请确认clinetJson参数不为空，且为json格式");
		}

		return result;
	}
}
