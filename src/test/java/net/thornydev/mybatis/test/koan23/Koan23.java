package net.thornydev.mybatis.test.koan23;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import util.FlavorH2;
import util.FlavorMySQL;

// Recommended that you do Koan22 before this one, as it builds on the pattern
// and lessons learned there.
// there is also an enum type handler
// http://stackoverflow.com/questions/10219253/mybatis-enum-usage
// http://stackoverflow.com/questions/10562895/enum-constant-in-mybatiss-sql-query
@org.junit.experimental.categories.Category({FlavorMySQL.class, FlavorH2.class})
public class Koan23 {

  static SqlSessionFactory sessionFactory;
  SqlSession session;
  Koan23Mapper mapper;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    String resource = "net/thornydev/mybatis/test/koan23/koan23-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    inputStream.close();
  }

  @Before
  public void setUp() throws Exception {
    session = sessionFactory.openSession();
    mapper = session.getMapper(Koan23Mapper.class);
  }

  @After
  public void tearDown() throws Exception {
    if (session != null) session.close();
  }

  @Test
  public void learnToConvertQueryResultIntoJavaEnum() {
    Category c = mapper.getCategoryByName("Horror");
    assertEquals(c, Category.HORROR);
  }

  @Test
  public void learnToUseJavaEnumsInComplexQuery() {
    FilmWithCategories fwc = mapper.getFilmById(25);
    assertNotNull(fwc);
    assertEquals(25, fwc.getId());
    assertEquals("ANGELS LIFE", fwc.getTitle());
    List<Category> lc = fwc.getCategories();
    assertEquals(1, lc.size());
    assertNotNull(lc.get(0));
    assertEquals(Category.NEW, lc.get(0));
    assertEquals("New", lc.get(0).toString());
  }

  @Test
  public void learnToConvertFromDatabaseEnumToJavaEnum() {
    FilmWithCategories fwc = mapper.getFilmWithRatingById(25);
    assertNotNull(fwc);
    assertEquals(25, fwc.getId());
    assertEquals("ANGELS LIFE", fwc.getTitle());
    assertEquals(Rating.G, fwc.getRating());

    // now query for one with a NC-17 rating => this one can't
    // be autoconverted by MyBatic because NC-17 != NC_17 textually
    fwc = mapper.getFilmWithRatingById(38);
    assertNotNull(fwc);
    assertEquals(38, fwc.getId());
    assertEquals("ARK RIDGEMONT", fwc.getTitle());
    assertEquals(Rating.NC_17, fwc.getRating());
  }

  @Test
  public void learnToConvertFromJavaEnumToDatabaseEnum() {
    FilmWithCategories fwc = mapper.getFilmWithRatingById(25);
    fwc.setRating(Rating.PG_13);

    int n = mapper.updateFilmRating(fwc);
    assertEquals(1, n);
  }

  @Test
  public void learnToInsertFromJavaEnum() {
    FilmWithCategories fwc = mapper.getFilmById(25);
    fwc.addCategory(Category.FAMILY);
    fwc.addCategory(Category.COMEDY);

    int n = mapper.deleteAllCategoriesForFilm(fwc);
    assertEquals(1, n);

    Date now = new Date();
    for (Category c: fwc.getCategories()) {
      n = mapper.addCategoryForFilm(fwc, c, now);
      assertEquals(1, n);
    }

    FilmWithCategories fwc25 = mapper.getFilmById(25);
    assertNotNull(fwc25);
    assertEquals("ANGELS LIFE", fwc.getTitle());
    List<Category> lc = fwc.getCategories();
    assertEquals(3, lc.size());
    assertNotNull(lc.get(0));
    assertEquals(1, Collections.frequency(lc, Category.COMEDY));
    assertEquals(1, Collections.frequency(lc, Category.FAMILY));
    assertEquals(1, Collections.frequency(lc, Category.NEW));
  }

}
