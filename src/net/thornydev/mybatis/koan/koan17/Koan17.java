package net.thornydev.mybatis.koan.koan17;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.thornydev.mybatis.koan.util.FilmInStockId;
import net.thornydev.mybatis.koan.util.FilmInStockParam;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

// In Koan17 we take on the same stored proc and stored function mappings
// that we did in Koan16, except that this time we try to do them entirely
// or mostly with MyBatis Java annotations.
// 
// In addition to referencing Loiane Groner's blog entry from last time
// (http://loianegroner.com/2011/03/ibatis-mybatis-working-with-stored-procedures)
// I also found this entry useful:
// http://code.google.com/p/mybatis/issues/detail?id=1
//
// As with koan16, my solutions for MySQL and PostgreSQL are to be found in
// completed-koans/koan17/mysql and completed-koans/koan17/pg.
// I was successful in getting all the tests to pass for PostgreSQL,
// but I could not get the all hashmaps version to work for MySQL.
// If you figure it out, let me know or contribute back to these koans.
//
// The tests below are exactly the same as koan16, except I removed one
// of the inventory_in_stock tests to simplify it a bit and reduce
// redundant busy work.
// 
// In order to complete this koan, you will need to:
// 1. Edit the TODO entries in this Koan17 Test
// 2. Edit the TODO entries in the koan17-mapper.xml file
// 3. Edit the TODO entries in the Koan17Mapper class
public class Koan17 {

	static SqlSession session;
	static SqlSessionFactory sessionFactory;
	static Koan17Mapper mapper;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String resource = "net/thornydev/mybatis/koan/koan17/koan17-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		inputStream.close();

		session = sessionFactory.openSession();
		mapper = session.getMapper(Koan17Mapper.class);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		if (session != null) session.close();
	}

	@Test
	public void learnToUseStoredProcWithHashMaps() {
		Map<String,Integer> param = new HashMap<String,Integer>();
		param.put("filmId", 12);
		param.put("storeId", 1);
		List<Map<String,Integer>> results = mapper.callFilmInStockWithHashMaps(param);

		// TODO: remove next assert if your database doesn't populate the OUT param
		assertEquals(3, param.get("count").intValue());

		assertEquals(3, results.size());
		assertEquals(Integer.valueOf(60), results.get(0).get("film_in_stock"));
		assertEquals(Integer.valueOf(61), results.get(1).get("film_in_stock"));
		assertEquals(Integer.valueOf(62), results.get(2).get("film_in_stock"));
	}

	@Test
	public void learnToUseStoredProcWithDomainObjects() {
		FilmInStockParam param = new FilmInStockParam();
		param.setFilmId(12);
		param.setStoreId(1);
		List<FilmInStockId> results = mapper.callFilmInStock(param);

		// TODO: remove next assert if your database doesn't populate the OUT param
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
		FilmInStockParam param = new FilmInStockParam();
		param.setFilmId(12);
		param.setStoreId(1);
		List<Integer> results = mapper.callFilmInStock2(param);

		// TODO: remove next assert if your database doesn't populate the OUT param
		assertEquals(3, param.getCount());

		assertEquals(3, results.size());

		assertEquals(60, results.get(0).intValue());
		assertEquals(61, results.get(1).intValue());
		assertEquals(62, results.get(2).intValue());
	}

	@Test
	public void learnToCallStoredFunction() {
		Boolean b = mapper.inventoryInStore(1);
		assertNotNull(b);
		assertTrue(b);
	}	
}
