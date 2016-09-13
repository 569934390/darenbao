package com.compses.common.mybatis;

public abstract class Dialect {

	public abstract String getLimitString(String sql, int skipResults,
			int maxResults);
	

}
