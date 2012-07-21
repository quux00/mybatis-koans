package net.thornydev.mybatis.test.koan03;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.thornydev.mybatis.test.domain.Country;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

// In Koan03, we again create an xml mapper file but this time it returns
// a domain object (a Country object that maps to the country table
// in the sakila database).
//
// To complete this koan test you will need to edit:
// 1. all the TODO entries in this koan
// 2. the koan03-config.xml file to specify a TypeAlias
// 3. the mapper xml file to have the right SQL queries and MyBatis XML entries
public class Koan03 {

  static SqlSessionFactory sessionFactory;
  SqlSession session;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    String resource = "net/thornydev/mybatis/test/koan03/koan03-config.xml";
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

    assertNotNull(c.getLastUpdate());
    String dateStr = c.getLastUpdate().toString();
    assertTrue( Pattern.
                compile("Feb\\s+15.+2006").
                matcher( dateStr ).
                find() );
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

    assertNotNull(c33.getLastUpdate());
    String dateStr = c33.getLastUpdate().toString();
    assertTrue( Pattern.
                compile("Feb\\s+15.+2006").
                matcher( dateStr ).
                find() );
  }
}
