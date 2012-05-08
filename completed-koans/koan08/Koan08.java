package net.thornydev.mybatis.koan.koan08;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;

import net.thornydev.mybatis.koan.domain.Country;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

public class Koan08 {

	static SqlSessionFactory sessionFactory;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String resource = "net/thornydev/mybatis/koan/koan08/koan08-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		inputStream.close();
	}


	@Test
	public void learnToSpecifyOrderViaDynamicStringSubstitutionVariableInXml() throws Exception {
		SqlSession session = null;
		
		try {
			session = sessionFactory.openSession();
			List<Country> lc = session.selectList("getCountriesOrdered", "country DESC");
			assertEquals(109, lc.size());
			Country c = lc.get(0);
			assertEquals("Zambia", c.getCountry());
			
		} finally {
			if (session != null) {
				session.rollback();
				session.close();
			}
		}
	}

	@Test
	public void learnToSpecifyOrderViaDynamicStringSubstitutionVariableInMapperClass() throws Exception {
		SqlSession session = null;
		
		try {
			session = sessionFactory.openSession();
			Koan08Mapper mapper = session.getMapper(Koan08Mapper.class);
			List<Country> lc = mapper.getCountriesOrdered2("country DESC");

			assertEquals(109, lc.size());
			Country c = lc.get(0);
			assertEquals("Zambia", c.getCountry());
			
			lc = session.selectList("getCountries", null);
			assertEquals(109, lc.size());
			c = lc.get(0);
			assertEquals("Afghanistan", c.getCountry());
			
		} finally {
			if (session != null) {
				session.rollback();
				session.close();
			}
		}
	}	
	
	@Test
	public void learnToSpecifyDynamicClausesInXml() throws Exception {
		SqlSession session = null;
		
		try {
			session = sessionFactory.openSession();
			// use an order by clause
			List<Country> lc = session.selectList("getCountries", "ORDER BY country DESC");
			assertEquals(109, lc.size());
			Country c = lc.get(0);
			assertEquals("Zambia", c.getCountry());
			
			// use no clause at all
			lc = session.selectList("getCountries", null);
			assertEquals(109, lc.size());
			c = lc.get(0);
			assertEquals("Afghanistan", c.getCountry());
			
			// specify a range via an SQL clause
			lc = session.selectList("getCountries", "WHERE country_id BETWEEN 22 and 33");
			assertEquals(12, lc.size());
			c = lc.get(11);
			assertEquals(33, c.getId());			
			assertEquals("Finland", c.getCountry());			
			
		} finally {
			if (session != null) {
				session.rollback();
				session.close();
			}
		}
	}
}
