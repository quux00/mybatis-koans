package net.thornydev.mybatis.test.koan08;

import net.thornydev.mybatis.test.domain.Country;

import org.apache.ibatis.annotations.Delete;

public interface Koan08Mapper {

  @Delete("DELETE FROM country WHERE country_id = #{id}")
  int deleteCountryById(int id);

  @Delete("DELETE FROM country WHERE country_id = #{id}")
  int deleteCountry(Country c);

}
