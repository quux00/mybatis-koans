package net.thornydev.mybatis.koan.koan05;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.List;

import net.thornydev.mybatis.koan.domain.Country;
import net.thornydev.mybatis.koan.util.Range;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

// In Koan05, we continue to use a mapper xml file and a Mapper class (for
// all but one of the koan tests).  We are still only using the Country domain
// object (and corresponding country table), but here we focus on a few new things:
// * creating the SqlSession object in each test in order to use the standard
//    try/finally idiom to ensure the sessions are closed properly
// * returning an int from a query
// * passing in multiple arguments to a method and either using: 
//   * the default way to reference them, or
//   * using a MyBatis annotation to specify how to reference them in the xml mapping
// * using the MyBatis RowBounds object to limit the number of results returned
//
// To complete this koan test you will need to edit:
// 1. all the TODO entries in this koan
// 2. the mapper xml file to have the right SQL queries and MyBatis XML entries
// 3. all the TODO entries in the Koan05Mapper class
public class Koan05 {

	static SqlSessionFactory sessionFactory;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String resource = "net/thornydev/mybatis/koan/koan05/koan05-config.xml";  
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		inputStream.close();
	}

	@Test
	public void learnToQueryForTotalCount() throws Exception {
		// TODO: to move to the more idiomatic way of using sessions, open a session
		//       here and ensure it is closed when done (or an Exception occurs)
		SqlSession session = null;

		// TODO: get a Koan05Mapper object
		Koan05Mapper mapper = null;
		
		// TODO: call a method on the mapper to get the count of number of countries
		//       in the sakila db
		int totalCountries = 0;

		assertEquals(109, totalCountries);

		// REMINDER: be sure to close your session ...
	}

	@Test
	public void learnToQueryWithMultipleParams() throws Exception {
		// TODO: open a session
		SqlSession session = null;

		// TODO: get a Koan05Mapper object
		Koan05Mapper mapper = null;
		
		List<Country> lc = mapper.getCountryRange(22, 33);

		assertEquals(12, lc.size());
		Country finland = lc.get(11);
		assertEquals("Finland", finland.getCountry());

	}	
	
	@Test
	public void learnToQueryWithMultipleParamsWithAnnotatedNames() throws Exception {
		// TODO: open a session
		SqlSession session = null;

		// TODO: get a Koan05Mapper object
		Koan05Mapper mapper = null;

		List<Country> lc = mapper.getCountryRange2(22, 33);

		assertEquals(12, lc.size());
		Country finland = lc.get(11);
		assertEquals("Finland", finland.getCountry());
	}
	
	@Test
	public void learnToQueryWithDomainSpecificRangeParam() throws Exception {
		// TODO: open a session
		SqlSession session = null;
		
		List<Country> lc = session.selectList("getCountryRange3", new Range(22, 33));

		assertEquals(12, lc.size());
		Country finland = lc.get(11);
		assertEquals("Finland", finland.getCountry());
			
	}	
	
	@Test
	public void learnToQueryWithRowBounds() throws Exception {
		// TODO: open a session
		SqlSession session = null;

		// TODO: create a RowBounds object with an offset and limit
		//       that cause 12 Countries to be returned starting with
		//       country_id 22.  (The last country returned from the
		//       query should be Finland.)
		RowBounds rb = null;
		
		// TODO: get a List<Country> by calling the "getCountries" mapping.
		//       Use the RowBounds object to limit / filter the returned results.
		//       Note: in this koan test we are NOT using a mapper object,
		//       so call one of the session "select" methods directly.
		List<Country> lc = null;

		assertEquals(12, lc.size());
		Country finland = lc.get(11);
		assertEquals("Finland", finland.getCountry());
	}		
	
	@Test
	public void learnToQueryMapperClassWithRowBounds() throws Exception {
		// TODO: open a session
		SqlSession session = null;

		// TODO: get a Koan05Mapper object
		Koan05Mapper mapper = null;

		// TODO: create a RowBounds object with an offset and limit
		//       that cause 12 Countries to be returned starting with
		//       country_id 22.  (The last country returned from the
		//       query should be Finland.)
		RowBounds rb = null;

		// TODO: call getCountries on the mapper and use the RowBounds
		//       object to limit / filter the returned Country objects
		List<Country> lc = null;

		assertEquals(12, lc.size());
		Country finland = lc.get(11);
		assertEquals("Finland", finland.getCountry());
	}		
}
