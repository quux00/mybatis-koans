package net.thornydev.mybatis.koan.koan16;

import static org.junit.Assert.*;

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

// MyBatis has the ability to map to both stored procedures and 
// stored functions.  The sakila database includes both, although the 
// implementation of these will differ by database.
// 
// For your chosen database, spend some time looking at how the
// * film_in_stock stored procedure is implemented
// * inventory_in_stock stored function is implemented
// 
// For example, in the PostgreSQL sakila database I'm using, film_in_stock
// is implemented as a stored function.
// 
// This solution to this koan is database specific.  In the completed koans
// section I have provided working solutions for the MySQL database (v5.5)
// and PostgreSQL database (v9.1), but you should only refer to these if you
// get stuck.  
// 
// As of this writing, the official MyBatis documentation is a little light
// on how to invoke stored procs and functions, so I recommend doing some 
// googling and in particular to read this blog entry:
// http://loianegroner.com/2011/03/ibatis-mybatis-working-with-stored-procedures/
// 
// In addition to doing one stored proc and one stored function, the tests
// in this koan require you to solve the problem in a couple of different ways -
// with hashmaps vs. domain objects as params (including OUT params in cases where
// the database supports that).  I also vary how to capture the return values.
// 
// In order to complete this koan, you will need to:
// 1. Edit the TODO entries in this Koan16 Test
// 2. Edit the TODO entries in the koan16-mapper.xml file
public class Koan16 {

	static SqlSession session;
	static SqlSessionFactory sessionFactory;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String resource = "net/thornydev/mybatis/koan/koan16/koan16-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		inputStream.close();

		session = sessionFactory.openSession();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		if (session != null) session.close();
	}

	@Test
	public void learnToUseStoredProcWithHashMaps() {
		// TODO: replace the '?' generic wildcards with actual types
		Map<?,?> param = new HashMap();  // TODO: create a HashMap with the appropriate generic type
		// TODO: populate the hashmap with appropriate entries for the stored proc query
		List<Map<?,?>> results = session.selectList("callFilmInStockWithHashMaps", param);

		// TODO: remove this line if your database does not use OUT params
		//       in which case MyBatis won't populate the count entry in this hash
		assertEquals(Integer.valueOf(3), param.get("count"));
		
		assertEquals(3, results.size());
		// TODO: fill in the lookup key for the values returned by the stored proc
		//       and put in the hashmap by MyBatis
		assertEquals(Integer.valueOf(60), results.get(0).get("???"));
		assertEquals(Integer.valueOf(61), results.get(1).get("???"));
		assertEquals(Integer.valueOf(62), results.get(2).get("???"));
	}

	
	@Test
	public void learnToUseStoredProcWithDomainObjects() {
		FilmInStockParam param = new FilmInStockParam();
		param.setFilmId(12);
		param.setStoreId(1);
		List<FilmInStockId> results = session.selectList("callFilmInStock", param);

		// TODO: remove this line if your database does not use OUT params
		//       in which case MyBatis won't populate the count entry in this hash
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
		FilmInStockParam param = new FilmInStockParam();
		param.setFilmId(12);
		param.setStoreId(1);
		List<Integer> results = session.selectList("callFilmInStock2", param);
		
		// TODO: remove this line if your database does not use OUT params
		//       in which case MyBatis won't populate the count entry in this hash
		assertEquals(3, param.getCount());
		
		assertEquals(3, results.size());
		assertEquals(60, results.get(0).intValue());
		assertEquals(61, results.get(1).intValue());
		assertEquals(62, results.get(2).intValue());
	}
	
	@Test
	public void learnToCallStoredFunction() {
		// TODO: choose one of these to map to and delete the other
		// Store 9 has no inventory, so the answer is "false" by some representation
		Integer g = session.selectOne("inventoryInStore", 9);
		assertNotNull(g);
		assertEquals(0, g.intValue());

		String s = session.selectOne("inventoryInStore", 9);
		assertNotNull(s);
		assertEquals("f", s);
	}

	@Test
	public void learnToCallStoredFunction2() {
		// Store 1 does have inventory, so the answer is true
		Boolean b = session.selectOne("inventoryInStoreBoolean", 1);
		assertNotNull(b);
		assertTrue(b);
	}
}
