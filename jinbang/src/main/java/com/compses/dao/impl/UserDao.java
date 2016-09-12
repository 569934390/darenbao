package com.compses.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.compses.dao.IUserDao;
import com.compses.model.DopPrivilegeUser;
import com.compses.util.DBUtils;

@Repository
public class UserDao extends BaseCommonDAO implements IUserDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public DopPrivilegeUser selectByUsername(String name) {
		String sql=DBUtils.getSql("DopPrivilegeUser", "selectUser");
		Map<String,Object> paramMap=new HashMap<String, Object>();
		paramMap.put("name", name);
		return DBUtils.queryForObject(sql,paramMap,namedParameterJdbcTemplate,DopPrivilegeUser.class);
	}

}
