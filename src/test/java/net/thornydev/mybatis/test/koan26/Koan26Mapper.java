package net.thornydev.mybatis.test.koan26;

import java.util.Map;

import net.thornydev.mybatis.test.domain.Country;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Select;

public interface Koan26Mapper {
  @Select("SELECT country_id AS id, country "
        + "FROM country")
  @MapKey("id")
  Map<Integer, Country> getAllCountriesMappedById();
}
