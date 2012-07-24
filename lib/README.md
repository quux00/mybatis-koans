If you use ant to compile and run the koans, you will need to provide a number of jars in the directory (or symlinks to those jars elsewhere):

* JUnit 4 jar
* MyBatis 3.x.x jar
* JDBC driver jar for your database of choice
  * H2 db jar: http://www.h2database.com/html/download.html
* Apache Commons IO: http://commons.apache.org/io/download_io.cgi
* The logback-class and logback-core jars: http://logback.qos.ch/download.html
* The slf4j-api jar: http://www.slf4j.org/download.html

How my lib dir looks:

    $ ls -l
    lrwxrwxrwx 1 (...) junit4.jar -> /home/midpeter444/java/lib/junit4.jar
    lrwxrwxrwx 1 (...) mybatis.jar -> /home/midpeter444/java/lib/mybatis.jar
    lrwxrwxrwx 1 (...) slf4j-api.jar -> /home/midpeter444/java/lib/slf4j-1.6.6/slf4j-api-1.6.6.jar
    lrwxrwxrwx 1 (...) commons-io.jar -> /home/midpeter444/java/lib/commons-io-2.4/commons-io-2.4.jar
    lrwxrwxrwx 1 (...) logback-classic.jar -> /home/midpeter444/java/lib/h2/bin/logback-classic-1.0.6.jar
    lrwxrwxrwx 1 (...) logback-core.jar -> /home/midpeter444/java/lib/h2/bin/logback-core-1.0.6.jar
    lrwxrwxrwx 1 (...) h2.jar -> /home/midpeter444/java/lib/h2/bin/h2-1.3.166.jar
    lrwxrwxrwx 1 (...) mysql-connector-java.jar -> /home/midpeter444/java/lib/mysql-connector-java.jar
    lrwxrwxrwx 1 (...) postgresql.jar -> /home/midpeter444/java/lib/postgresql.jar
