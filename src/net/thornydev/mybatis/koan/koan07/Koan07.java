package net.thornydev.mybatis.koan.koan07;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import net.thornydev.mybatis.koan.Country;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

public class Koan07 {

	static SqlSessionFactory sessionFactory;
	static final Date NOW = new Date();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String resource = "net/thornydev/mybatis/koan/koan07/koan07-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		inputStream.close();
	}

	@Test
	public void learnToInsertViaXmlMapping() throws Exception {
		SqlSession session = null;
		
		try {
			session = sessionFactory.openSession();
			Country c = new Country();
			c.setId(1000);
			c.setCountry("South Sudan");  // new country as of July 2011
			c.setLastUpdate(NOW);
			int n = session.insert("insertCountry", c);
			
			assertEquals(1, n);
			
			int totalCountries = session.selectOne("getCountryCount");
			assertEquals(110, totalCountries);
			
			Country southSudan = session.selectOne("getCountryById", 1000);
			assertNotNull(southSudan);
			assertEquals(1000, southSudan.getId());
			assertEquals("South Sudan", southSudan.getCountry());
			Timestamp newTs = new Timestamp(southSudan.getLastUpdate().getTime());
			Timestamp origTs = Timestamp.valueOf("2006-02-15 09:44:00");
			assertTrue(newTs.after(origTs));
			
		} finally {
			if (session != null) {
				session.rollback();
				session.close();
			}
		}
	}

	@Test
	public void learnToUpdateViaXmlMapping() throws Exception {
		SqlSession session = null;
		
		try {
			session = sessionFactory.openSession();
			Country c = new Country();
			c.setId(89);
			c.setCountry("North Sudan");  // adjust the name
			c.setLastUpdate(NOW);
			int n = session.update("updateCountry", c);
			
			assertEquals(1, n);
			
			int totalCountries = session.selectOne("getCountryCount");
			assertEquals(109, totalCountries);
			
			Country northSudan = session.selectOne("getCountryById", 89);
			assertNotNull(northSudan);
			assertEquals("North Sudan", northSudan.getCountry());
			Timestamp newTs = new Timestamp(northSudan.getLastUpdate().getTime());
			Timestamp origTs = Timestamp.valueOf("2006-02-15 09:44:00");
			assertTrue(newTs.after(origTs));
			
		} finally {
			if (session != null) {
				session.rollback();
				session.close();
			}
		}
	}
	
	@Test
	public void learnToDeleteViaAnnotationMapping() throws Exception {
		SqlSession session = null;
		
		try {
			// first insert a new record via xml mapped SQL
			session = sessionFactory.openSession();
			Country c1000 = new Country();
			c1000.setId(1000);
			c1000.setCountry("The Shire");
			c1000.setLastUpdate(NOW);
			int n = session.insert("insertCountry", c1000);
			
			Country c1001 = new Country();
			c1001.setId(1001);
			c1001.setCountry("Mordor");
			c1001.setLastUpdate(NOW);
			n = session.insert("insertCountry", c1001);
			assertEquals(1, n);
			
			int totalCountries = session.selectOne("getCountryCount");			
			assertEquals(111, totalCountries);
			
			// second delete it via mapper class
			Koan07Mapper mapper = session.getMapper(Koan07Mapper.class);
			n = mapper.deleteCountryById( c1000.getId() );
			assertEquals(1, n);
			totalCountries = session.selectOne("getCountryCount");			
			assertEquals(110, totalCountries);
			
			n = mapper.deleteCountry(c1001);
			assertEquals(1, n);
			totalCountries = session.selectOne("getCountryCount");			
			assertEquals(109, totalCountries);
			
		} finally {
			if (session != null) {
				session.rollback();
				session.close();
			}
		}
	}	

	// FIXME: Need additional documentation here ...
	@Test
	public void learnToInsertUsingKeyProperty() throws Exception {
		SqlSession session = null;
		
		try {
			session = sessionFactory.openSession();
			Country c = new Country();
			c.setCountry("South Sudan");
			c.setLastUpdate(NOW);
			int n = session.insert("insertCountry2", c);
			
			assertEquals(1, n);
			assertEquals(110, c.getId());  // id should have been filled in
			
			int totalCountries = session.selectOne("getCountryCount");
			assertEquals(110, totalCountries);
			
			Country southSudan = session.selectOne("getCountryById", 110);
			assertNotNull(southSudan);
			assertEquals(110, southSudan.getId());
			assertEquals("South Sudan", southSudan.getCountry());
			Timestamp newTs = new Timestamp(southSudan.getLastUpdate().getTime());
			Timestamp origTs = Timestamp.valueOf("2006-02-15 09:44:00");
			assertTrue(newTs.after(origTs));
			
		} finally {
			if (session != null) {
				session.rollback();
				session.close();
			}
		}
	}
	
	
	@Test
	public void learnToUseDynamicStringSubstitutionVariables() throws Exception {
		SqlSession session = null;
		
		try {
			session = sessionFactory.openSession();
			List<Country> lc = session.selectList("getCountries", "ORDER BY country DESC");
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
}
