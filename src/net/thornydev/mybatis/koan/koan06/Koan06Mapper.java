package net.thornydev.mybatis.koan.koan06;

import java.util.List;
import java.util.Map;

import net.thornydev.mybatis.koan.domain.Country;

import org.apache.ibatis.session.RowBounds;

public interface Koan06Mapper {

	// TODO: add annotation to specify the SQL mapping for this method	
	int getCountryCount();

	// TODO: add annotation to specify the SQL mapping for this method	
	Country getCountryById(int id);
	
	// TODO: add annotation to specify the SQL mapping for this method	
	// TODO: add any other annotations required to make this return a Map
	Map<Integer,Country> getAllCountriesMappedById();

	// TODO: add annotation to specify the SQL mapping for this method
	// TODO: depending how you specify the parameters in the SQL, you may 
	//       also need additional annotations
	List<Country> getCountryRange(int lowerBound, int upperBound);
	
	// TODO: add annotation to specify the SQL mapping for this method
	// TODO: even though you can have overloaded method names in Java, will it work here?
	List<Country> getCountryRange(RowBounds rb);
}
