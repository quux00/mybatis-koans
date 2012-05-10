package net.thornydev.mybatis.koan.koan11;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
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
			// FIXME: would an assertEquals also work here or does check object equivalence?
			assertTrue( new Year("2006").equals(f.getReleaseYear()) );
//			assertTrue( Pattern.
//					compile("Trailers.+Commentaries.+Behind the Scenes", Pattern.CASE_INSENSITIVE).
//					matcher(f.getSpecialFeatures()).
//					find() );
			
		} finally {
			if (session != null) session.close();
		}
	}

}
