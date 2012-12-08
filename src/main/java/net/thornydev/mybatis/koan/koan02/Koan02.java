package net.thornydev.mybatis.koan.koan02;

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
    String resource = "net/thornydev/mybatis/koan/koan02/koan02-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    inputStream.close();
  }

  @Before
  public void setUp() throws Exception {
    // TODO: create session
    session = null;
  }

  @After
  public void tearDown() throws Exception {
    // TODO: it is important to close your resources ...

  }

  @Test
  public void learnToQueryViaXmlMapperReturningHashMap() throws Exception {
    // TODO: call "selectFirstCountryAsMap" mapped query
    // TODO: fill in "?" generic unknown placeholders
    Map<?,?> map = null;

    assertEquals(1, ((Number) getFromMap(map, "country_id") ).intValue());
    assertEquals("Afghanistan", map.get("country"));
    assertNotNull(map.get("last_update"));
  }

  @Test
  public void learnToQueryMapperReturningHashMapWithParameterInput() throws Exception {
    // TODO: call "selectOneAsMapDynamic" mapped query, passing in id 33 as param
    Map<Object,Object> map = null;

    assertEquals(33, ((Number)getFromMap(map, "country_id")).intValue());
    assertEquals("Finland", map.get("country"));
    assertNotNull(map.get("last_update"));
  }

  @Test
  public void learnToQueryViaXmlMapperReturningListOfHashMaps() throws Exception {
    // TODO: query for a list
    // TODO: fill in "?" generic unknown placeholders
    final List<Map<?,?>> lmap = session.selectList("selectAsListOfMaps");

    assertEquals(109, lmap.size());
    // TODO: fill in "?" generic unknown placeholders
    final Map<?,?> map109 = lmap.get(0);

    assertEquals(109, ((Number)getFromMap(map109, "country_id")).intValue());
    assertEquals("Zambia", map109.get("country"));
  }

  /* ---[ Helper method ]--- */

  // TODO: fill in "?" generic unknown placeholders
  private Object getFromMap(Map<?, ?> map, String key) {
    if (map.containsKey(key.toLowerCase())) {
      return map.get(key.toLowerCase());
    } else {
      return map.get(key.toUpperCase());
    }
  }
}
