package net.thornydev.mybatis.test.koan14;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;

import net.thornydev.mybatis.test.domain.Actor;
import net.thornydev.mybatis.test.domain.Address;
import net.thornydev.mybatis.test.domain.City;
import net.thornydev.mybatis.test.util.KoanSchoolMarm;
import net.thornydev.mybatis.test.util.Range;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class Koan14 {

  static SqlSession session;
  static SqlSessionFactory sessionFactory;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    final String resource = "net/thornydev/mybatis/test/koan14/koan14-config.xml";
    final InputStream inputStream = Resources.getResourceAsStream(resource);
    sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    inputStream.close();

    session = sessionFactory.openSession();
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    if (session != null)
      session.close();
  }

  @Test
  public void learnToUseObjectFactory_City() {

    final City c = session.selectOne("getCityById", 188);

    assertNotNull(c);
    assertEquals(188, c.getId());
    assertEquals("Guadalajara", c.getCity());
    assertNotNull(c.getLastUpdate());
    assertNotNull(c.getCountry());

    checkObjectFactoryWasUsed();

  }

  @Test
  public void learnToUseObjectFactoryAndResultHandler_Actor() {

    final ActorResultHandler rh = new ActorResultHandler();
    session.select("getActorByRange", new Range(36, 42), rh);
    final List<Actor> la = rh.getActors();
    assertNotNull(la);
    assertEquals(6, la.size());

    final Actor first = la.get(0);
    assertEquals(36, first.getId().intValue());
    assertEquals("BURT", first.getFirstName());
    assertEquals("DUKAKIS", first.getLastName());

    final Actor fifth = la.get(4);
    assertEquals(41, fifth.getId().intValue());
    assertEquals("JODIE", fifth.getFirstName());
    assertEquals("DEGENERES", fifth.getLastName());

    checkObjectFactoryWasUsed();
    checkActorMapConstructorUsed();
  }

  @Test
  public void learnToUseObjectFactory_Address() {
    final AddressMapper mapper = session.getMapper(AddressMapper.class);
    final Address addr = mapper.getAddressById(100);
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
    assertTrue("You must use the ObjectFactory to pass this koan",
               KoanSchoolMarm.getInstance().getObjectFactoryUsed());
    // reset for next test
    KoanSchoolMarm.getInstance().setObjectFactoryUsed(false);
  }

  private void checkActorMapConstructorUsed() {
    assertTrue(
      "You must use the Actor constructor that takes a Map for this koan test",
      KoanSchoolMarm.getInstance().getActorMapConstructorUsed());
  }
}
