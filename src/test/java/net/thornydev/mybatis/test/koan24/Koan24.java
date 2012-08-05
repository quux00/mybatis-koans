package net.thornydev.mybatis.test.koan24;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import net.thornydev.mybatis.test.domain.Actor;

import static org.junit.Assert.*;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class Koan24 {

  static SqlSession session;
  static SqlSessionFactory sessionFactory;
  static Koan24Mapper mapper;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    String resource = "net/thornydev/mybatis/test/koan24/koan24-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    inputStream.close();

    session = sessionFactory.openSession();
    mapper = session.getMapper(Koan24Mapper.class);
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    if (session != null) session.close();
  }

  @Test
  public void testRetrievingCopyOnWriteArrayList() {
    List<Actor> actors = mapper.getActorsInFilm(1);
    assertNotNull(actors);
    assertEquals(10, actors.size());
    Actor penelope = actors.get(0);
    Actor rock = actors.get(8);

    assertEquals("GUINESS", penelope.getLastName());
    assertEquals("DUKAKIS", rock.getLastName());

    // key test !!
    assertEquals(CopyOnWriteArrayList.class, actors.getClass());
  }

  @Test
  public void testRetrievingListOfCaseInsensitiveMaps() {
    List<Map<String,Object>> actors = mapper.getActorsInFilmAsMap(2);
    assertNotNull(actors);
    assertEquals(4, actors.size());
    assertEquals(CopyOnWriteArrayList.class, actors.getClass());

    Map<String,Object> m = actors.get(0);
    assertNotNull(m);
    assertEquals("FAWCETT", m.get("last_name"));
    assertEquals("BOB", m.get("FIRST_NAME"));

    m = actors.get(3);
    assertNotNull(m);
    assertEquals("DEPP", m.get("Last_Name"));
  }
}
