package net.thornydev.mybatis.test.koan10;

import java.util.List;

import net.thornydev.mybatis.test.domain.City;
import net.thornydev.mybatis.test.domain.Country;

public interface Koan10Mapper {

	Country getCountryById(int id);

	City getCityById(int id);

	List<City> getCities();

}
