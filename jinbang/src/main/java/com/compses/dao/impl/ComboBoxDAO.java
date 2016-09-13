package com.compses.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.compses.dao.IComboBoxDAO;
import com.compses.util.DBUtils;

public class ComboBoxDAO implements IComboBoxDAO {
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<Map<String, Object>> selectList(String sqlKey,
			Map<String, Object> paramMap) {
		String[] strArr=sqlKey.split("\\.");
		String sql=DBUtils.getSql(strArr[0], strArr[1]);
		return DBUtils.query(sql,paramMap,namedParameterJdbcTemplate);
	}

}
