package net.thornydev.mybatis.koan.koan07;

import java.util.List;
import java.util.Map;

import net.thornydev.mybatis.koan.domain.Country;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

public interface Koan07Mapper {

  @Select("SELECT count(*) FROM country")
  int getCountryCount();

  @Select("SELECT country_id AS id, country, last_update AS lastUpdate FROM country WHERE country_id = #{id}")
  Country getCountryById(int id);

  @Select("SELECT country_id AS id, country, last_update AS lastUpdate FROM country")
  @MapKey("id")
  Map<Integer,Country> getAllCountriesMappedById();

  @Select("SELECT country_id AS id, country, last_update AS lastUpdate FROM country WHERE country_id BETWEEN #{param1} and #{param2}")
  List<Country> getCountryRange(int lowerBound, int upperBound);

  @Select("SELECT country_id AS id, country, last_update AS lastUpdate FROM country")
  List<Country> getCountryRange2(RowBounds rb);
}
