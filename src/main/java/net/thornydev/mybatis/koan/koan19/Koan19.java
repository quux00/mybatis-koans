package net.thornydev.mybatis.koan.koan19;

import static org.junit.Assert.*;

import java.io.InputStream;

import net.thornydev.mybatis.koan.domain.Address;
import net.thornydev.mybatis.koan.domain.Staff;
import net.thornydev.mybatis.koan.util.Email;
import net.thornydev.mybatis.koan.util.NullEmail;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

// The Null Object pattern can be useful in many situations.
// See http://www.oodesign.com/null-object-pattern.html for more info.
//
// Koan19 focuses on implementing the Null Object pattern for
// an Email class.  The staff table in the sakila database has
// an email field that does not have a NOT NULL constraint. If
// the email is null, we don't want a null in the Staff object.
// Instead we want to give it a reference to an util.NullEmail
// object, which implements the util.Email interface that I've
// provided for you.
//
// Your task is to figure out how to have MyBatis create a Staff
// object that either has an EmailImpl object or a NullEmail object.
// MyBatis should create an EmailImpl object and give it to the
// Staff object when the email field is non-null. It should
// create and use a NullEmail object when the email field is null.
//
// Unfortunately, in the default sakila db dataset, there are only
// two staff members and neither has a null email, so we make
// the second one have a null email for this test (see the setUp
// and tearDown methods below).
//
// To complete this koan you will need to fill in the resultMap in
// the koan19-mapper.xml file and then create any necessary supporting
// code to implement this desired functionality.  If you need to,
// you can also add an additional constructor to the domain.Staff class
// if you don't like the ones I've provided, but don't remove the ones
// I've created if you want to try to run my solution.
//
// **RESTRICTION** - the logic of whether to create an
// EmailImpl or NullEmail object should NOT be placed in the Staff
// object. Put it somewhere else, embedding it in a MyBatis specific
// class or some sort of Factory.
//
// NOTE: After completing this koan, take a look at my solution in
// completed-koans/koan19.  If you took a different approach, let
// me know as my first attempt failed and I'd like to see how you
// got it done.
public class Koan19 {

  static SqlSession session;
  static SqlSessionFactory sessionFactory;
  static Koan19Mapper mapper;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    String resource = "net/thornydev/mybatis/koan/koan19/koan19-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    inputStream.close();

    session = sessionFactory.openSession();
    mapper = session.getMapper(Koan19Mapper.class);
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    if (session != null) session.close();
  }

  @Before
  public void setUp() throws Exception {
    mapper.setEmail(null, 2);
  }

  @After
  public void tearDown() throws Exception {
    // restore it back to what it was
    mapper.setEmail("Jon.Stephens@sakilastaff.com", 2);
    // Note: technically this is not needed if you don't have autocommit turned on,
    //       but putting it in case someone does run the koan with autocommit on
  }

  @Test
  public void firstStaffMemberShouldHaveNonNullEmail() {
    Staff st = session.selectOne("getStaffById", 1);
    assertNotNull(st);
    assertEquals(1, st.getId());
    assertEquals("Mike", st.getFirstName());
    assertTrue(st.isActive());
    assertEquals("Mike", st.getUsername());

    Address addr = st.getAddress();
    assertNotNull(addr);
    assertEquals(3, addr.getId().intValue());
    assertEquals("Alberta", addr.getDistrict());

    Email e = st.getEmail();
    assertNotNull(e);
    assertFalse(e instanceof NullEmail);
    assertEquals("Mike.Hillyer", e.getUsername());
  }

  @Test
  public void secondStaffMemberShouldHaveEmailNullObject() {
    Staff st = session.selectOne("getStaffById", 2);
    assertNotNull(st);
    assertEquals(2, st.getId());
    assertEquals("Jon", st.getUsername());

    Address addr = st.getAddress();
    assertNotNull(addr);
    assertEquals(4, addr.getId().intValue());
    assertEquals("QLD", addr.getDistrict());

    Email e = st.getEmail();
    assertNotNull(e);
    assertTrue(e instanceof NullEmail);
    assertEquals("", e.getUsername());
  }
}
