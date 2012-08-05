package net.thornydev.mybatis.koan.koan24;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import net.thornydev.mybatis.koan.domain.Actor;

import static org.junit.Assert.*;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

// By default when MyBatis returns a List, it uses java.util.ArrayList.
// And when it returns a Map, it uses java.util.HashMap.
// In this Koan you will make MyBatis behave differently.
// Suppose for thread-safety reasons that instead of an ArrayList,
// you want a java.util.concurrent.CopyOnWriteArrayList.  You could
// let MyBatis return an ArrayList and then copy everything into a
// CopyOnWriteArrayList, but that is not efficient.
//
// Your first task in the "testRetrievingCopyOnWriteArrayList" koan
// is to have MyBatis return a CopyOnWriteArrayList. There are at
// least two ways I know of to do this.  I have implemented one in
// the completed koans and outlined the other in this StackOverflow
// answer: http://stackoverflow.com/a/11596014/871012.  (Don't
// read this article until you have come up with your own answer!)
//
// Your second task is to be able to retrieve column names from
// a Map returned by MyBatis in a case-insensitive manner.
// When you tell MyBatis to return a map (say with resultType="hashmap").
// as of MyBatis-3.1.1 and previous versions, there is no way
// to tell MyBatis to have the keys in the map all be uppercase or lowercase.
// Which it is is determined by the underlying JDBC driver.  In some
// cases you can specify a column alias in your SQL with upper or lower case
// and the map key will match that - this works with MySQL for instance,
// but not with H2 and PostgreSQL.
//
// In the second test in this koan,
// "testRetrievingListOfCaseInsensitiveMaps", I want you to return
// a List<Map> where the List is of type CopyOnWriteArrayList and
// the Map somehow doesn't care about the case of the keys you pass in:
// asking for map.get("FOO") returns the same as map.get("foo") or
// map.get("Foo").  There are a couple of ways to do this.  Again,
// I have an example in the completed koans and another outlined
// in a StackOverflow answer: http://stackoverflow.com/a/11732674/871012.
// You might also search apache commons to look at maps they have
// defined for this.  Note that they built their solution before generics
// and haven't retrofitted it, so that presents its own challenge, but
// an interesting one at that.
//
// Good luck.
public class Koan24 {

  static SqlSession session;
  static SqlSessionFactory sessionFactory;
  static Koan24Mapper mapper;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    String resource = "net/thornydev/mybatis/koan/koan24/koan24-config.xml";
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
