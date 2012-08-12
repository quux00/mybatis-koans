package net.thornydev.mybatis.koan.koan26;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.Map;

import net.thornydev.mybatis.koan.domain.Country;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

// Given the query:
//   SELECT country_id AS id, country
//   FROM country
// 
// The challenges:
//  1. Koan 26-2: get MyBatis to return a Map<Integer,Country> (not a List<Map>)
//     where the Integer is the is id (country id) and the Country object 
//     has its id and country (name) field filled in.
//  2. Koan 26-1: get MyBatis to return a Map<Integer,String> 
//     (not a List<Map<Integer,String>, nor a List<Map<String,Object>)
//     where the Integer is the id (country id) and the String is the country name.
public class Koan26 {

  static SqlSession session;
  static SqlSessionFactory sessionFactory;
  static Koan26Mapper mapper;
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    String resource = "net/thornydev/mybatis/koan/koan26/koan26-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    inputStream.close();

    session = sessionFactory.openSession();
    mapper = session.getMapper(Koan26Mapper.class);
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    if (session != null) session.close();
  }

  @Test
  public void learnToReturnMapWithIdMappedToCountryDomainObject() {
    // TODO: fill in code to return Map<Integer,Country> that will
    // pass the asserts
    Map<Integer,Country> m = null;
    assertNotNull(m);
    assertEquals(109, m.size());
    
    assertEquals("Afghanistan", m.get(1).getCountry());
    assertEquals("Azerbaijan", m.get(10).getCountry());
    assertEquals("Zambia", m.get(109).getCountry());
    assertFalse(m.containsKey(110));
  }

  @Test
  public void learnToReturnMapWithIdMappedToString() {
    // TODO: fill in code to return Map<Integer,String> that will
    // pass the asserts
    Map<Integer,String> m = null;
    assertNotNull(m);
    assertEquals(109, m.size());
    
    assertEquals("Afghanistan", m.get(1));
    assertEquals("Azerbaijan", m.get(10));
    assertEquals("Zambia", m.get(109));
    assertFalse(m.containsKey(110));
  }
}
