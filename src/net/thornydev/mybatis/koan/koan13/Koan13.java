package net.thornydev.mybatis.koan.koan13;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;

import net.thornydev.mybatis.koan.domain.Actor;
import net.thornydev.mybatis.koan.domain.Address;
import net.thornydev.mybatis.koan.domain.City;
import net.thornydev.mybatis.koan.util.ObjectFactoryCheck;
import net.thornydev.mybatis.koan.util.Range;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

// In Koan12, we dealt with immutable objects by building long constructor
// lists, though we also learned that MyBatis can work around the final
// keyword, so it is not strictly necessary to pass in all fields to the
// constructor when using MyBatis.
// 
// In Koan13, we continue our investigation of ways to handle immutable
// objects with MyBatis.  We show two additional patterns for using immutable
// objects:
// 1. passing in a Map of params to the constructor (which is
//    typical in dynamic languages, though not that common in Java and other
//    statically typed languages).
// 2. using the builder idiom to set params that will construct an underlying
//    immutable object.  (If you are interested in more background on the
//    builder "idiom" as I call it, see my blog post on the topic:
//    http://thornydev.blogspot.com/2012/02/factories-and-builders-idioms-and.html)
// 
// MyBatis does not know how to use these more exotic techniques out of the box
// though, so we need to be able to intercept how it creates our domain objects
// and do it ourselves.  MyBatis provides the ObjectFactory interface for just
// this purpose (and a concrete default implementation class called 
// DefaultObjectFactory, which we will leverage).
// 
// In implementing and configuring the ObjectFactory, we will learn two 
// additional important things:
// 1. there can only be one ObjectFactory registered in your config file
// 2. the order of declaration of XML elements in the MyBatis config file
//    is significant (you will get an error if they are out of order)
// 
// We start by using the City class. While the City class does not have 
// immutable fields, it is included here in order to demonstrate a simple use
// of the ObjectFactory - basically creating a City class with a standard
// constructor.
// 
// Next we use the constructor of the Actor class that takes a 
// Map<String,Object> in order to set its immutable fields.
// 
// Third, we use the Builder inner class of the Address class to create
// an Address class in the Koan13ObjectFactory.
// 
// In showing so many ways to use MyBatis, the domain classes have gotten
// rather polluted (e.g., how many different ways are there to create a
// Address class now?).  This design is definitely not meant to show best
// practice recommendation, but rather to show various aspects of working
// with MyBatis. You should then pick the style that works best for your
// project and stick to that.
// 
// Lastly, we learn how to use the MyBatis ResultHandler class. The 
// ResultHandler can receive the output of the ObjectFactory and do
// something additional with it, such as add it to lists, filter out
// the ones we don't want or whatever. In our koan, we use it to filter
// out any Actors whose last name is 4 characters long.
//
// In order to complete this koan, you will need to:
// 1. Edit the TODO entries in this Koan13 Test
// 2. Edit the TODO entries in the Koan13ObjectFactory class
// 3. Edit the TODO entries in the actor-mapper.xml file
// 4. Edit the TODO entries in the MyBatis config xml file 
// 5. Implement a ResultHandler - see TODO in ActorResultHandler
public class Koan13 {

	static SqlSession session;
	static SqlSessionFactory sessionFactory;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String resource = "net/thornydev/mybatis/koan/koan13/koan13-config.xml";
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
	public void learnToUseObjectFactory_City() {
		City c = session.selectOne("getCityById", 188);
		assertNotNull(c);
		assertEquals(188, c.getId());
		assertEquals("Guadalajara", c.getCity());
		assertNotNull(c.getLastUpdate());
		assertNotNull(c.getCountry());
		
		checkObjectFactoryWasUsed();
	}
	
	@Test
	public void learnToUseObjectFactoryAndResultHandler_Actor() {
		ActorResultHandler rh = new ActorResultHandler();
		// TODO: the cast to ResultHandler will be unnecessary once you properly
		//       implement ActorResultHandler
		session.select("getActorByRange", new Range(36, 42), (ResultHandler) rh);
		List<Actor> la = rh.getActors();
		assertNotNull(la);
		assertEquals(6, la.size());
		
		Actor first = la.get(0);
		assertEquals(36, first.getId().intValue());
		assertEquals("BURT", first.getFirstName());
		assertEquals("DUKAKIS", first.getLastName());
	
		Actor fifth = la.get(4);
		assertEquals(41, fifth.getId().intValue());
		assertEquals("JODIE", fifth.getFirstName());
		assertEquals("DEGENERES", fifth.getLastName());
	
		checkObjectFactoryWasUsed();
	}
	
	@Test
	public void learnToUseObjectFactory_Address() {
		AddressMapper mapper = session.getMapper(AddressMapper.class);
		Address addr = mapper.getAddressById(100);
		assertNotNull(addr);
		assertEquals(100, addr.getId().intValue());
		assertEquals("1308 Arecibo Way", addr.getAddress());
		assertEquals("30695", addr.getPostalCode());
		assertNotNull(addr.getLastUpdate());
		assertNotNull(addr.getCity());
		assertNotNull(addr.getCity().getCountry());
		
		checkObjectFactoryWasUsed();
	}	

	/* ---[ HELPER METHODS ]--- */

	private void checkObjectFactoryWasUsed() {
		assertTrue(ObjectFactoryCheck.getInstance().getObjectFactoryUsed());
		// reset for next test
		ObjectFactoryCheck.getInstance().setObjectFactoryUsed(false);
	}
}
