package com.club.web.store.domain.emu;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class bankCardTypeHanlder extends BaseTypeHandler<bankCardType>{

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, bankCardType parameter, JdbcType jdbcType)
			throws SQLException {
		 ps.setInt(i, parameter.ordinal());
	}

	@Override
	public bankCardType getNullableResult(ResultSet rs, String columnName) throws SQLException {
		 return convert(rs.getInt(columnName));
	}

	private bankCardType convert(int result) {
		return bankCardType.valueOf(result);
	}

	@Override
	public bankCardType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return convert(rs.getInt(columnIndex));
	}

	@Override
	public bankCardType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return convert(cs.getInt(columnIndex));
	}

}
