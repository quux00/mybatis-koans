package net.thornydev.mybatis.koan.koan08;

import java.util.List;

import net.thornydev.mybatis.koan.domain.Country;

import org.apache.ibatis.annotations.Param;

public interface Koan08Mapper {
	List<Country> getCountriesOrdered2(@Param("columnName") String columnName);
}
