package net.thornydev.mybatis.test.koan23;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class RatingTypeHandler extends BaseTypeHandler<Rating> {

  @Override
  public Rating getNullableResult(ResultSet rs, String colName) throws SQLException {
    return Rating.fromString(rs.getString(colName));
  }

  @Override
  public Rating getNullableResult(ResultSet rs, int colNum) throws SQLException {
    return Rating.fromString(rs.getString(colNum));
  }

  @Override
  public Rating getNullableResult(CallableStatement arg0, int arg1) throws SQLException {
    return null;
  }

  @Override
  public void setNonNullParameter(PreparedStatement ps, int idx, Rating r, JdbcType t)
  throws SQLException {
    ps.setString(idx, r.toString());
  }

}
