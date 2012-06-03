package net.thornydev.mybatis.koan.koan20;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;

import net.thornydev.mybatis.koan.domain.Film;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class Koan20 {

  static SqlSession session;
  static SqlSessionFactory sessionFactory;
  static Koan20Mapper mapper;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    String resource = "net/thornydev/mybatis/koan/koan20/koan20-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    inputStream.close();

    session = sessionFactory.openSession();
    mapper = session.getMapper(Koan20Mapper.class);
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    if (session != null) session.close();
  }

  @Test
  public void testUsingIfSelectorWithTitle() {
    Film f = new Film();
    f.setTitle("%NECKLACE%");
    List<String> films = mapper.selectLongFilmsByTitleOrRating(f);

    assertNotNull(films);
    assertEquals(3, films.size());
    assertEquals("NECKLACE OUTBREAK", films.get(0));
    assertEquals("OPPOSITE NECKLACE", films.get(2));
  }

  @Test
  public void testUsingIfSelectorWithRating() {
    Film f = new Film();
    f.setRating("PG");
    List<String> films = mapper.selectLongFilmsByTitleOrRating(f);
    assertNotNull(films);
    assertEquals(131, films.size());
    assertEquals("STAR OPERATION", films.get(3));
    
    // http://code.google.com/p/mybatis/issues/detail?id=103
  }

  @Test
  public void testUsingIfSelectorWithTitleAndRating() {
    Film f = new Film();
    f.setRating("PG");
    f.setTitle("%LUCK%");
    List<String> films = mapper.selectLongFilmsByTitleOrRating(f);
    assertNotNull(films);
    assertEquals(2, films.size());
    assertEquals("MIGHTY LUCK", films.get(1));
  }
}
