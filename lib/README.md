If you use ant to compile and run the koans, you will need to provide three jars in the directory (or symlinks to those jars elsewhere):

* JUnit 4 jar
* MyBatis 3.x.x jar
* JDBC driver jar for your database of choice

If you use H2, you will also need to have:  **NOT TRUE - also need some or all of these to use mysql and pg, right??**

slf4j-api.jar
commons-io.jar
h2.jar
logback-classic.jar
logback-core.jar

For example, here is what my lib dir looks like:

    lrwxrwxrwx 1 (...) junit4.jar -> /home/midpeter444/java/lib/junit4.jar
    lrwxrwxrwx 1 (...) mybatis.jar -> /home/midpeter444/java/lib/mybatis.jar
    lrwxrwxrwx 1 (...) mysql-connector-java.jar -> /home/midpeter444/java/lib/mysql-connector-java.jar
    lrwxrwxrwx 1 (...) postgresql.jar -> /home/midpeter444/java/lib/postgresql.jar
    lrwxrwxrwx 1 (...) slf4j-api.jar -> /home/midpeter444/java/lib/slf4j-1.6.6/slf4j-api-1.6.6.jar
    lrwxrwxrwx 1 (...) commons-io.jar -> /home/midpeter444/java/lib/commons-io-2.4/commons-io-2.4.jar
    lrwxrwxrwx 1 (...) h2.jar -> /home/midpeter444/java/lib/h2/bin/h2-1.3.166.jar
    lrwxrwxrwx 1 (...) logback-classic.jar -> /home/midpeter444/java/lib/h2/bin/logback-classic-1.0.6.jar
    lrwxrwxrwx 1 (...) logback-core.jar -> /home/midpeter444/java/lib/h2/bin/logback-core-1.0.6.jar
