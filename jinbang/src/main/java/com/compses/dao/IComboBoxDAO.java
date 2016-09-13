package com.compses.dao;

import java.util.List;
import java.util.Map;

public interface IComboBoxDAO {
	public List<Map<String,Object>> selectList(String sqlKey,Map<String,Object> paramMap);
}
