package com.club.web.deal.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.deal.service.InvoiceService;

/**
 * 发票控制类
 * 
 * @author zhuzd
 *
 */
@Controller
@RequestMapping("/deal/invoice")
public class InvoiceController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private InvoiceService invoiceService;

	@RequestMapping("/list")
	@ResponseBody
	public Page<Map<String,Object>> list(Page<Map<String, Object>> page, String conditionStr) {
		logger.debug("invoice list ");
		Map<String,Object> result = new HashMap<>();
		result.put(Constants.RESULT_CODE, 1);
		try {
			if (StringUtils.isNotEmpty(conditionStr) && page != null) {
				page.setConditons(JsonUtil.toMap(conditionStr));
			}
			page = invoiceService.list(page);
		} catch (Exception e) {
			logger.error("invoice list error:", e);
		}
		return page;
	}
	
	/**
	 * 导出列表
	 * @return
	 */
	@RequestMapping("/exportExcel")
	public void exportExcel(String condition,HttpServletResponse response){
		logger.debug("invoice exportExcel");
		try {
			invoiceService.exportExcel(condition,response);
        } catch (Exception e) {
            logger.error("invoice exportExcel",e);
        }
	}
}
