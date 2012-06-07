package net.thornydev.mybatis.koan.koan23;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

// It is recommended that you do Koan22 before this one, as it builds on the pattern
// and lessons learned there.
// 
// On stackoverflow and in the MyBatis google group I have seen a number of
// questions on how to use/handle Java enums and database enums with MyBatis.
// In this koan, we take those on.
// 
// We model the sakila category table as a single Java enum and we model
// the rating field in the film table as a Java enum.  In MySQL and PostgreSQL,
// the rating column is also implemented as database enum, which poses some
// special handling requirements in the case of PostgreSQL.
// 
// To start, familiarize yourself with the built-in MyBatis EnumTypeHandler.
// Use it and get a feel for how it works. Once you understand, you'll be
// able to see circumstances in which you need to supplement its functionality
// with your own handler.
// 
// A few notes from my explorations (with mybatis 3.1.1):
// * Implementing a MyBatis ObjectFactory will not help you, as that is not
//   called for a Java enum type (at least I couldn't get it to work).
// * In the case of the Category enum, there are actually two pieces of
//   information - its id and its name. I was not able to find a way to get
//   MyBatis to load both from a database query (you can do one or the other), 
//   so I modeled the Category enum to hard code its ID to match the IDs in 
//   the sakila db. If you find a way to load both (that isn't more trouble 
//   than it's worth), I'd love to see your solution.
// 
// To complete this koan, you will need to:
// 1. Complete all TODOs in the koan23-mapper.xml
// 2. Implement/override toString method of the Category enum.
// 3. Implement/override the fromString and toString methods of the Rating enum.
// 4. Implement a RatingTypeHandler and declare it in the koan23-config.xml file.
public class Koan23 {

  static SqlSessionFactory sessionFactory;
  SqlSession session;
  Koan23Mapper mapper;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    String resource = "net/thornydev/mybatis/koan/koan23/koan23-config.xml";
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
    
    for (Category c: fwc.getCategories()) {
      n = mapper.addCategoryForFilm(fwc, c);
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
