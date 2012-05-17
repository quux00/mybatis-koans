package net.thornydev.mybatis.koan.koan05;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;

import net.thornydev.mybatis.koan.domain.Country;

public interface Koan05Mapper {
	// TODO: this method should take a parameter in order to query by id
	Country getCountryById(int id);
	// TODO: add method to call the "getAllCountries" statement in the koan05-mapper.xml file
	List<Country> getAllCountries();
	// TODO: add an annotation to getAllCountriesMappedById that will make it work
	@MapKey("id")
	Map<Integer,Country> getAllCountriesMappedById();
}
