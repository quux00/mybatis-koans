package net.thornydev.mybatis.koan.koan15;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Date;

import net.thornydev.mybatis.koan.domain.Address;
import net.thornydev.mybatis.koan.domain.City;
import net.thornydev.mybatis.koan.domain.Country;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

// Quote from MyBatis User Guide:  "The JDBC type is only required for
// nullable columns upon insert, update or delete."

public class Koan15 {

  static SqlSession session;
  static SqlSessionFactory sessionFactory;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    String resource = "net/thornydev/mybatis/koan/koan15/koan15-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    inputStream.close();

    session = sessionFactory.openSession();
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    if (session != null) session.close();
  }

  @Test
  public void learnToInsertDomainObjectThatReferencesAnotherDomainObject() {
    int cntBefore = (Integer) session.selectOne("getCount", "city");

    Country c = session.selectOne("getCountryById", 22);

    City city = new City(1000, "FooCity", new Date());
    city.setCountry(c);

    session.insert("insertCity", city);
    int cntAfter = (Integer) session.selectOne("getCount", "city");

    assertEquals(cntAfter, cntBefore + 1);

    session.rollback();
  }

  @Test
  public void learnToInsertDomainObjectWithNullValues() {
    City city = session.selectOne("getCityById", 375);
    assertNotNull(city);
    assertNotNull(city.getCountry());
    assertEquals("Okara", city.getCity());
    assertEquals("Pakistan", city.getCountry().getCountry());

    Address addr = new Address.Builder().
    id(1000).
    address("100 Foo St.").
    address2(null).  // could also just leave it off, but here to be explicit
    district("Bar").
    city(city).
    postalCode(null).
    phone("555-8675-309").
    build();

    int before = (Integer) session.selectOne("getCount", "address");
    int n = session.insert("insertAddress", addr);
    assertEquals(1, n);
    int after = (Integer) session.selectOne("getCount", "address");
    assertEquals(after, before + 1);

    session.rollback();
  }
}
