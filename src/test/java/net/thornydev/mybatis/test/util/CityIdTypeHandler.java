package net.thornydev.mybatis.test.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.thornydev.mybatis.test.domain.City;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class CityIdTypeHandler extends BaseTypeHandler<City> {

  @Override
  public City getNullableResult(ResultSet arg0, String arg1) throws SQLException {
    return null;
  }

  @Override
  public City getNullableResult(ResultSet arg0, int arg1) throws SQLException {
    return null;
  }

  @Override
  public City getNullableResult(CallableStatement arg0, int arg1)	throws SQLException {
    return null;
  }

  @Override
  public void setNonNullParameter(PreparedStatement ps, int idx,
                                  City city, JdbcType t) throws SQLException {
    ps.setInt(idx, city.getId());
  }

}
