package net.thornydev.mybatis.koan.koan23;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

// would a type discriminator help here?
// there is also an enum type handler
// http://stackoverflow.com/questions/10219253/mybatis-enum-usage
// http://stackoverflow.com/questions/10562895/enum-constant-in-mybatiss-sql-query
public class Koan23 {

  static SqlSessionFactory sessionFactory;
  SqlSession session;
  Koan23Mapper mapper;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    String resource = "net/thornydev/mybatis/koan/koan23/koan23-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    inputStream.close();
  }

  @Before
  public void setUp() throws Exception {
    session = sessionFactory.openSession();
    mapper = session.getMapper(Koan23Mapper.class);
  }
    
  @After
  public void tearDown() throws Exception {
    if (session != null) session.close();
  }

  @Test
  public void learnToConvertQueryResultIntoJavaEnum() {
    Category c = mapper.getCategoryByName("Horror");
    assertEquals(c, Category.HORROR);
  }
  
  @Test
  public void learnToUseJavaEnumsInComplexQuery() {
    FilmWithCategories fwc = mapper.getFilmById(25);
    assertNotNull(fwc);
    assertEquals(25, fwc.getId());
    assertEquals("ANGELS LIFE", fwc.getTitle());
    List<Category> lc = fwc.getCategories();
    assertEquals(1, lc.size());
    assertNotNull(lc.get(0));
    assertEquals(Category.NEW, lc.get(0));
    assertEquals("New", lc.get(0).toString());
  }
  
  @Test
  public void learnToInsertFromJavaEnum() {
    FilmWithCategories fwc = mapper.getFilmById(25);
    fwc.addCategory(Category.FAMILY);
    fwc.addCategory(Category.COMEDY);
    
    int n = mapper.deleteAllCategoriesForFilm(fwc);
    assertEquals(1, n);
    
    for (Category c: fwc.getCategories()) {
      n = mapper.addCategoryForFilm(fwc, c);
      assertEquals(1, n);
    }
    
    FilmWithCategories fwc25 = mapper.getFilmById(25);
    assertNotNull(fwc25);
    assertEquals("ANGELS LIFE", fwc.getTitle());
    List<Category> lc = fwc.getCategories();
    assertEquals(3, lc.size());
    assertNotNull(lc.get(0));
    assertEquals(1, Collections.frequency(lc, Category.COMEDY));
    assertEquals(1, Collections.frequency(lc, Category.FAMILY));
    assertEquals(1, Collections.frequency(lc, Category.NEW));
  }

}


//org.apache.ibatis.exceptions.PersistenceException: 
//### Error querying database.  Cause: java.lang.IllegalArgumentException: No enum constant net.thornydev.mybatis.koan.koan22.Category.11
//### The error may exist in net/thornydev/mybatis/koan/koan22/koan22-mapper.xml
//### The error may involve net.thornydev.mybatis.koan.koan22.Koan22Mapper.getCategoryByName-Inline
//### The error occurred while setting parameters
//### SQL: SELECT category_id AS id, name     FROM category     WHERE name = ?
//### Cause: java.lang.IllegalArgumentException: No enum constant net.thornydev.mybatis.koan.koan22.Category.11
//  at org.apache.ibatis.exceptions.ExceptionFactory.wrapException(ExceptionFactory.java:23)
//  at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:104)
//  at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:95)
//  at org.apache.ibatis.session.defaults.DefaultSqlSession.selectOne(DefaultSqlSession.java:59)
//  at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:95)
//  at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:40)
//  at $Proxy7.getCategoryByName(Unknown Source)
//  at net.thornydev.mybatis.koan.koan22.Koan22.learnToConvertQueryResultIntoJavaEnum(Koan22.java:48)
//  at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
//  at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
//  at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
//  at java.lang.reflect.Method.invoke(Method.java:601)
//  at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:44)
//  at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:15)
//  at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:41)
//  at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:20)
//  at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:28)
//  at org.junit.internal.runners.statements.RunAfters.evaluate(RunAfters.java:31)
//  at org.junit.runners.BlockJUnit4ClassRunner.runNotIgnored(BlockJUnit4ClassRunner.java:79)
//  at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:71)
//  at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:49)
//  at org.junit.runners.ParentRunner$3.run(ParentRunner.java:193)
//  at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:52)
//  at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:191)
//  at org.junit.runners.ParentRunner.access$000(ParentRunner.java:42)
//  at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:184)
//  at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:28)
//  at org.junit.runners.ParentRunner.run(ParentRunner.java:236)
//  at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:50)
//  at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:38)
//  at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:467)
//  at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:683)
//  at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:390)
//  at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:197)
//Caused by: java.lang.IllegalArgumentException: No enum constant net.thornydev.mybatis.koan.koan22.Category.11
//  at java.lang.Enum.valueOf(Enum.java:236)
//  at net.thornydev.mybatis.koan.koan22.Category.valueOf(Category.java:1)
//  at net.thornydev.mybatis.koan.koan22.Category.fromString(Category.java:44)
//  at net.thornydev.mybatis.koan.koan22.EnumCategoryTypeHandler.getNullableResult(EnumCategoryTypeHandler.java:15)
//  at net.thornydev.mybatis.koan.koan22.EnumCategoryTypeHandler.getNullableResult(EnumCategoryTypeHandler.java:1)
//  at org.apache.ibatis.type.BaseTypeHandler.getResult(BaseTypeHandler.java:51)
//  at org.apache.ibatis.executor.resultset.FastResultSetHandler.createPrimitiveResultObject(FastResultSetHandler.java:425)
//  at org.apache.ibatis.executor.resultset.FastResultSetHandler.createResultObject(FastResultSetHandler.java:372)
//  at org.apache.ibatis.executor.resultset.FastResultSetHandler.createResultObject(FastResultSetHandler.java:355)
//  at org.apache.ibatis.executor.resultset.FastResultSetHandler.getRowValue(FastResultSetHandler.java:255)
//  at org.apache.ibatis.executor.resultset.FastResultSetHandler.handleRowValues(FastResultSetHandler.java:214)
//  at org.apache.ibatis.executor.resultset.FastResultSetHandler.handleResultSet(FastResultSetHandler.java:186)
//  at org.apache.ibatis.executor.resultset.FastResultSetHandler.handleResultSets(FastResultSetHandler.java:152)
//  at org.apache.ibatis.executor.statement.PreparedStatementHandler.query(PreparedStatementHandler.java:57)
//  at org.apache.ibatis.executor.statement.RoutingStatementHandler.query(RoutingStatementHandler.java:70)
//  at org.apache.ibatis.executor.SimpleExecutor.doQuery(SimpleExecutor.java:57)
//  at org.apache.ibatis.executor.BaseExecutor.queryFromDatabase(BaseExecutor.java:267)
//  at org.apache.ibatis.executor.BaseExecutor.query(BaseExecutor.java:141)
//  at org.apache.ibatis.executor.CachingExecutor.query(CachingExecutor.java:105)
//  at org.apache.ibatis.executor.CachingExecutor.query(CachingExecutor.java:81)
//  at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:101)
//  ... 32 more
//
