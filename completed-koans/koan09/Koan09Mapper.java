package net.thornydev.mybatis.koan.koan09;

import java.util.List;

import net.thornydev.mybatis.koan.domain.Country;

import org.apache.ibatis.annotations.Param;

public interface Koan09Mapper {
	List<Country> getCountriesOrdered2(@Param("columnName") String columnName);
}
