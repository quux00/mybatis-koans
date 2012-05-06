package net.thornydev.mybatis.koan.koan05;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;

import net.thornydev.mybatis.koan.Country;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

public class Koan05 {

	static SqlSessionFactory sessionFactory;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String resource = "net/thornydev/mybatis/koan/koan05/koan05-config.xml";  
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		inputStream.close();
	}

	@Test
	public void learnToQueryForTotalCount() throws Exception {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			Koan05Mapper mapper = session.getMapper(Koan05Mapper.class);
			int totalCountries = mapper.getCountryCount();
			
			assertEquals(109, totalCountries);
			
		} finally {
			if (session != null) session.close();
		}
	}

	@Test
	public void learnToQueryWithMultipleParams() throws Exception {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			Koan05Mapper mapper = session.getMapper(Koan05Mapper.class);
			List<Country> lc = mapper.getCountryRange(22, 33);
			
			assertEquals(12, lc.size());
			Country finland = lc.get(11);
			assertEquals("Finland", finland.getCountry());
			
		} finally {
			if (session != null) session.close();
		}
	}	
	
	@Test
	public void learnToQueryWithMultipleParamsWithAnnotatedNames() throws Exception {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			Koan05Mapper mapper = session.getMapper(Koan05Mapper.class);
			List<Country> lc = mapper.getCountryRange2(22, 33);
			
			assertEquals(12, lc.size());
			Country finland = lc.get(11);
			assertEquals("Finland", finland.getCountry());
			
		} finally {
			if (session != null) session.close();
		}
	}	
	
	@Test
	public void learnToQueryWithRowBounds() throws Exception {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();

			int offset = 21;
			int limit = 12;
			RowBounds rb = new RowBounds(offset, limit);
			List<Country> lc = session.selectList("getCountries", null, rb);

			assertEquals(12, lc.size());
			Country finland = lc.get(11);
			assertEquals("Finland", finland.getCountry());
			
		} finally {
			if (session != null) session.close();
		}
	}		
	
	@Test
	public void learnToQueryMapperClassWithRowBounds() throws Exception {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();

			int offset = 21;
			int limit = 12;
			RowBounds rb = new RowBounds(offset, limit);
			Koan05Mapper mapper = session.getMapper(Koan05Mapper.class);
			List<Country> lc = mapper.getCountries(rb);

			assertEquals(12, lc.size());
			Country finland = lc.get(11);
			assertEquals("Finland", finland.getCountry());
			
		} finally {
			if (session != null) session.close();
		}
	}		
}
