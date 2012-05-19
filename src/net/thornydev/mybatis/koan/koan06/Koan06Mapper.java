package net.thornydev.mybatis.koan.koan06;

import java.util.List;

import net.thornydev.mybatis.koan.domain.Country;

public interface Koan06Mapper {

  int getCountryCount();
  List<Country> getCountryRange(int lowerBound, int upperBound);

  // TODO: add the MyBatis annotation(s) to be able to reference parameters
  //       as "id1" and "id2" in the xml mapping
  List<Country> getCountryRange2(int lowerBound, int upperBound);

  // TODO: create an interface method that maps to the "getCountries" query
  //       in the xml mapping file and also can use a RowBounds objec to
  //       limit what the query returns
}

