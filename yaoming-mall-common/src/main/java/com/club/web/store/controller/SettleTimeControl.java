package com.club.web.store.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.framework.util.JsonUtil;
import com.club.web.store.service.SettleTimeService;
import com.club.web.store.vo.SettleTimeVo;

@Controller
@RequestMapping("SettleTime")
public class SettleTimeControl {

	@Autowired
	SettleTimeService settleTimeService;

	@RequestMapping("saveOrUpdateSettleTime")
	@ResponseBody
	public Map<String, Object> saveOrUpdateSettleTime(@RequestParam(value = "modelJson", required = true) String modelJson){
		SettleTimeVo settleTimeVo=JsonUtil.toBean(modelJson, SettleTimeVo.class);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (settleTimeVo != null) {
				result=	settleTimeService.saveOrUpdateSettleTime(settleTimeVo);}
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	@RequestMapping("getSettleTime")
	@ResponseBody
	public Object getSettleTime(){
		SettleTimeVo settleTimeVo=null;
		try {
			 settleTimeVo=settleTimeService.getSettleTime();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return settleTimeVo;
		}
}
