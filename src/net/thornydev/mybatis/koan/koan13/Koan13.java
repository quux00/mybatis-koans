package net.thornydev.mybatis.koan.koan13;

import static org.junit.Assert.*;

import java.io.InputStream;

import net.thornydev.mybatis.koan.domain.Actor;
import net.thornydev.mybatis.koan.domain.Address;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
	public void learnToUseObjectFactory() {
		AddressMapper mapper = session.getMapper(AddressMapper.class);
		Address addr = mapper.getAddressById(100);
		assertEquals(100, addr.getId().intValue());
		assertEquals("1308 Arecibo Way", addr.getAddress());
		assertEquals("30695", addr.getPostalCode());
		assertNotNull(addr.getLastUpdate());
		assertNotNull(addr.getCity());
		assertNotNull(addr.getCity().getCountry());
	}
	
	@Test
	public void learnToUseObjectFactoryAndResultHander() {
		ActorResultHandler rh = new ActorResultHandler();
		session.select("getActorById", 200, rh);
		Actor actor = rh.getActor();
		System.out.println(actor.toString());
		assertEquals(200, actor.getId().intValue());
		assertEquals("THORA", actor.getFirstName());
		assertEquals("TEMPLE", actor.getLastName());
	}	
	

}
