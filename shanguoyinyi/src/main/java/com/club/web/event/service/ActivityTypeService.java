package com.club.web.event.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.club.core.common.Page;
import com.club.web.event.vo.EventActivityTypeVo;

public interface ActivityTypeService {

	Map<String,Object> saveOrUpdateActivityType(EventActivityTypeVo eventActivityTypeVo,
			HttpServletRequest request);

	Page<Map<String, Object>> queryActivityTypePage(Page<Map<String, Object>> page);

	Map<String,Object> delete(String idStr);

	Map<? extends String, ? extends Object> updateStatus(String idStr, long status, HttpServletRequest request);

	List<EventActivityTypeVo> findList();

}
