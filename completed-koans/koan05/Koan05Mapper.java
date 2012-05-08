package net.thornydev.mybatis.koan.koan05;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import net.thornydev.mybatis.koan.domain.Country;

public interface Koan05Mapper {

	int getCountryCount();
	List<Country> getCountryRange(int lowerBound, int upperBound);
	List<Country> getCountryRange2(@Param("id1") int lowerBound, @Param("id2") int upperBound);
	List<Country> getCountries(RowBounds rb);
}

