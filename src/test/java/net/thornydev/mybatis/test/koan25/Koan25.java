package net.thornydev.mybatis.test.koan25;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class Koan25 {

  static SqlSession session;
  static SqlSessionFactory sessionFactory;
  static Koan25Mapper mapper;
  static final String ADDR2_ID1 = "Apt. A";
  static final String ADDR2_ID2 = "Suite 234";

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    final String resource = "net/thornydev/mybatis/test/koan25/koan25-config.xml";
    final InputStream inputStream = Resources.getResourceAsStream(resource);
    sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    inputStream.close();

    session = sessionFactory.openSession();

    mapper = session.getMapper(Koan25Mapper.class);

    // since address2 field is null/blank by default in sakila,
    // we update a couple of records
    int r1 = mapper.addAddress2Data(1, ADDR2_ID1);
    int r2 = mapper.addAddress2Data(2, ADDR2_ID2);

    assertEquals(1, r1);
    assertEquals(1, r2);
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    if (session != null)
      // undo the address2 update we did in setup method
      session.rollback();
    session.close();
  }

  @Test
  public void learnToMapMultipleColumnsIntoArray() {

    Koan25Address addr = mapper.getAddressById(1);
    assertNotNull(addr);
    assertEquals(1, addr.getId());
    String[] addresses = addr.getAddresses();
    assertEquals("47 MySakila Drive", addresses[0]);
    assertEquals(ADDR2_ID1, addresses[1]);

    addr = mapper.getAddressById(2);
    assertNotNull(addr);
    assertEquals(2, addr.getId());
    addresses = addr.getAddresses();
    assertEquals("28 MySQL Boulevard", addresses[0]);
    assertEquals(ADDR2_ID2, addresses[1]);
  }
}
