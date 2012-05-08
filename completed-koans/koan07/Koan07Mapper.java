package net.thornydev.mybatis.koan.koan07;

import org.apache.ibatis.annotations.Delete;

import net.thornydev.mybatis.koan.Country;

public interface Koan07Mapper {
	@Delete("DELETE FROM country WHERE country_id = #{id}")
	int deleteCountryById(int id);

	@Delete("DELETE FROM country WHERE country_id = #{id}")
	int deleteCountry(Country c);
}
