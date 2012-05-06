If you use ant to compile and run the koans, you will need to provide three jars in the directory (or symlinks to those jars elsewhere):

* JUnit 4 jar
* MyBatis 3.x.x jar
* JDBC driver jar for your database of choice

For example, here is what my lib dir looks like:

    lrwxrwxrwx 1 (...) junit4.jar -> /home/midpeter444/java/lib/junit4.jar
    lrwxrwxrwx 1 (...) mybatis.jar -> /home/midpeter444/java/lib/mybatis.jar
    lrwxrwxrwx 1 (...) mysql-connector-java.jar -> /home/midpeter444/java/lib/mysql-connector-java.jar
    lrwxrwxrwx 1 (...) postgresql.jar -> /home/midpeter444/java/lib/postgresql.jar
