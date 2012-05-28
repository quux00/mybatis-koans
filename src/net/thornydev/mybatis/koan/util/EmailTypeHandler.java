package net.thornydev.mybatis.koan.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * Acts as a factory method to return the appropriate implementation of an Email.
 * Returns a Null object if the email value in the database was null/empty
 */
public class EmailTypeHandler extends BaseTypeHandler<Email> {

	@Override
	public Email getNullableResult(ResultSet rs, String colName) throws SQLException {
		System.out.println("DEBUG1");
		return createEmail(rs.getString(colName));
	}

	@Override
	public Email getNullableResult(ResultSet rs, int colNum) throws SQLException {
		System.out.println("DEBUG2");
		return createEmail(rs.getString(colNum));
	}


	private Email createEmail(String s) {
		System.out.println(s);
		if (s == null || s.equals("")) {
			System.out.println("DEBUG3");
			return new NullEmail();
		} else {
			System.out.println("DEBUG4");
			return new EmailImpl(s);
		}
	}
	
	@Override
	public Email getNullableResult(CallableStatement arg0, int arg1) throws SQLException {
		return null;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int colNum,
			Email e, JdbcType t) throws SQLException {
	}

}
