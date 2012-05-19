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
    return new Year(rs.getString(colName));
  }

  @Override
  public Year getNullableResult(ResultSet rs, int colIdx) throws SQLException {
    return new Year(rs.getString(colIdx));
  }

  @Override
  public Year getNullableResult(CallableStatement arg0, int arg1)	throws SQLException {
    return null;
  }

  @Override
  public void setNonNullParameter(PreparedStatement ps, int idx, Year yr, JdbcType jt) throws SQLException {
    ps.setInt( idx, Integer.parseInt(yr.getYear()) );
  }

}
