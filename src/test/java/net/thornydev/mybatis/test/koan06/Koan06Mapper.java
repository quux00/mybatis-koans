package net.thornydev.mybatis.test.koan06;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import net.thornydev.mybatis.test.domain.Country;

public interface Koan06Mapper {
  int getCountryCount();
  List<Country> getCountryRange(int lowerBound, int upperBound);
  List<Country> getCountryRange2(@Param("id1") int lowerBound, @Param("id2") int upperBound);
  List<Country> getCountries(RowBounds rb);
}

