package net.thornydev.mybatis.test.koan16.h2;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.thornydev.mybatis.test.util.FilmInStockId;
import net.thornydev.mybatis.test.util.FilmInStockParam;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import util.FlavorH2;

// http://loianegroner.com/2011/03/ibatis-mybatis-working-with-stored-procedures/

/**
 * 
 */
@Category(FlavorH2.class)
public class Koan16 {

	static SqlSession session;
	static SqlSessionFactory sessionFactory;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		final String resource = "net/thornydev/mybatis/test/koan16/h2/koan16-config.xml";

		final InputStream inputStream = Resources.getResourceAsStream(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		inputStream.close();

		session = sessionFactory.openSession();

		/** define stored procedures */

		session.update("defineFilmInStock");

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		if (session != null)
			session.close();
	}

	@Test
	public void learnToUseStoredProcWithHashMaps() {

		final Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("filmId", 12);
		param.put("storeId", 1);

		final List<Map<String, Integer>> results = session.selectList(
				"callFilmInStockWithHashMaps", param);

		// mysql will put count in the hash
		assertEquals(3, param.get("count").intValue());

		assertEquals(3, results.size());
		assertEquals(60, results.get(0).get("inventory_id").intValue());
		assertEquals(61, results.get(1).get("inventory_id").intValue());
		assertEquals(62, results.get(2).get("inventory_id").intValue());

	}

	@Test
	public void learnToUseStoredProcWithDomainObjects() {

		final FilmInStockParam param = new FilmInStockParam();
		param.setFilmId(12);
		param.setStoreId(1);

		final List<FilmInStockId> results = session.selectList(
				"callFilmInStock", param);

		assertEquals(3, param.getCount());
		assertEquals(3, results.size());

		FilmInStockId f = results.get(0);
		assertNotNull(f);
		assertEquals(60, f.getFilmId().intValue());

		f = results.get(1);
		assertNotNull(f);
		assertEquals(61, f.getFilmId().intValue());

		f = results.get(2);
		assertNotNull(f);
		assertEquals(62, f.getFilmId().intValue());

	}

	@Test
	public void learnToUseStoredProcWithDomainObjects2() {

		final FilmInStockParam param = new FilmInStockParam();
		param.setFilmId(12);
		param.setStoreId(1);

		final List<Integer> results = session.selectList("callFilmInStock2",
				param);

		assertEquals(3, param.getCount());

		assertEquals(3, results.size());
		assertEquals(60, results.get(0).intValue());
		assertEquals(61, results.get(1).intValue());
		assertEquals(62, results.get(2).intValue());
	}

	@Test
	public void learnToCallStoredFunction() {
		final Integer g = session.selectOne("inventoryInStore", 9);
		assertNotNull(g);
		assertEquals(0, g.intValue());
	}

	@Test
	public void learnToCallStoredFunction2() {
		final Boolean b = session.selectOne("inventoryInStoreBoolean", 1);
		assertNotNull(b);
		assertTrue(b);
	}
}
