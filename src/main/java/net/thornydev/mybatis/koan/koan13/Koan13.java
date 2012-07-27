package net.thornydev.mybatis.koan.koan13;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.List;

import net.thornydev.mybatis.koan.domain.Actor;
import net.thornydev.mybatis.koan.domain.Address;
import net.thornydev.mybatis.koan.domain.City;
import net.thornydev.mybatis.koan.domain.Country;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

// In earlier koans, we used the <constructor> element in our mappings
// to handle domain objects that did not have default (empty) constructors.
// In Koan13, we take that to the next level by using "immutable objects":
// objects whose fields are all declared final and must be set in their
// constructors.
//
// We start by using the Actor class, which we model as having no
// dependencies. All its fields are final, so we start by creating a
// query mapping that will set all the fields in its constructor.
//
// Then we show how we can create an immutable Actor class and insert it
// into the actor table. That is pretty easy. But what if we want to let
// MyBatis set the id with a <selectKey> mapper like we've used before?
// If the Actor class is immutable, we can't set the id to null and then
// let MyBatis later set the id to the next value from the database. Or
// can we? It turns out, MyBatis can, as it uses reflection - it can set
// the id even though it is final and has no setters. We will work through
// getting that set up and working.
//
// After completing those, the next challenge will be to deal with an
// immutable class that depends on other domain objects. In this case,
// we start working with the Address entity, which we model as having a
// "has-one" relationship with the City entity, which (as we saw in an
// earlier koan) has a "has-one" relationship with the Country entity.
// The Address object has a "final" (immutable) reference to its City object.
// We'll see how to set up a MyBatis mapping to handle a query that pulls
// back an Address, City and Country object.
//
// To ratchet up the koan challenge even more, we will specify each domain
// entity to have its own mapper file. You will need to configure the config
// and mapper xml files to properly reference each other.
//
// You'll notice that we have constructors with long param lists, which leads
// to multiple versions of constructors, having different combinations of
// optional params. There are better ways to handle this for immutable objects
// and we will tackle that in the next koan.
//
// In order to complete this koan, you will need to:
// 1. Edit the TODO entries in this Koan13 TestCase
// 2. Edit the TODO entries in the three mapper xml files that have them
// 3. Edit the TODO entries in the MyBatis config xml file
public class Koan13 {

  // Note that we've moved to using one session for the whole koan
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
    // TODO: remember to close your resources
  }

  @Test
  public void learnToPopulateAnImmutableObjectFromMyBatisQuery() {
    ActorMapper mapper = session.getMapper(ActorMapper.class);
    List<Actor> actors = mapper.getActorByFullName("UMA", "WOOD");
    assertEquals(1, actors.size());
    Actor uma = actors.get(0);
    assertEquals(13, uma.getId().intValue());
    assertEquals("UMA", uma.getFirstName());
    assertEquals("WOOD", uma.getLastName());
    assertNotNull(uma.getLastUpdate());
  }

  @Test
  public void learnToCreateAndInsertAnImmutableObjectWithPrecreatedId() {
    ActorMapper mapper = session.getMapper(ActorMapper.class);

    Actor a = new Actor(1000, "Timothy", "Foobar");
    int n = mapper.insertNewActor(a);
    assertEquals(1, n);

    Actor b = mapper.getActorById(1000);
    assertEquals(a.getId(), b.getId());
    assertEquals(a.getFirstName(), b.getFirstName());
    assertEquals(a.getLastName(), b.getLastName());
    assertNotNull(b.getLastUpdate());

    session.rollback();
  }

  @Test
  public void learnToCreateAndInsertAnImmutableObjectAwaitingIdFromDB() {
    ActorMapper mapper = session.getMapper(ActorMapper.class);

    // insert null as Id - let MyBatis fill it in
    // (Note: we switched to using Integer rather than int as type for id here
    //        in order to allow null to be a sentinel value as a temp placeholder;
    //        this is just a general principle and doesn't affect how MyBatis works)
    Actor a = new Actor(null, "Sally", "Bazquux");
    int n = mapper.insertNewActorGetNextIdFromDb(a);
    assertEquals(1, n);

    assertNotNull(a.getId());
    assertTrue(a.getId().intValue() > 0);

    Actor b = mapper.getActorById(a.getId());
    assertEquals(a.getId(), b.getId());
    assertEquals(a.getFirstName(), b.getFirstName());
    assertEquals(a.getLastName(), b.getLastName());
    assertNotNull(b.getLastUpdate());

    session.rollback();
  }

  @Test
  public void learnToQueryImmutableObjectsThatChainToOtherDomainObjectsAndUseMultipleMappingFiles() {
    AddressMapper mapper = session.getMapper(AddressMapper.class);

    Address addr = mapper.getAddressById(600);
    assertNotNull(addr);
    assertEquals(600, addr.getId().intValue());
    assertEquals("1837 Kaduna Parkway", addr.getAddress());
    assertEquals("82580", addr.getPostalCode());

    City city = addr.getCity();
    assertNotNull(city);
    assertEquals("Jining", city.getCity());

    Country country = city.getCountry();
    assertNotNull(country);
    assertEquals("China", country.getCountry());
  }
}
