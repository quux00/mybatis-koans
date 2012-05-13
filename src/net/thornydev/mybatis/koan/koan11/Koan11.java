package net.thornydev.mybatis.koan.koan11;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.thornydev.mybatis.koan.domain.Film;
import net.thornydev.mybatis.koan.domain.Language;
import net.thornydev.mybatis.koan.util.Year;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

// Koan11 investigates the use of the MyBatis TypeHandler in order to 
// handle domain specific types or to do special processing on a known type.
// 
// We also do exercises to test the TypeHandler in both directions - pulling
// data out (queries) and pushing data into the database (DML).
// 
// In order to use the MyBatis TypeHandler extend 
// org.apache.ibatis.type.BaseTypeHandler<T>, where T is the data type of you 
// want to return from queries or convert to a database type for DML statements.
// 
// In this koan, we use net.thornydev.mybatis.koan.domain.Film, rather than 
// net.thornydev.mybatis.koan.koan10.FilmK10. There are three differences:
// 1. the "releaseYear" field is of type net.thornydev.mybatis.koan.util.Year
//    rather than a simple String
// 2. the "specialFeatures" field is now a List<String>, rather than a simple
//    String with multiple comma-separated entries
// 3. the rentalRate and replacementCost fields have been converted to
//    BigDecimal types rather than floats.
// 
// Our first use of a TypeHandler is to handle the Year domain specific class.
// It is not a very interesting domain type, but serves as a good illustration of
// how to use a TypeHandler.  Since MyBatis will not know by default how to 
// handle the Year class, you need to help it and register the TypeHandler as
// a general handler of the Year type for all queries/DML statements.
//
// Our second use of a TypeHandler is to specify for a specific query 
// ("getLanguageById") how to handle the text in the special_features column. 
// The special_features column is a comma-separated list of features
// (it is not third normal form compliant). Since we have specified that
// the specialFeatures property of Film is now a List<String>, then we need
// a handler to split the string by commas and removes any extraneous characters
// (the postgresql dataset uses curly braces and quotes, for example).
// However, because the special_features data is a String, we can't register
// the SpecialFeaturesTypeHandler as a general handler for all strings, but
// only for the special_features column in the query we are interested in.
//
// To complete this koan test you will need to edit:
//   1. the TODO entries in the YearTypeHandler interface
//   2. the TODO entries in the FilmMapper interface
//// 1. the mapper xml file to have the right SQL queries and MyBatis XML entries
//// 2. the config xml file to set up type aliases and a proper namespace
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
	public void learnToUseTypeHandlerForDomainSpecificFieldTypesForQueries() {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			LanguageMapper mapper = session.getMapper(LanguageMapper.class);
		
			Language lang = mapper.getLanguageById(1);
			assertNotNull(lang);
			assertEquals(1, lang.getId());
			assertEquals("English", lang.getName().trim());
			assertNotNull(lang.getFilms());
			
			List<Film> lf = lang.getFilms();
			assertEquals(1000, lf.size());
			
			Film f = lf.get(0);
			assertEquals(1000, f.getId());
			assertEquals("ZORRO ARK", f.getTitle());
			assertNotNull(f.getReleaseYear());
			assertEquals( new Year("2006"), f.getReleaseYear() );
			assertEquals( BigDecimal.valueOf(4.99), f.getRentalRate() );
			assertNotNull( f.getSpecialFeatures() );
			assertEquals( 3, f.getSpecialFeatures().size() );
			
			List<String> expectedSpecFeat = new ArrayList<String>();
			expectedSpecFeat.add("Trailers");
			expectedSpecFeat.add("Commentaries");
			expectedSpecFeat.add("Behind the Scenes");
			assertTrue( f.getSpecialFeatures().containsAll(expectedSpecFeat) );
			
		} finally {
			if (session != null) session.close();
		}
	}

	@Test
	public void learnToUseTypeHandlerForDomainSpecificFieldTypesForDMLStatements() {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			FilmMapper mapper = session.getMapper(FilmMapper.class);
			
			Film f = new Film(1000);
			f.setReleaseYear(new Year("2012"));
			f.setReplacementCost(BigDecimal.valueOf(25.95));
			
			int n = mapper.updateYearAndReplacementCost(f);
			assertEquals(1, n);

			// test to make sure it was updated as expected
			f = mapper.getFilmById(1000);
			assertEquals("ZORRO ARK", f.getTitle());
			assertEquals(new Year("2012"), f.getReleaseYear());
			assertEquals(BigDecimal.valueOf(25.95), f.getReplacementCost());
			
		} finally {
			if (session != null) {
				session.rollback();
				session.close();
			}
		}
	}
}
