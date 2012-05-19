package net.thornydev.mybatis.koan.koan07;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.thornydev.mybatis.koan.domain.Country;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

public class Koan07 {

  static SqlSessionFactory sessionFactory;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    String resource = "net/thornydev/mybatis/koan/koan07/koan07-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    inputStream.close();
  }

  @Test
  public void learnToQueryUsingMapperAnnotationsNoParams() throws Exception {
    SqlSession session = null;

    try {
      session = sessionFactory.openSession();
      Koan07Mapper mapper = session.getMapper(Koan07Mapper.class);
      int totalCountries = mapper.getCountryCount();

      assertEquals(109, totalCountries);

    } finally {
      if (session != null) session.close();
    }
  }

  @Test
  public void learnToQueryUsingMapperAnnotationsWithParams() throws Exception {
    SqlSession session = null;

    try {
      session = sessionFactory.openSession();
      Koan07Mapper mapper = session.getMapper(Koan07Mapper.class);
      Country c = mapper.getCountryById(1);

      assertNotNull(c);
      assertEquals(1, c.getId());
      assertEquals("Afghanistan", c.getCountry());

      DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date date = format.parse("2006-02-15 09:44:00");
      assertEquals(date, c.getLastUpdate());

    } finally {
      if (session != null) session.close();
    }
  }

  @Test
  public void learnToQueryViaAnnotationsReturningHashMapOfCountriesKeyedById() throws Exception {
    SqlSession session = null;

    try {
      session = sessionFactory.openSession();

      Koan07Mapper mapper = session.getMapper(Koan07Mapper.class);
      Map<Integer,Country> countriesMap = mapper.getAllCountriesMappedById();

      assertEquals(109, countriesMap.size());

      Country c33 = countriesMap.get(33);
      assertEquals(33, c33.getId());
      assertEquals("Finland", c33.getCountry());

      DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date date = format.parse("2006-02-15 09:44:00");
      assertEquals(date, c33.getLastUpdate());
    } finally {
      if (session != null) session.close();
    }
  }

  @Test
  public void learnToQueryViaAnnotationsTakingTwoParams() throws Exception {
    SqlSession session = null;

    try {
      session = sessionFactory.openSession();
      Koan07Mapper mapper = session.getMapper(Koan07Mapper.class);

      List<Country> lc = mapper.getCountryRange(22, 33);

      assertEquals(12, lc.size());
      Country finland = lc.get(11);
      assertEquals("Finland", finland.getCountry());

    } finally {
      if (session != null) session.close();
    }
  }

  @Test
  public void learnToQueryViaAnnotationsUsingRowBounds() throws Exception {
    SqlSession session = null;
    try {
      session = sessionFactory.openSession();

      int offset = 21;
      int limit = 12;
      RowBounds rb = new RowBounds(offset, limit);
      Koan07Mapper mapper = session.getMapper(Koan07Mapper.class);
      List<Country> lc = mapper.getCountryRange2(rb);

      assertEquals(12, lc.size());
      Country finland = lc.get(11);
      assertEquals("Finland", finland.getCountry());

    } finally {
      if (session != null) session.close();
    }
  }

}
