package net.thornydev.mybatis.koan.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * A MyBatis TypeHandler for the special_features column in the film
 * table of the sakila database.  The special_features is a non-normalized
 * string with multiple entries separated by commas.  This TypeHandler
 * "normalizes" those entries into an explicit list of strings.
 */
public class FilmSpecialFeaturesTypeHandler extends BaseTypeHandler<List<String>> {

  @Override
  public List<String> getNullableResult(ResultSet rs, String colName) throws SQLException {
    // TODO: fill in
    return null;
  }

  @Override
  public List<String> getNullableResult(ResultSet rs, int colNum) throws SQLException {
    // TODO: fill in
    return null;
  }

  private List<String> cleanAndSplit(final String sf) {
    if (sf == null || sf.equals("")) {
      @SuppressWarnings("unchecked")
      List<String> empty = (List<String>)Collections.EMPTY_LIST;
      return empty;
    } else {
      List<String> list = new ArrayList<String>();
      String[] ary = sf.replaceAll("[{}]", "").split("\\s*,\\s*");
      for (String s: ary) {
        list.add( s.replaceAll("\"", "") );
      }
      return list;
    }
  }

  @Override
  public List<String> getNullableResult(CallableStatement arg0, int arg1)	throws SQLException {
    return null;
  }

  @Override
  public void setNonNullParameter(PreparedStatement arg0, int arg1,
                                  List<String> arg2, JdbcType arg3) throws SQLException {
  }
}
