package net.thornydev.mybatis.koan.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.thornydev.mybatis.koan.domain.Country;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class CountryIdTypeHandler extends BaseTypeHandler<Country> {

	@Override
	public Country getNullableResult(ResultSet arg0, String arg1)
			throws SQLException {
		return null;
	}

	@Override
	public Country getNullableResult(ResultSet arg0, int arg1)
			throws SQLException {
		return null;
	}

	@Override
	public Country getNullableResult(CallableStatement arg0, int arg1)
			throws SQLException {
		return null;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int idx,
			Country co, JdbcType t) throws SQLException {
		ps.setInt(idx, co.getId());
	}

}
