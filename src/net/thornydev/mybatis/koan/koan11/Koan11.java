package net.thornydev.mybatis.koan.koan11;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

// In Koan09 our domain objects were modeled after a "has one" or "belongs to"
// set of relationships. One City "has one" or "belongs to" a Country, so the
// City object had a reference to a single Country, but the Country domain
// object did not have a list of City objects that were in it.
//
// In Koan11 we go the opposite direction and model the "has-many" directionality
// of relationship. For that we use the language and film tables from the sakila
// database. A language has many films, while a film has only one language.
// The Language object has a list of films and we get to learn to do that mapping
// using the MyBatis <collection> element.
//
// We also change the namespace of the mapper from the generic "KoanXXMapper" to
// the actual object type it is mapping - LanguageMapper in this case.
//
// Take a look at the sakila data in the film and language tables. Not all
// languages have films and we will test how to handle that as well.
//
// Note: For Koan11 you will use the FilmK11 and LanguageK11 classes in the koan11
// directory/package. In a later koan, we will use the domain.Film and
// domain.Language classes - ignore them for now.
// 
// To complete this koan test you will need to edit:
// 1. the mapper xml file to have the right SQL queries and MyBatis XML entries
// 2. the config xml file to set up type aliases and a proper namespace
public class Koan11 {

  static SqlSessionFactory sessionFactory;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    String resource = "net/thornydev/mybatis/koan/koan11/koan11-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    inputStream.close();
  }

  @Test
  public void learnToQueryHasManyRelationshipUsingCollection() {
    SqlSession session = null;
    try {
      session = sessionFactory.openSession();
      LanguageMapper mapper = session.getMapper(LanguageMapper.class);

      LanguageK11 lang = mapper.getLanguageById(1);
      assertNotNull(lang);
      assertEquals(1, lang.getId());
      assertEquals("English", lang.getName().trim());
      assertNotNull(lang.getFilms());

      List<FilmK11> lf = lang.getFilms();
      assertEquals(1000, lf.size());

      // Note that the test expects the "last" film to be first in the list
      // so make your SQL match this expectation
      FilmK11 f = lf.get(0);
      assertEquals(1000, f.getId());
      assertEquals("ZORRO ARK", f.getTitle());
      assertTrue( Pattern.
                  compile("Trailers.+Commentaries.+Behind the Scenes", Pattern.CASE_INSENSITIVE).
                  matcher(f.getSpecialFeatures()).
                  find() );
      // we didn't populate the rating field in the query so should be null
      assertNull(f.getRating());

    } finally {
      if (session != null) session.close();
    }
  }

  @Test
  public void learnToQueryHasManyRelationshipThatHasNoEntitiesInThatRelationship() {
    SqlSession session = null;
    try {
      session = sessionFactory.openSession();
      LanguageMapper mapper = session.getMapper(LanguageMapper.class);

      // NOTE: if this test fails but "learnToQueryHasManyRelationshipUsingCollection"
      // succeeded, you need to modify the SQL in koan11-mapper to return a
      // language even if it is has no films (Hint: an inner join will not work)
      LanguageK11 lang = mapper.getLanguageById(3);
      assertNotNull(lang);
      assertEquals(3, lang.getId());
      assertEquals("Japanese", lang.getName().trim());
      assertNotNull(lang.getFilms());
      // should be no films in Japanese in the db
      assertEquals(0, lang.getFilms().size());

    } finally {
      if (session != null) session.close();
    }
  }
}
