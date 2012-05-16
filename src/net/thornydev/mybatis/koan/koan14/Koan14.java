package net.thornydev.mybatis.koan.koan14;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Date;

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

public class Koan14 {

	static SqlSession session;
	static SqlSessionFactory sessionFactory;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String resource = "net/thornydev/mybatis/koan/koan14/koan14-config.xml";
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
	public void learnToInsertNullValues() {
		int cntBefore = session.selectOne("getCityCount");

		Country c = session.selectOne("getCountryById", 22);
		
		City city = new City(1000, "FooCity", new Date());
		city.setCountry(c);
		
		session.insert("insertCity", city);
		int cntAfter = session.selectOne("getCityCount");
		
		assertEquals(cntAfter, cntBefore + 1);
		session.rollback();
	}

}
