package net.thornydev.mybatis.koan.koan07;

import net.thornydev.mybatis.koan.domain.Country;

public interface Koan07Mapper {
	// TODO: Specify the DELETE sql in an annotation, not xml, for this method 
	//       that takes an id, rather than a Country object
	int deleteCountryById(int id);

	
	// TODO: Specify the DELETE sql in an annotation, not xml, for this method
	//       that takes a Country object, rather than an id
	int deleteCountry(Country c);
}
