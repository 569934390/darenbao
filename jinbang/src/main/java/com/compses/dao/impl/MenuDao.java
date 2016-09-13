package com.compses.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.compses.dao.IMenuDao;
import com.compses.model.Menu;
import com.compses.util.DBUtils;

@Repository
public class MenuDao implements IMenuDao{

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public List<Menu> getMenuList() {
		// TODO Auto-generated method stub
		String sql=DBUtils.getSql("DopMenu", "getMenuList");
		return DBUtils.query(sql,namedParameterJdbcTemplate,Menu.class);
	}

}
