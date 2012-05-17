package net.thornydev.mybatis.koan.koan08;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;

import net.thornydev.mybatis.koan.domain.Country;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

// In Koan08, we move beyond the select statement and learn how to do
// updates, inserts and deletes, both via straight xml mapping and through
// annotations on Java mapper interfaces.
// 
// In passing, we will see use of the session.rollback() method.  You can
// also try doing a session.commit() and verify manually that it gets committed
// to the db, but remember to delete those insertions before proceeding
// with the koans.  You could also try not calling either rollback or commit
// and see what happens ...
// 
// In the first tests, we do an insert by just hardcoding an ID we know is not
// being used.  In a later test, we add a little more intelligence by using
// the "selectKey" feature to dynamically specify what key (id) should be used
// when inserting a new country record.  The query I use is not best practice
// and may not work in all databases (I've tested it in mysql and postgresql),
// but it nicely illustrates the "selectKey" feature.  We'll see better 
// alternatives in later koans.
//
// To complete this koan test you will need to edit:
// 1. all the TODO entries in this koan
// 2. all the TODO entries in the Koan08Mapper class
// 3. all the TODO entries in the the koan mapper xml file 
// 4. the mapper element in koan08-config.xml
public class Koan08 {

	static SqlSessionFactory sessionFactory;
	static final Date NOW = new Date();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String resource = "net/thornydev/mybatis/koan/koan08/koan08-config.xml";
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
			c.setId(1000);                // note that we explicitly set id here (for now)
			c.setCountry("South Sudan");  // new country as of July 2011
			c.setLastUpdate(NOW);
			
			// TODO: call the "insertCountry" mapping - either directly via the
			//       session or add an interface method to Koan08Mapper, whichever
			//       you prefer.  Either way, get the return value of number of rows
			//       inserted and check that it is 1.
			int n = -1;
			assertEquals(1, n);
			
			// validate that the insert worked
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
				// TODO: try it without calling rollback() and see what happens
				// TODO: try it with calling commit() and see what happens
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
			
			// TODO: call the "updateCountry" mapping - either directly via the
			//       session or add an interface method to Koan08Mapper, whichever
			//       you prefer.  Either way, get the return value of number of rows
			//       updated and check that it is 1.
			int n = -1;
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
			Koan08Mapper mapper = session.getMapper(Koan08Mapper.class);
			
			n = mapper.deleteCountryById( c1000.getId() );
			assertEquals(1, n);
			totalCountries = session.selectOne("getCountryCount");			
			assertEquals(110, totalCountries);
			
			// TODO: delete record with id 1001 by using the whole Country
			//       object rather than just passing the id
			n = -1;
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

	// In this test we use the <selectKey> feature of MyBatis xml mapping
	// in order to dynamically set the next country_id primary key to use. 
	// In this case, do NOT fill in the id field of the Country class
	// when you create it.  Rather let MyBatis fill it in according to the
	// logic in the <selectKey> section.
	@Test
	public void learnToInsertUsingKeyProperty() throws Exception {
		SqlSession session = null;
		
		try {
			session = sessionFactory.openSession();
			// first get max country id and cache it for assert testing later
			int maxId = session.selectOne("getMaxCountryId");
			
			// TODO: create the Country "South Sudan", but leave the id blank
			Country c = null;
			
			int n = session.insert("insertCountry2", c);			
			assertEquals(1, n);
			assertEquals(maxId + 1, c.getId());  // id should have been filled in and set to new maxId
			
			int totalCountries = session.selectOne("getCountryCount");
			assertEquals(110, totalCountries);
			
			Country southSudan = session.selectOne("getCountryById", maxId + 1);
			assertNotNull(southSudan);
			assertEquals(maxId + 1, southSudan.getId());
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
}
