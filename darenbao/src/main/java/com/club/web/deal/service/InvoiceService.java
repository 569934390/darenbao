package com.club.web.deal.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.club.core.common.Page;

public interface InvoiceService {

	Page<Map<String, Object>> list(Page<Map<String, Object>> page);

	void exportExcel(String condition, HttpServletResponse response);

}
