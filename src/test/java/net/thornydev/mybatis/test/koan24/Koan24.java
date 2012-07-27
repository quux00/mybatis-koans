package net.thornydev.mybatis.test.koan24;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.UUID;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Koan24 {

	static final Logger log = LoggerFactory.getLogger(Koan24.class);

	static SqlSessionFactory sessionFactory;
	SqlSession session;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		final String resource = "net/thornydev/mybatis/test/koan24/koan24-config.xml";

		final InputStream inputStream = Resources.getResourceAsStream(resource);

		sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

		inputStream.close();

	}

	@Before
	public void setUp() throws Exception {
		session = sessionFactory.openSession();
	}

	@After
	public void tearDown() throws Exception {
		if (session != null) {
			session.close();
		}
	}

	@Test
	public void learnToUseAutoInsert() throws Exception {

		final Koan24Mapper mapper = session.getMapper(Koan24Mapper.class);

		mapper.createTable();

		final int countOne = mapper.getRecordCount();

		final String sourceId = UUID.randomUUID().toString();

		final InstInfo info = new InstInfo();
		info.setSourceId(sourceId);

		assertNull(info.getTargetId());

		mapper.insertRecord(info);

		assertNotNull(info.getTargetId());

		final int countTwo = mapper.getRecordCount();

		assertEquals(countOne + 1, countTwo);

		session.commit();

		log.debug("generated targetId : {}", info.getTargetId());

	}

}
