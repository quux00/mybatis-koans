package net.thornydev.mybatis.test.koan22;

import static org.junit.Assert.*;

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

public class Koan22 {

	static SqlSessionFactory sessionFactory;
	SqlSession session;
	Koan22Mapper mapper;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		final String resource = "net/thornydev/mybatis/test/koan22/koan22-config.xml";
		final InputStream inputStream = Resources.getResourceAsStream(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		inputStream.close();
	}

	@Before
	public void setUp() throws Exception {
		session = sessionFactory.openSession();
		mapper = session.getMapper(Koan22Mapper.class);
	}

	@After
	public void tearDown() throws Exception {
		if (session != null)
			session.close();
	}

	@Test
	public void learnToInsertFromMultipleObjectsAsParams() {

		final Category familyCat = mapper.getCategoryByName("Family");
		final Category comedyCat = mapper.getCategoryByName("Comedy");

		final FilmWithCategories fwc = mapper.getFilmById(25);
		assertNotNull(fwc.getCategories().get(0));
		assertEquals("New", fwc.getCategories().get(0).getName());
		final Category newCat = fwc.getCategories().get(0);
		fwc.addCategory(familyCat);
		fwc.addCategory(comedyCat);

		int n = mapper.deleteAllCategoriesForFilm(fwc);
		assertEquals(1, n);

		for (final Category c : fwc.getCategories()) {
			n = mapper.addCategoryForFilm(fwc, c);
			assertEquals(1, n);
		}

		final FilmWithCategories fwc25 = mapper.getFilmById(25);
		assertNotNull(fwc25);
		assertEquals("ANGELS LIFE", fwc.getTitle());
		final List<Category> lc = fwc.getCategories();
		assertEquals(3, lc.size());
		assertNotNull(lc.get(0));
		assertEquals(1, Collections.frequency(lc, comedyCat));
		assertEquals(1, Collections.frequency(lc, familyCat));
		assertEquals(1, Collections.frequency(lc, newCat));
	}

}
