package net.thornydev.mybatis.test.koan17.h2;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.thornydev.mybatis.test.util.FilmInStockId;
import net.thornydev.mybatis.test.util.FilmInStockParam;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.FlavorH2;

// http://code.google.com/p/mybatis/issues/detail?id=1
/**
 *
 */
@Category(FlavorH2.class)
public class Koan17 {

  static final Logger log = LoggerFactory.getLogger(Koan17.class);

  static SqlSession session;
  static SqlSessionFactory sessionFactory;
  static Koan17Mapper mapper;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {

    final String resource = "net/thornydev/mybatis/test/koan17/h2/koan17-config.xml";

    final InputStream inputStream = Resources.getResourceAsStream(resource);
    sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    inputStream.close();

    session = sessionFactory.openSession();
    mapper = session.getMapper(Koan17Mapper.class);

    /** define H2 stored procedures */
    session.update("defineStoredProcedures");

  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    if (session != null)
      session.close();
  }

  // can't get this one to work with only annotations
  @Ignore
  @Test
  public void learnToUseStoredProcWithHashMaps() {

    final Map<String, Integer> param = new HashMap<String, Integer>();
    param.put("filmId", 12);
    param.put("storeId", 1);

    final List<Map<String, Integer>> results = mapper
        .callFilmInStockWithHashMaps(param);

    log.debug("results : {}", results);

    assertEquals(3, results.size());
    assertEquals(Integer.valueOf(60), results.get(0).get("film_in_stock"));
    assertEquals(Integer.valueOf(61), results.get(1).get("film_in_stock"));
    assertEquals(Integer.valueOf(62), results.get(2).get("film_in_stock"));

  }

  // this one works if you define a resultmap in an xml file (see
  // Koan17Mapper)
  @Test
  public void learnToUseStoredProcWithDomainObjects() {

    final FilmInStockParam param = new FilmInStockParam();
    param.setFilmId(12);
    param.setStoreId(1);

    final List<FilmInStockId> results = mapper.callFilmInStock(param);

    log.debug("results : {}", results);

    assertEquals(3, results.size());

    FilmInStockId f = results.get(0);
    assertNotNull(f);
    assertEquals(60, f.getFilmId().intValue());

    f = results.get(1);
    assertNotNull(f);
    assertEquals(61, f.getFilmId().intValue());

    f = results.get(2);
    assertNotNull(f);
    assertEquals(62, f.getFilmId().intValue());
  }

  @Test
  public void learnToUseStoredProcWithDomainObjects2() {

    final FilmInStockParam param = new FilmInStockParam();
    param.setFilmId(12);
    param.setStoreId(1);

    final List<Integer> results = mapper.callFilmInStock2(param);

    log.debug("results : {}", results);

    assertEquals(3, results.size());

    assertEquals(60, results.get(0).intValue());
    assertEquals(61, results.get(1).intValue());
    assertEquals(62, results.get(2).intValue());
  }

  @Test
  public void learnToCallStoredFunction() {

    final Boolean b = mapper.inventoryInStore(1);

    assertNotNull(b);
    assertTrue(b);

  }

}
