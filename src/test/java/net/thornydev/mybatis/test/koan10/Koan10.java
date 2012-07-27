package net.thornydev.mybatis.test.koan10;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;

import net.thornydev.mybatis.test.domain.City;
import net.thornydev.mybatis.test.domain.Country;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

public class Koan10 {

  static SqlSessionFactory sessionFactory;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    final String resource = "net/thornydev/mybatis/test/koan10/koan10-config.xml";
    final InputStream inputStream = Resources.getResourceAsStream(resource);
    sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    inputStream.close();
  }

  @Test
  public void learnToUseResultMapForSingleTableQuery() {

    SqlSession session = null;

    try {

      session = sessionFactory.openSession();

      final Koan10Mapper mapper = session.getMapper(Koan10Mapper.class);

      final Country c = mapper.getCountryById(33);

      assertEquals(33, c.getId());
      assertEquals("Finland", c.getCountry());
      assertNotNull(c.getLastUpdate());

    } finally {
      if (session != null)
        session.close();
    }
  }

  @Test
  public void learnToUseResultMapForTwoTableAssociation() {

    SqlSession session = null;

    try {

      session = sessionFactory.openSession();
      final Koan10Mapper mapper = session.getMapper(Koan10Mapper.class);
      final City city = mapper.getCityById(544);

      assertEquals(544, city.getId());
      assertEquals("Toulouse", city.getCity());
      assertNotNull(city.getLastUpdate());

      final Country co = city.getCountry();
      assertNotNull(co);
      assertEquals("France", co.getCountry());
      assertNotNull(co.getLastUpdate());

    } finally {
      if (session != null)
        session.close();
    }
  }

  @Test
  public void learnToUseNestedSelectForAssociation() {
    SqlSession session = null;
    try {
      session = sessionFactory.openSession();
      final int cityCount = (Integer) session.selectOne("getCityCount");

      final Koan10Mapper mapper = session.getMapper(Koan10Mapper.class);
      final List<City> lc = mapper.getCities();

      final City first = lc.iterator().next();
      assertEquals(1, first.getId());
      assertNotNull(first.getCountry());

      assertEquals(cityCount, lc.size());
      final City city = lc.get(543);
      assertEquals(544, city.getId());
      assertEquals("Toulouse", city.getCity());
      assertNotNull(city.getLastUpdate());

      final Country co = city.getCountry();
      assertNotNull(co);
      assertEquals("France", co.getCountry());
      assertNotNull(co.getLastUpdate());

    } finally {
      if (session != null)
        session.close();
    }
  }

}
