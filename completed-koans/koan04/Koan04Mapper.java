package net.thornydev.mybatis.koan.koan04;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;

import net.thornydev.mybatis.koan.domain.Country;

public interface Koan04Mapper {
	Country getCountryById(int id);
	List<Country> getAllCountries();
	@MapKey("id")
	Map<Integer,Country> getAllCountriesMappedById();
}
