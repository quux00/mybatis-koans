package net.thornydev.mybatis.koan.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class YearTypeHandler extends BaseTypeHandler<Year> {

	@Override
	public Year getNullableResult(ResultSet rs, String colName) throws SQLException {
		// TODO: fill in
		return null;
	}

	@Override
	public Year getNullableResult(ResultSet rs, int colIdx) throws SQLException {
		// TODO: fill in
		return null;
	}

	@Override
	public Year getNullableResult(CallableStatement arg0, int arg1)	throws SQLException {
		// OPTIONAL TODO: this will never be called in this example
		return null;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int idx, Year yr, JdbcType jt) throws SQLException {
		// TODO: fill in for the DML statements (not needed for queries)
	}

}
