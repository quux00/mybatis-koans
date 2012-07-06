package net.thornydev.mybatis.test.koan02;

import static org.junit.Assert.*;

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

		assertEquals(Integer.valueOf(1), map.get("country_id"));
		assertEquals("Afghanistan", map.get("country"));
		assertNotNull(map.get("last_update"));

	}

	@Test
	public void learnToQueryMapperReturningHashMapWithParameterInput()
			throws Exception {
		final Map<Object, Object> map = session.selectOne(
				"selectOneAsMapDynamic", 33);

		assertEquals(Integer.valueOf(33), map.get("country_id"));
		assertEquals("Finland", map.get("country"));
		assertNotNull(map.get("last_update"));
	}

	@Test
	public void learnToQueryViaXmlMapperReturningListOfHashMaps()
			throws Exception {
		final List<Map<String, Object>> lmap = session
				.selectList("selectAsListOfMaps");

		assertEquals(109, lmap.size());
		final Map<String, Object> map109 = lmap.get(0);

		assertEquals(Integer.valueOf(109), map109.get("country_id"));
		assertEquals("Zambia", map109.get("country"));
	}

}
