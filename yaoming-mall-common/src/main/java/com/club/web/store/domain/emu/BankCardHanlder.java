package com.club.web.store.domain.emu;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class BankCardHanlder extends BaseTypeHandler<BankCardEMU>{

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, BankCardEMU parameter, JdbcType jdbcType)
			throws SQLException {
		 ps.setInt(i, parameter.ordinal());
	}

	@Override
	public BankCardEMU getNullableResult(ResultSet rs, String columnName) throws SQLException {
		 return convert(rs.getInt(columnName));
	}

	private BankCardEMU convert(int result) {
		return BankCardEMU.valueOf(result);
	}

	@Override
	public BankCardEMU getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return convert(rs.getInt(columnIndex));
	}

	@Override
	public BankCardEMU getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return convert(cs.getInt(columnIndex));
	}

}
