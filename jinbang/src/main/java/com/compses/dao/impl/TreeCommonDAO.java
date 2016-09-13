package com.compses.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class TreeCommonDAO extends BaseCommonDAO {
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
}
