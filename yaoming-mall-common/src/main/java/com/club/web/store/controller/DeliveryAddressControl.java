package com.club.web.store.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;
import com.club.web.store.service.DeliveryAddressService;
import com.club.web.store.service.TradeEexpressageService;
import com.club.web.store.vo.DeliveryAddressVo;
import com.club.web.store.vo.TradeEexpressageVo;

@Controller
@RequestMapping("DeliveryAddress")
public class DeliveryAddressControl {

	@Autowired
	DeliveryAddressService deliveryAddressService;

	@RequestMapping("saveOrUpdateDeliveryAddress")
	@ResponseBody
	public Map<String, Object> saveTradeExpressage(
			@RequestParam(value = "modelJson", required = true) String modelJson,String provinceName,String cityName,String countyName) {
		Map<String, Object> result = new HashMap<String, Object>();
       if (!StringUtils.isEmpty(modelJson)) {
    	   DeliveryAddressVo deliveryAddressVo = JsonUtil.toBean(modelJson, DeliveryAddressVo.class);
    	   deliveryAddressVo.setProvinceName(provinceName);
    	   deliveryAddressVo.setCityName(cityName);
    	   deliveryAddressVo.setCountyName(countyName);

   		try {
   			result = deliveryAddressService.saveOrUpdateDeliveryAddress(deliveryAddressVo);
   		} catch (Exception e) {
   			result.put("success", false);
   			result.put("msg", e.getMessage());
   		}
	    }else{result.put("success", false);
			result.put("msg", "数据为空");}
		
		return result;
	}
	@RequestMapping("getDeliveryAddress")
	@ResponseBody
	public Object saveTradeExpressage(HttpServletResponse response) {
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8"); 
		
		Map<String, Object> result = new HashMap<String, Object>();
		DeliveryAddressVo deliveryAddressVo=null;
		try {
			 deliveryAddressVo = deliveryAddressService.getDeliveryAddress();
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
			return result;
		}
		return deliveryAddressVo;
	}
	
}
