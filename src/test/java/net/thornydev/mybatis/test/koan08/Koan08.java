package net.thornydev.mybatis.test.koan08;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;

import net.thornydev.mybatis.test.domain.Country;

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
// may not work in all databases (I've tested it in mysql and postgresql), but
// it nicely illustrates the "selectKey" feature.  We'll see better alternatives
// in later koans.
//
// To complete this koan test you will need to edit:
// 1. all the TODO entries in this koan
// 2. all the TODO entries in the Koan06Mapper class
// 3. the koan config xml file to tell it where to find the SQL mappings
public class Koan08 {

	static SqlSessionFactory sessionFactory;
	static final Date NOW = new Date();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		final String resource = "net/thornydev/mybatis/test/koan08/koan08-config.xml";
		final InputStream inputStream = Resources.getResourceAsStream(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		inputStream.close();
	}

	@Test
	public void learnToInsertViaXmlMapping() throws Exception {

		SqlSession session = null;

		try {

			session = sessionFactory.openSession();

			final Country c = new Country();
			c.setId(1000);
			c.setCountry("South Sudan"); // new country as of July 2011
			c.setLastUpdate(NOW);

			final int n = session.insert("insertCountry", c);

			assertEquals(1, n);

			final int totalCountries = (Integer) session
					.selectOne("getCountryCount");
			assertEquals(110, totalCountries);

			final Country southSudan = session
					.selectOne("getCountryById", 1000);
			assertNotNull(southSudan);
			assertEquals(1000, southSudan.getId());
			assertEquals("South Sudan", southSudan.getCountry());
			final Timestamp newTs = new Timestamp(southSudan.getLastUpdate()
					.getTime());
			final Timestamp origTs = Timestamp.valueOf("2006-02-15 09:44:00");
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
			final Country c = new Country();
			c.setId(89);
			c.setCountry("North Sudan"); // adjust the name
			c.setLastUpdate(NOW);
			final int n = session.update("updateCountry", c);

			assertEquals(1, n);

			final int totalCountries = (Integer) session
					.selectOne("getCountryCount");
			assertEquals(109, totalCountries);

			final Country northSudan = session.selectOne("getCountryById", 89);
			assertNotNull(northSudan);
			assertEquals("North Sudan", northSudan.getCountry());
			final Timestamp newTs = new Timestamp(northSudan.getLastUpdate()
					.getTime());
			final Timestamp origTs = Timestamp.valueOf("2006-02-15 09:44:00");
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
			final Country c1000 = new Country();
			c1000.setId(1000);
			c1000.setCountry("The Shire");
			c1000.setLastUpdate(NOW);
			int n = session.insert("insertCountry", c1000);

			final Country c1001 = new Country();
			c1001.setId(1001);
			c1001.setCountry("Mordor");
			c1001.setLastUpdate(NOW);
			n = session.insert("insertCountry", c1001);
			assertEquals(1, n);

			int totalCountries = (Integer) session.selectOne("getCountryCount");
			assertEquals(111, totalCountries);

			// second delete it via mapper class
			final Koan08Mapper mapper = session.getMapper(Koan08Mapper.class);
			n = mapper.deleteCountryById(c1000.getId());
			assertEquals(1, n);
			totalCountries = (Integer) session.selectOne("getCountryCount");
			assertEquals(110, totalCountries);

			n = mapper.deleteCountry(c1001);
			assertEquals(1, n);
			totalCountries = (Integer) session.selectOne("getCountryCount");
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
	// when you create it. Rather let MyBatis fill it in according to the
	// logic in the <selectKey> section.
	@Test
	public void learnToInsertUsingKeyProperty() throws Exception {
		SqlSession session = null;

		try {
			session = sessionFactory.openSession();
			// first get max country id and cache it for assert testing later
			final int maxId = (Integer) session.selectOne("getMaxCountryId");

			final Country c = new Country();
			c.setCountry("South Sudan");
			c.setLastUpdate(NOW);
			final int n = session.insert("insertCountry2", c);

			assertEquals(1, n);
			assertEquals(maxId + 1, c.getId()); // id should have been filled in
												// and set to new maxId

			final int totalCountries = (Integer) session
					.selectOne("getCountryCount");
			assertEquals(110, totalCountries);

			final Country southSudan = session.selectOne("getCountryById",
					maxId + 1);
			assertNotNull(southSudan);
			assertEquals(maxId + 1, southSudan.getId());
			assertEquals("South Sudan", southSudan.getCountry());
			final Timestamp newTs = new Timestamp(southSudan.getLastUpdate()
					.getTime());
			final Timestamp origTs = Timestamp.valueOf("2006-02-15 09:44:00");
			assertTrue(newTs.after(origTs));

		} finally {
			if (session != null) {
				session.rollback();
				session.close();
			}
		}
	}
}
