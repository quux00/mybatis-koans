package net.thornydev.mybatis.koan.koan10;

import java.util.List;

import net.thornydev.mybatis.koan.domain.City;
import net.thornydev.mybatis.koan.domain.Country;

public interface Koan10Mapper {
  Country getCountryById(int id);
  City getCityById(int id);
  List<City> getCities();
}
