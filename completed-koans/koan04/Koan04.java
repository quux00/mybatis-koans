package net.thornydev.mybatis.koan.koan04;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.thornydev.mybatis.koan.domain.Country;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

// In Koan04, we again create an xml mapper file and this time we create a
// Mapper java interface for it in order to get compile time checking of our
// method invocations.  We call that mapper Koan04Mapper.
// The select statements in the mapper are exactly the same as in Koan03, but
// we have changed their names to make them more natural to the domain.
//
// To complete this koan test you will need to edit:
// 1. all the TODO entries in this koan
// 2. the mapper xml file to have the right SQL queries and MyBatis XML entries
public class Koan04 {

	static SqlSessionFactory sessionFactory;
	SqlSession session;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String resource = "net/thornydev/mybatis/koan/koan04/koan04-config.xml";  
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		inputStream.close();
	}

	@Before
	public void setUp() throws Exception {
		session = sessionFactory.openSession();
	}

	@After
	public void tearDown() throws Exception {
		if (session != null) session.close();
	}

	@Test
	public void learnToQueryViaMapperClassReturningCountryDomainObject() throws Exception {
		Koan04Mapper mapper = session.getMapper(Koan04Mapper.class);
		Country c = mapper.getCountryById(1);
		
		assertNotNull(c);
		assertEquals(1, c.getId());
		assertEquals("Afghanistan", c.getCountry());
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = format.parse("2006-02-15 09:44:00");
		assertEquals(date, c.getLastUpdate());	
	}
	
	@Test
	public void learnToQueryViaMapperClassReturningListOfCountries() throws Exception {
		Koan04Mapper mapper = session.getMapper(Koan04Mapper.class);
		List<Country> allCountries = mapper.getAllCountries();
		
		assertEquals(109, allCountries.size());
		Country c109 = allCountries.get(0);

		assertEquals(109, c109.getId());
		assertEquals("Zambia", c109.getCountry());
	}	

	@Test
	public void learnToQueryViaMapperClassReturningHashMapOfCountriesKeyedById() throws Exception {
		Koan04Mapper mapper = session.getMapper(Koan04Mapper.class);
		Map<Integer,Country> countriesMap = mapper.getAllCountriesMappedById();
		
		assertEquals(109, countriesMap.size());
		
		Country c33 = countriesMap.get(33);
		assertEquals(33, c33.getId());
		assertEquals("Finland", c33.getCountry());

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = format.parse("2006-02-15 09:44:00");
		assertEquals(date, c33.getLastUpdate());
	}
}
