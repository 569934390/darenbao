package com.club.web.client.service;

import java.util.Map;

import com.club.core.common.Page;
import com.club.framework.exception.BaseAppException;

public interface StaffService {

	public Page<Map<String, Object>> selectPage(Map<String, Object> params) throws BaseAppException;
	
	public void save(Map<String, Object> params) throws BaseAppException;
	public void delete(String... ids) throws BaseAppException;

}
