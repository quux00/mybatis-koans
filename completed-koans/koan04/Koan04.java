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

// In Koan04, we do the exact same queries as Koan03, except
// that we do not use an "AS" clause in the SQL to change underscore
// to camelCase, like this:
//    SELECT country_id AS id, country, last_update
// instead of
//    SELECT country_id AS id, country, last_update AS lastUpdate
// 
// (Note: we still need the "country_id AS id" part since we changed the 
//        property name, not just modified it from underscore to camelCase.)
//
// To complete this koan test you will need to edit:
// 1. the koan04-config.xml file to specify the setting to get auto-mapping to
//    camelCase turned on
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
	public void learnToQueryViaXmlMapperReturningCountryDomainObject() throws Exception {
		Country c = session.selectOne("selectFirstCountry");
		
		assertNotNull(c);
		assertEquals(1, c.getId());
		assertEquals("Afghanistan", c.getCountry());
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = format.parse("2006-02-15 09:44:00");
		assertEquals(date, c.getLastUpdate());
	}

	@Test
	public void learnToQueryViaXmlMapperReturningListOfCountries() throws Exception {
		List<Country> lmap = session.selectList("selectAsListOfCountries");
		
		assertEquals(109, lmap.size());
		Country c109 = lmap.get(0);

		assertEquals(109, c109.getId());
		assertEquals("Zambia", c109.getCountry());
	}
	
	@Test
	public void learnToQueryViaXmlMapperReturningHashMapOfCountriesKeyedById() throws Exception {
		Map<Integer,Country> countriesMap = session.selectMap("selectAsMapOfCountries", "id");
		
		assertEquals(109, countriesMap.size());
		
		Country c33 = countriesMap.get(33);
		assertEquals(33, c33.getId());
		assertEquals("Finland", c33.getCountry());

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = format.parse("2006-02-15 09:44:00");
		assertEquals(date, c33.getLastUpdate());
	}
}
