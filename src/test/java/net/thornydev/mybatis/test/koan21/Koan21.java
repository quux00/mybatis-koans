package net.thornydev.mybatis.test.koan21;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.thornydev.mybatis.test.domain.Film;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

// make sure you do Koan20 before this one, as it picks up where that one left off
public class Koan21 {

  static SqlSession session;
  static SqlSessionFactory sessionFactory;
  static Koan21Mapper mapper;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    String resource = "net/thornydev/mybatis/test/koan21/koan21-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    inputStream.close();

    session = sessionFactory.openSession();
    mapper = session.getMapper(Koan21Mapper.class);
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


  @Test
  public void learnToUseDynamicWhereTag() {
    // first pass - give it no data at all => should return all films
    Film f = new Film();
    List<String> films = mapper.selectFilmByTitleRatingAndOrMinLength(f);
    assertNotNull(films);
    assertEquals(1000, films.size());

    // now give it a rating
    f.setRating("NC-17");
    films = mapper.selectFilmByTitleRatingAndOrMinLength(f);
    assertNotNull(films);
    assertEquals(210, films.size());

    // now give it a title word (or partial word)
    f.setTitle("%GO%");
    films = mapper.selectFilmByTitleRatingAndOrMinLength(f);
    assertNotNull(films);
    assertEquals(6, films.size());

    // finally, give it a min length
    f.setLength(100);
    films = mapper.selectFilmByTitleRatingAndOrMinLength(f);
    assertNotNull(films);
    assertEquals(3, films.size());
  }

  @Test
  public void learnToUseForeachTag() {
    List<String> films = mapper.selectFilmByRentalRates(new double[] {4.99, 2.99});
    assertNotNull(films);
    assertEquals(659, films.size());
  }

  @Test
  public void learnToUseForeachTagWithWhereTag() {
    List<BigDecimal> rates = new ArrayList<BigDecimal>();
    rates.add(BigDecimal.valueOf(2.99));
    rates.add(BigDecimal.valueOf(4.99));
    List<String> films = mapper.selectFilmByRentalRates2(rates);
    assertNotNull(films);
    assertEquals(659, films.size());

    // now try it with null => should get back all films
    // this will only work if you use a dynamic where tag
    films = mapper.selectFilmByRentalRates2(null);
    assertNotNull(films);
    assertEquals(1000, films.size());
  }

  @Test
  public void learnToUseDynamicSetTagToAllowVariableNumberOfSetClauses() {
    Film orig = mapper.getFilmById(222);

    // first try update with no fields add - should result in no update
    Film modFilm = new Film(222);
    mapper.updateFilmIfNecessary(modFilm);

    Film firstUpdateFilm = mapper.getFilmById(222);
    assertNotNull(firstUpdateFilm);
    assertTrue(orig.equals(firstUpdateFilm));

    // next update rentalRate only
    modFilm.setRentalRate(BigDecimal.valueOf(10.00));
    mapper.updateFilmIfNecessary(modFilm);
    Film secondUpdateFilm = mapper.getFilmById(222);
    assertNotNull(secondUpdateFilm);
    assertFalse(orig.equals(secondUpdateFilm));
    assertEquals(0, BigDecimal.valueOf(10.00).compareTo(secondUpdateFilm.getRentalRate()));
    assertEquals("DESERT POSEIDON", secondUpdateFilm.getTitle());  // original value
    assertEquals(4, secondUpdateFilm.getRentalDuration());  // original value

    // next update title
    String newTitle = "That Voodoo That You Do";
    modFilm.setTitle(newTitle);
    mapper.updateFilmIfNecessary(modFilm);
    Film thirdUpdateFilm = mapper.getFilmById(222);
    assertNotNull(thirdUpdateFilm);
    assertFalse(orig.equals(secondUpdateFilm));
    assertEquals(newTitle, thirdUpdateFilm.getTitle());
    assertEquals(4, thirdUpdateFilm.getRentalDuration());  // original value

    // finally update rentalDuration
    modFilm.setRentalDuration(40);
    mapper.updateFilmIfNecessary(modFilm);
    Film fourthUpdateFilm = mapper.getFilmById(222);
    assertNotNull(fourthUpdateFilm);
    assertTrue(modFilm.equals(fourthUpdateFilm));
  }
}
