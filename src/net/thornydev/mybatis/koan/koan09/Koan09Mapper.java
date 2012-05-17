package net.thornydev.mybatis.koan.koan09;

import java.util.List;

import net.thornydev.mybatis.koan.domain.Country;

public interface Koan09Mapper {
	// TODO: This looks like it should work, but it won't given the way that
	//       the "getCountriesOrdered2" mapping is written in the xml file.
	//       Add something here to fix it. Do NOT change the getCountriesOrdered2
	//       entry in xml file for this exercise.
	List<Country> getCountriesOrdered2(String s);
}
