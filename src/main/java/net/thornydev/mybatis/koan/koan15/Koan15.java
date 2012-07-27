package net.thornydev.mybatis.koan.koan15;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Date;

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

// Koan15 returns to DML statements, INSERT in particular.
// We take on two things we didn't do before:
// 1. Inserting records from a domain object that depends on other
//    domain objects.
// 2. Inserting null values into nullable columns.
//
// For the first challenge we insert a City object into the city table
// and we insert an Address into the address table.
// City has a reference to a Country object and Address has a reference
// to a City object.  Out of the box, MyBatis has no idea how to get
// the city_id or country_id out of the City and Country domain objects,
// so we must provide a TypeHandler.
//
// For the second challenge on inserting nulls, the MyBatis 3 User Guide says
//    "The JDBC type is only required for
//     nullable columns upon insert, update or delete."
//      -and-
//    "NOTE The JDBC Type is required by JDBC for all nullable columns, if
//     null is passed as a value. You can investigate this yourself by
//     reading the JavaDocs for the PreparedStatement.setNull() method."
//
// So we follow those instructions when inserting null values into the
// nullable columns of the address table.
//
// Note: so far, I have not found that specifying the jdbcType for nullable
//       columns is required.  I have tested this with using:
//       - Java 6 and Java 7
//       - MyBatis-3.1.1
//       - PostgreSQL 9.1 and MySQL 5.5
//
// In order to complete this koan, you will need to:
// 1. Complete the TODO entries in the koan15-mapper.xml file
// 2. Implement two TypeHandlers:
//    a. net.thornydev.mybatis.koan.util.CountryIdTypeHandler
//    b. net.thornydev.mybatis.koan.util.CityIdTypeHandler
public class Koan15 {

  static SqlSession session;
  static SqlSessionFactory sessionFactory;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    String resource = "net/thornydev/mybatis/koan/koan15/koan15-config.xml";
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
  public void learnToInsertDomainObjectThatReferencesAnotherDomainObject() {
    int cntBefore = (Integer) session.selectOne("getCount", "city");

    Country c = session.selectOne("getCountryById", 22);

    City city = new City(1000, "FooCity", new Date());
    city.setCountry(c);

    session.insert("insertCity", city);
    int cntAfter = (Integer) session.selectOne("getCount", "city");

    assertEquals(cntAfter, cntBefore + 1);

    session.rollback();
  }

  @Test
  public void learnToInsertDomainObjectWithNullValues() {
    City city = session.selectOne("getCityById", 375);
    assertNotNull(city);
    assertNotNull(city.getCountry());
    assertEquals("Okara", city.getCity());
    assertEquals("Pakistan", city.getCountry().getCountry());

    Address addr = new Address.Builder().
    id(1000).
    address("100 Foo St.").
    address2(null).  // could also just leave it off, but here to be explicit
    district("Bar").
    city(city).
    postalCode(null).
    phone("555-8675-309").
    build();

    int before = (Integer) session.selectOne("getCount", "address");
    int n = session.insert("insertAddress", addr);
    assertEquals(1, n);
    int after = (Integer) session.selectOne("getCount", "address");
    assertEquals(after, before + 1);

    session.rollback();
  }
}
