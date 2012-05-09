package net.thornydev.mybatis.koan.koan09;

import java.util.List;

import net.thornydev.mybatis.koan.domain.City;
import net.thornydev.mybatis.koan.domain.Country;

public interface Koan09Mapper {
	Country getCountryById(int id);
	City getCityById(int id);
	List<City> getCities();
}
