package net.thornydev.mybatis.koan.koan25;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

// In Koan25, we imagine a scenario where you have inherited a domain
// class and an SQL statement that cannot be changed.  You can only
// change the MyBatis pieces around it, including the mapping, but NOT
// the SQL.
// 
// In this case the two things you CANNOT change are:
// * the net.thornydev.mybatis.koan.koan25.Koan25Address class
// * the SQL statement in the getAddressById mapping
// 
// You can change anything else (well except for the tests in this
// koan class of course).
//
// Your task is to figure how to get the SQL in the getAddressById
// query shoehorned into the Koan25Address class which wants an array
// of the two address fields, rather a separate "address" and "address2"
// fields as we have in the domain.Address class.  (To emphasize, you 
// are NOT allowed to use the domain.Address class here => pretend it 
// doesn't exist).
// 
// Note 1: by default the sakila database has no data in the address2
// field of the address table, so I do two updates in the setup
// to modify two records to have some address2 data for the test.
//
// Note 2: if you come up with a different answer than mine, I'd 
// like to see it - please submit it to the mybatis-koans repo.
public class Koan25 {

  static SqlSession session;
  static SqlSessionFactory sessionFactory;
  static Koan25Mapper mapper;
  static final String ADDR2_ID1 = "Apt. A";
  static final String ADDR2_ID2 = "Suite 234";
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    final String resource = "net/thornydev/mybatis/koan/koan25/koan25-config.xml";
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
