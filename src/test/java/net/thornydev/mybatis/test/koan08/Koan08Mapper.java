package net.thornydev.mybatis.test.koan08;

import org.apache.ibatis.annotations.Delete;

import net.thornydev.mybatis.test.domain.Country;

public interface Koan08Mapper {
  @Delete("DELETE FROM country WHERE country_id = #{id}")
  int deleteCountryById(int id);

  @Delete("DELETE FROM country WHERE country_id = #{id}")
  int deleteCountry(Country c);
}
