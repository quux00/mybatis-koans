package net.thornydev.mybatis.test.koan13;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;

import net.thornydev.mybatis.test.domain.Actor;
import net.thornydev.mybatis.test.domain.Address;
import net.thornydev.mybatis.test.domain.City;
import net.thornydev.mybatis.test.domain.Country;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class Koan13 {

  // Note that we've moved to using one session for the whole koan
  static SqlSession session;
  static SqlSessionFactory sessionFactory;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    final String resource = "net/thornydev/mybatis/test/koan13/koan13-config.xml";
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
  public void learnToPopulateAnImmutableObjectFromMyBatisQuery() {
    final ActorMapper mapper = session.getMapper(ActorMapper.class);
    final List<Actor> actors = mapper.getActorByFullName("UMA", "WOOD");
    assertEquals(1, actors.size());
    final Actor uma = actors.get(0);
    assertEquals(13, uma.getId().intValue());
    assertEquals("UMA", uma.getFirstName());
    assertEquals("WOOD", uma.getLastName());
    assertNotNull(uma.getLastUpdate());
  }

  @Test
  public void learnToCreateAndInsertAnImmutableObjectWithPrecreatedId() {

    final ActorMapper mapper = session.getMapper(ActorMapper.class);

    final Actor a = new Actor(1000, "Timothy", "Foobar");

    final int n = mapper.insertNewActor(a);
    assertEquals(1, n);

    final Actor b = mapper.getActorById(1000);
    assertEquals(a.getId(), b.getId());
    assertEquals(a.getFirstName(), b.getFirstName());
    assertEquals(a.getLastName(), b.getLastName());
    assertNotNull(b.getLastUpdate());

    session.rollback();

  }

  @Test
  public void learnToCreateAndInsertAnImmutableObjectAwaitingIdFromDB() {

    final ActorMapper mapper = session.getMapper(ActorMapper.class);

    final Actor a = new Actor(null, "Sally", "Bazquux");
    final int n = mapper.insertNewActorGetNextIdFromDb(a);
    assertEquals(1, n);

    assertNotNull(a.getId());
    assertTrue(a.getId().intValue() > 0);

    final Actor b = mapper.getActorById(a.getId());
    assertEquals(a.getId(), b.getId());
    assertEquals(a.getFirstName(), b.getFirstName());
    assertEquals(a.getLastName(), b.getLastName());
    assertNotNull(b.getLastUpdate());

    session.rollback();

  }

  @Test
  public void learnToQueryImmutableObjectsThatChainToOtherDomainObjectsAndUseMultipleMappingFiles() {

    final AddressMapper mapper = session.getMapper(AddressMapper.class);

    final Address addr = mapper.getAddressById(600);
    assertNotNull(addr);
    assertEquals(600, addr.getId().intValue());
    assertEquals("1837 Kaduna Parkway", addr.getAddress());
    assertEquals("82580", addr.getPostalCode());

    final City city = addr.getCity();
    assertNotNull(city);
    assertEquals("Jining", city.getCity());

    final Country country = city.getCountry();
    assertNotNull(country);

    assertEquals("China", country.getCountry());

  }

}
