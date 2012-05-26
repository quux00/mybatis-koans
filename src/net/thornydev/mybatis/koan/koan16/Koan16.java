package net.thornydev.mybatis.koan.koan16;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

// http://loianegroner.com/2011/03/ibatis-mybatis-working-with-stored-procedures/
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
	public void learnToCallStoredProcWithHashMaps() {
		Map<String,Integer> param = new HashMap<String,Integer>();
		param.put("filmId", 12);
		param.put("storeId", 1);
		List<Map<String,Integer>> results = session.selectList("callFilmInStockWithHashMaps_pg", param);

		assertEquals(3, results.size());
		assertEquals(60, results.get(0).get("film_in_stock").intValue());
		assertEquals(61, results.get(1).get("film_in_stock").intValue());
		assertEquals(62, results.get(2).get("film_in_stock").intValue());
	}

	@Test
	public void learnToCallStoredProcWithHashMaps2() {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("filmId", 12);
		param.put("storeId", 1);
		List<Map<?,?>> results = session.selectList("callFilmInStockWithHashMaps2_pg", param);
		System.out.println("2222 -------------------------- 22222");
		System.out.println("<>>>>param count: "+param.get("count"));
		System.out.println( param.toString() );
		System.out.println("sz result map: " + results.size());
		System.out.println(results.toString());
	}

	
	@Test
	public void learnToCallStoredProcWithParam() {
		FilmInStockParam param = new FilmInStockParam();
		param.setFilmId(12);
		param.setStoreId(1);
		List<Map<String,Integer>> results = session.selectList("callFilmInStock_pg", param);

		assertEquals(3, results.size());
		assertEquals(60, results.get(0).get("film_in_stock").intValue());
		assertEquals(61, results.get(1).get("film_in_stock").intValue());
		assertEquals(62, results.get(2).get("film_in_stock").intValue());
	}
}
