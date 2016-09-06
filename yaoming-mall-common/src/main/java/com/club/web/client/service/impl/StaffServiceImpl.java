package com.club.web.client.service.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.core.idproduce.ISequenceGenerator;
import com.club.framework.exception.BaseAppException;
import com.club.web.client.service.StaffService;
import com.club.web.common.service.IBaseService;
import com.yaoming.common.util.StringUtil;
import com.yaoming.module.security.service.SecurityEditService;

@Service
public class StaffServiceImpl implements StaffService {

	@Autowired private IBaseService baseService;
	@Autowired private SecurityEditService securityEditService;
    @Resource(name = "sequenceProcGenerator") private ISequenceGenerator sequenceGenerator;

	private final String SELECT_STAFF_PAGE = "select a.staff_id, a.login_name, a.staff_name, a.contact_nbr, a.email, a.update_time, group_concat(c.note) roles, group_concat(c.id) roleIds from staff_t a left join _security_role_user b on a.staff_id=b.user_id left join _security_role c on b.role_id=c.id group by a.staff_id having (roles like '#{conditions}' or login_name like '#{conditions}' or staff_name like '#{conditions}')";
	
	@Override
	public Page<Map<String, Object>> selectPage(Map<String, Object> params) throws BaseAppException {
		params.put("sql", SELECT_STAFF_PAGE.replace("#{conditions}", params.get("conditions").toString()));
		return baseService.selectPage(params, true);
	}

	@Override
	public void save(Map<String, Object> params) throws BaseAppException {

		int id;
		boolean flag = false;
		if(!params.containsKey("staffId") || (id=Integer.parseInt(params.get("staffId").toString()))<=0) {
			params.put("staffId", id=sequenceGenerator.sequenceIntValue("STAFF_T", "STAFF_ID"));
			params.put("createdTime", new Date());
			flag = true;
		}

		params.put("updateTime", new Date());
		params.put("effDate", new Date());
		params.put("expDate", new Date());
		
		
		long[] roleIds = StringUtil.toLongArray(params.remove("roles").toString(), ",");

		if(flag)
			baseService.insert("staff_t", params);
		else 
			baseService.update("staff_t", params);
		
		securityEditService.saveUserRoles(id, roleIds);
	}
	
	@Override
	public void delete(String... ids) throws BaseAppException {
		for (String id : ids)
			baseService.delete("staff_t.staff_id="+id);
	}

}
