package net.thornydev.mybatis.koan.koan20;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.List;

import net.thornydev.mybatis.koan.domain.Film;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

// In Koan20, we learn and exercise the difference between
// ${} variables and #{} variables in MyBatis.  One creates
// a PreparedStatement and one doesn't.  Your job is to figure
// out which is which and when it is appropriate to use one
// or the other.
//
// In general, we know that PreparedStatements are preferred over
// simple Statement, as they are precompiled on the database and can
// allow caching client side in the JDBC driver.  However, in some
// cases it is difficult, maybe impossible, to use a prepared statement with
// MyBatis, depending on what you want to do. And whether prepared statements
// are faster is database and use-case specific, so you might benchmark
// your code and find that you'd rather use non-prepared statements.
//
// Here are a few stackoverflow links on the topic if you are interested:
// http://stackoverflow.com/questions/10717355/why-preparedstatement-is-preferable-over-statement
// http://stackoverflow.com/questions/8371053/jdbc-statement-preparedstatement-callablestatement-and-caching
//
// In this koan we consider two cases:
// 1. We want to use the SQL 'LIKE' operator in order to search for films with
//    a certain word in the title.  If you use a prepared statement, the variable
//    gets turned in a '?' symbol in the JDBC driver and you thus have to include
//    any '%' wildcards in the string you pass to the prepared statement.
//
//    This isn't wrong, but we might want the underlying SQL mapping to handle that by
//    wrapping the word we give it inside of wildcards, in which case we shouldn't
//    use a PreparedStatement.
//
// 2. In some implementations of the sakila database, the rating field for the film table
//    is an enum, rather than a varchar with a constraint on content.  In particular,
//    the MySQL and PostgreSQL implementations use an enum, where the Oracle, SQL Server,
//    and SQLite versions use a varchar.  In a PreparedStatement, a Java String
//    naturally maps to a VARCHAR, but it doesn't map to an enum. I found that this
//    was not a problem with MySQL, but is a problem with PostgreSQL.
//
//    So if you are using PostgreSQL, what should you do?
//    MyBatis does have an EnumTypeHandler so you may want to research that.
//    Another option is to not use a prepared statement.
//    Here is a short discussion that will help:
//       http://code.google.com/p/mybatis/issues/detail?id=103
//    If you want to pursue the prepared statement option, this link will help
//    give some context on JDBC and PostgreSQL enum types:
//       http://www.gotoquiz.com/web-coding/programming/java-programming/convert-between-java-enums-and-postgresql-enums/
//
// To complete this koan you will, at a minimum, need to edit the koan20-mapper.xml file.
// You may need to create additional classes or mappings depending on the route you pursue.
// If your answer differs from mine and it works, please let me know and we can add it the
// list of solutions for this koan.
public class Koan20 {

  static SqlSession session;
  static SqlSessionFactory sessionFactory;
  static Koan20Mapper mapper;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    String resource = "net/thornydev/mybatis/koan/koan20/koan20-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    inputStream.close();

    session = sessionFactory.openSession();
    mapper = session.getMapper(Koan20Mapper.class);
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    if (session != null) session.close();
  }

  @Test
  public void learnPoundVsDollarPrefixDifference() {
    // solve first with #{} notation
    Film f = new Film();
    f.setTitle("%NECKLACE%");
    List<String> films = mapper.selectLongFilmsByTitleWithPound(f);

    assertNotNull(films);
    assertEquals(3, films.size());
    assertEquals("NECKLACE OUTBREAK", films.get(0));
    assertEquals("OPPOSITE NECKLACE", films.get(2));

    // solve second with ${} notation
    Film f2 = new Film();
    f2.setTitle("NECKLACE");
    List<String> films2 = mapper.selectLongFilmsByTitleWithDollar(f2);

    assertNotNull(films2);
    assertEquals(3, films2.size());
    assertEquals("NECKLACE OUTBREAK", films2.get(0));
    assertEquals("OPPOSITE NECKLACE", films2.get(2));
  }

  @Test
  public void learnToUseDollarNotationForDbEnumTypes() {
    Film f = new Film();
    f.setTitle("DYNAMITE");
    f.setRating("PG-13");
    List<String> films = mapper.selectLongFilmsByTitleAndRating(f);

    assertNotNull(films);
    assertEquals(1, films.size());
    assertEquals("DYNAMITE TARZAN", films.get(0));
  }
}
