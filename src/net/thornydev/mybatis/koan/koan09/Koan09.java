package net.thornydev.mybatis.koan.koan09;

import static org.junit.Assert.*;

import java.io.InputStream;

import net.thornydev.mybatis.koan.domain.City;
import net.thornydev.mybatis.koan.domain.Country;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

// Koan09 finally gets us to more than one table.  We learn about how to
// use MyBatis ResultMaps to map relational joins to our domain objects. 
// 
// But before moving to ResultMaps mapping multiple tables to domain objects,
// we will start with the good old country table to Country object mapping
// but this time use an explicit MyBatis ResultMap, rather than the implicit
// one we've been using thus far.
// 
// FIXME: more here ...
public class Koan09 {

	static SqlSessionFactory sessionFactory;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String resource = "net/thornydev/mybatis/koan/koan09/koan09-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		inputStream.close();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void learnToUseResultMapForSingleTableQuery() {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			Koan09Mapper mapper = session.getMapper(Koan09Mapper.class);
			Country c = mapper.getCountryById(33);
			
			assertEquals(33, c.getId());
			assertEquals("Finland", c.getCountry());
			
		} finally {
			if (session != null) session.close();
		}
	}

	@Test
	public void learnToUseResultMapForTwoTableAssociation() {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			Koan09Mapper mapper = session.getMapper(Koan09Mapper.class);
			City city = mapper.getCityById(544);
			
			assertEquals(544, city.getId());
			assertEquals("Toulouse", city.getCity());
			assertNotNull(city.getLastUpdate());
			
			Country co = city.getCountry();
			assertNotNull(co);
			assertEquals("France", co.getCountry());
			assertNotNull(co.getLastUpdate());
			
		} finally {
			if (session != null) session.close();
		}
	}

	@Test
	public void learnToUseNestedSelectForAssociation() {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			Koan09Mapper mapper = session.getMapper(Koan09Mapper.class);
			City city = mapper.getCityByIdLazy(544);
			
			assertEquals(544, city.getId());
			assertEquals("Toulouse", city.getCity());
			assertNotNull(city.getLastUpdate());
			
			Country co = city.getCountry();
			assertNotNull(co);
			assertEquals("France", co.getCountry());
			assertNotNull(co.getLastUpdate());
			
		} finally {
			if (session != null) session.close();
		}
	}

}
