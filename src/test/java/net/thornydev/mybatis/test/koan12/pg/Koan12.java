package net.thornydev.mybatis.test.koan12.pg;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.thornydev.mybatis.test.domain.Film;
import net.thornydev.mybatis.test.domain.Language;
import net.thornydev.mybatis.test.util.Year;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import util.FlavorPostgreSQL;

@Category(FlavorPostgreSQL.class)
public class Koan12 {

	static SqlSessionFactory sessionFactory;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		final String resource = "net/thornydev/mybatis/test/koan12/pg/koan12-config.xml";
		final InputStream inputStream = Resources.getResourceAsStream(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		inputStream.close();
	}

	@Test
	public void learnToUseTypeHandlerForDomainSpecificFieldTypesForQueries() {

		SqlSession session = null;

		try {

			session = sessionFactory.openSession();

			final LanguageMapper mapper = session
					.getMapper(LanguageMapper.class);

			final Language lang = mapper.getLanguageById(1);
			assertNotNull(lang);
			assertEquals(1, lang.getId());
			assertEquals("English", lang.getName().trim());
			assertNotNull(lang.getFilms());

			final List<Film> lf = lang.getFilms();
			assertEquals(1000, lf.size());

			final Film f = lf.get(0);
			assertEquals(1000, f.getId());
			assertEquals("ZORRO ARK", f.getTitle());
			assertNotNull(f.getReleaseYear());
			assertEquals(new Year("2006"), f.getReleaseYear());
			assertEquals(BigDecimal.valueOf(4.99), f.getRentalRate());
			assertNotNull(f.getSpecialFeatures());
			assertEquals(3, f.getSpecialFeatures().size());

			final List<String> expectedSpecFeat = new ArrayList<String>();
			expectedSpecFeat.add("Trailers");
			expectedSpecFeat.add("Commentaries");
			expectedSpecFeat.add("Behind the Scenes");
			assertTrue(f.getSpecialFeatures().containsAll(expectedSpecFeat));

		} finally {
			if (session != null)
				session.close();
		}
	}

	@Test
	public void learnToUseTypeHandlerForDomainSpecificFieldTypesForDMLStatements() {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			final FilmMapper mapper = session.getMapper(FilmMapper.class);

			Film f = new Film(1000);
			f.setReleaseYear(new Year("2012"));
			f.setReplacementCost(BigDecimal.valueOf(25.95));

			final int n = mapper.updateYearAndReplacementCost(f);
			assertEquals(1, n);

			// test to make sure it was updated as expected
			f = mapper.getFilmById(1000);
			assertEquals("ZORRO ARK", f.getTitle());
			assertEquals("2012", f.getReleaseYear().toString());
			assertEquals(BigDecimal.valueOf(25.95), f.getReplacementCost());

		} finally {
			if (session != null) {
				session.rollback();
				session.close();
			}
		}
	}
}
