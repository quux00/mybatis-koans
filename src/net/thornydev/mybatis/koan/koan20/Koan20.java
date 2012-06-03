package net.thornydev.mybatis.koan.koan20;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;

import net.thornydev.mybatis.koan.domain.Film;
import net.thornydev.mybatis.koan.koan21.Koan21Mapper;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
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
  public void learnPoundVsDollarPrefixDifference() {
    Film f = new Film();
    f.setTitle("%NECKLACE%");
    List<String> films = mapper.selectLongFilmsByTitleWithPound(f);

    assertNotNull(films);
    assertEquals(3, films.size());
    assertEquals("NECKLACE OUTBREAK", films.get(0));
    assertEquals("OPPOSITE NECKLACE", films.get(2));

    Film f2 = new Film();
    f2.setTitle("NECKLACE");
    List<String> films2 = mapper.selectLongFilmsByTitleWithDollar(f2);

    assertNotNull(films2);
    assertEquals(3, films2.size());
    assertEquals("NECKLACE OUTBREAK", films2.get(0));
    assertEquals("OPPOSITE NECKLACE", films2.get(2));
  }

}
