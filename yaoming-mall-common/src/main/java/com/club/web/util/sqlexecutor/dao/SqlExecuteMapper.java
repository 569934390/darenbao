package com.club.web.util.sqlexecutor.dao;

import org.apache.ibatis.annotations.Param;

public interface SqlExecuteMapper {
	public void executeUpdate(@Param("sql") String sql);
}
