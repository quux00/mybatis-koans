package net.thornydev.mybatis.koan.koan03;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.thornydev.mybatis.koan.domain.Country;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
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
// 3. the koan03-mapper.xml file to have the right SQL queries and MyBatis XML entries
public class Koan03 {

  static SqlSessionFactory sessionFactory;
  SqlSession session;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    String resource = "net/thornydev/mybatis/koan/koan03/koan03-config.xml";
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
    // TODO: call "selectFirstCountry" query to return a Country object
    Country c = null;

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
    // TODO: query for a list of all Country objects
    List<Country> lmap = null;

    assertEquals(109, lmap.size());
    Country c109 = lmap.get(0);

    assertEquals(109, c109.getId());
    assertEquals("Zambia", c109.getCountry());
  }

  // NOTE: you will only be able to do this koan test if you are
  //       using MyBatis-3.0.3 or higher, as this feature was removed
  //       and then added back at this release point
  @Test
  public void learnToQueryViaXmlMapperReturningHashMapOfCountriesKeyedById() throws Exception {
    // TODO: call query to return a map of ids to Country object.
    //       This should return a map of all 109 countries and the key to
    //       the map is the id of the country in the database
    // TODO: fill in the ?,? of the Map to their proper types
    Map<?,?> countriesMap = null;

    assertEquals(109, countriesMap.size());

    // TODO: when you fill in the "?" generic placeholders above
    //       this cast will no longer be necessary - remove it
    Country c33 = (Country)countriesMap.get(33);
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
