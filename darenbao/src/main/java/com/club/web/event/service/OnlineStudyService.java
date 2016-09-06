package com.club.web.event.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.club.core.common.Page;
import com.club.web.event.vo.EventOnlineStudyTypeVo;
import com.club.web.event.vo.EventOnlineStudyVo;

public interface OnlineStudyService {

	Map<String,Object> saveOrUpdateOnlineStudy(EventOnlineStudyVo eventOnlineStudyVo,
			HttpServletRequest request);

	Page<Map<String, Object>> queryOnlineStudyPage(Page<Map<String, Object>> page);

	Map<String,Object> delete(String idStr);

	Map<String,Object> findOneById(String id);

	

}
