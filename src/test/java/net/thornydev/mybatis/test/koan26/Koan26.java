package net.thornydev.mybatis.test.koan26;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.Map;

import net.thornydev.mybatis.test.domain.Country;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class Koan26 {
  static SqlSession session;
  static SqlSessionFactory sessionFactory;
  static Koan26Mapper mapper;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    String resource = "net/thornydev/mybatis/test/koan26/koan26-config.xml";
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
    Map<Integer,Country> m = mapper.getAllCountriesMappedById();
    assertNotNull(m);
    assertEquals(109, m.size());
    
    assertEquals("Afghanistan", m.get(1).getCountry());
    assertEquals("Azerbaijan", m.get(10).getCountry());
    assertEquals("Zambia", m.get(109).getCountry());
    assertFalse(m.containsKey(110));
  }
  
  @Test
  public void learnToReturnMapWithIdMappedToString() {
    final Koan26ResultHandler rh = new Koan26ResultHandler();
    session.select("getAllCountries", rh);
    Map<Integer,String> m = rh.getIdNameMap();
    assertNotNull(m);
    assertEquals(109, m.size());
    
    assertEquals("Afghanistan", m.get(1));
    assertEquals("Azerbaijan", m.get(10));
    assertEquals("Zambia", m.get(109));
    assertFalse(m.containsKey(110));
  }
}
