package net.thornydev.mybatis.test.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class YearTypeHandler extends BaseTypeHandler<Year> {

  @Override
  public Year getNullableResult(final ResultSet rs, final String colName)
  throws SQLException {

    return new Year(rs.getString(colName));

  }

  @Override
  public Year getNullableResult(final ResultSet rs, final int colIdx)
  throws SQLException {

    return new Year(rs.getString(colIdx));

  }

  @Override
  public Year getNullableResult(final CallableStatement arg0, final int arg1)
  throws SQLException {

    return null;

  }

  @Override
  public void setNonNullParameter(final PreparedStatement ps, final int idx,
                                  final Year yr, final JdbcType jt) throws SQLException {

    ps.setInt(idx, Integer.parseInt(yr.getYear()));

  }

}
