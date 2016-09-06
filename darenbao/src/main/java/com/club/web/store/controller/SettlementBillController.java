package com.club.web.store.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.web.store.service.SettlementBillService;
import com.club.web.store.vo.SubbranchVo;

/**   
* @Title: SettlementBillController.java
* @Package com.club.web.store.controller
* @Description: 待结算账单接口类 
* @author hqLin 
* @date 2016/05/03
* @version V1.0   
*/
@Controller
@RequestMapping("/settlementBillController")
public class SettlementBillController {

	@Autowired
	private SettlementBillService settlementBillService;
	
	@RequestMapping("/selectSettlementBill")
	@ResponseBody
	public Map<String, Object> selectSettlementBill(Page<Map<String, Object>> page, String orderId, 
			String shopId, Date startTime, Date endTime, int status) {
		Map<String, Object> returnMap = settlementBillService.selectSettlementBill(page, orderId, 
				shopId, startTime, endTime, status);	
		
		return returnMap;
	}
	
	@RequestMapping("/getSubbranch")
	@ResponseBody
	public List<SubbranchVo> getSubbranch(){
		List<SubbranchVo> subbranchVos = settlementBillService.getSubbranch();
		
		return subbranchVos;
	}
	
	@RequestMapping("/updateBillStatus")
	@ResponseBody
	public Map<String, Object> updateBillStatus(String ids,int status){
		Map<String, Object> result = new HashMap<String, Object>();
		if(ids != null){
			try {
				result.put("success", true);
				result.put("msg", "结算成功");
				settlementBillService.settlementBill(ids,status);				
			} catch (Exception e) {
				result.put("success", false);
				result.put("msg", e.getMessage());
			}
		} else{
			result.put("success", false);
			result.put("msg", "订单ID不能为空");
		}
			
		return result;
	}
	
	@RequestMapping("/addSettlementBill")
	@ResponseBody
	public Map<String, Object> addSettlementBill(String indentId){
		Map<String, Object> result = new HashMap<String, Object>();
		if(indentId == null || indentId.isEmpty()){
			result.put("success", false);
			result.put("msg", "订单号不能为空");
		} else{
			try {
				boolean indentExist = settlementBillService.indentExist(indentId);
				if(indentExist){
					result.put("success", true);
					result.put("msg", "新增结算单成功");
					settlementBillService.addSettlementBill(indentId);
				} else{
					result.put("success", false);
					result.put("msg", "该订单已在结算单中");
				}
			} catch (Exception e) {
				result.put("success", false);
				result.put("msg", e.getMessage());
			}
		}
			
		return result;
	}
	
	@RequestMapping("/exportExcel")
	@ResponseBody
	public Map<String, Object> exportExcel(String orderId, String shopId, Date startTime, Date endTime, int status, HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String path = settlementBillService.exportExcel(orderId, shopId, startTime, endTime, status, request);
			result.put("success", true);
			result.put("msg", path);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
			
		return result;
	}
	
	@RequestMapping("/selectTotalBill")
	@ResponseBody
	public Map<String, Object> selectTotalBill() {
		Map<String, Object> returnMap = settlementBillService.selectTotalBill();	
		
		return returnMap;
	}
}