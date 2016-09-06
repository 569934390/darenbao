package com.club.web.event.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.club.core.common.Page;
import com.club.web.event.vo.EventActivitySignUpSaveVo;
import com.club.web.event.vo.EventActivitySignUpVo;

public interface EventActivitySignUpService {

	Page<Map<String, Object>> queryActivityUserPage(Page<Map<String, Object>> page);

	Map<String,Object> signUp(EventActivitySignUpSaveVo activitySignUpVo, HttpServletRequest request);

	List<EventActivitySignUpVo> findByActivityId(Long eventActivityId);

	void userExportData(Map map, HttpServletResponse response) throws Exception ;

}
