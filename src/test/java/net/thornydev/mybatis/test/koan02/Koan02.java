package net.thornydev.mybatis.test.koan02;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

// In Koan2, we create an xml mapper file that returns a HashMap or
// a List of HashMaps.
//
// We will query the country table first, since no other table depends
// (has foreign keys) into it.
//
// To complete this koan test you will need to edit:
// 1. all the TODO entries in this koan
// 2. the koan02-config.xml file to specify the mapper xml file to use
// 3. the mapper xml file to have the right SQL queries and MyBatis XML entries

public class Koan02 {

	static SqlSessionFactory sessionFactory;
	SqlSession session;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		final String resource = "net/thornydev/mybatis/test/koan02/koan02-config.xml";
		final InputStream inputStream = Resources.getResourceAsStream(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		inputStream.close();
	}

	@Before
	public void setUp() throws Exception {
		session = sessionFactory.openSession();
	}

	@After
	public void tearDown() throws Exception {
		if (session != null)
			session.close();
	}

	@Test
	public void learnToQueryViaXmlMapperReturningHashMap() throws Exception {

		final Map<String, Object> map = session
				.selectOne("selectFirstCountryAsMap");

    assertEquals(1, ((Number) getFromMap(map, "country_id") ).intValue());
    assertEquals("Afghanistan", getFromMap(map, "country"));
    assertNotNull(getFromMap(map, "last_update"));

	}

	@Test
	public void learnToQueryMapperReturningHashMapWithParameterInput() throws Exception {
		final Map<String, Object> map = 
		    session.selectOne("selectOneAsMapDynamic", 33);

		assertEquals(33, ((Number)getFromMap(map, "country_id")).intValue());
		assertEquals("Finland", getFromMap(map, "country"));
		assertNotNull(getFromMap(map, "last_update"));
	}

	@Test
	public void learnToQueryViaXmlMapperReturningListOfHashMaps()
			throws Exception {
		final List<Map<String, Object>> lmap = session
				.selectList("selectAsListOfMaps");

		assertEquals(109, lmap.size());
		final Map<String, Object> map109 = lmap.get(0);

		assertEquals(109, ((Number)getFromMap(map109, "country_id")).intValue());
		assertEquals("Zambia", getFromMap(map109, "country"));
	}

	
	/* ---[ Helper method ]--- */
	
	private Object getFromMap(Map<String, Object> map, String key) {
	  if (map.containsKey(key.toLowerCase())) {
	    return map.get(key.toLowerCase());
	  } else {
	    return map.get(key.toUpperCase());
	  }
	}
}
