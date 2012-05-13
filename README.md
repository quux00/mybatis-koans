# MyBatis Koans

A [koan](http://en.wikipedia.org/wiki/K%C5%8Dan) is a question or statement to be meditated upon in order to improve and test a student's progress. Among programmers, software koans have become a clever way to learn a software language or tool.  As the [Ruby koan website](http://rubykoans.com/) says: "The Koans walk you along the path to enlightenment" -- in this case to learn and practice with the [MyBatis 3](http://mybatis.org/) data mapper framework.

A software koan comes in the form of a broken unit test that you must fix to get it to pass, usually by filling in the blanks or entire missing sections.  The koan is intended to teach one or a small set of cohesive features about the language or tool being studied.

The structure of these koans is inspired by the challenging and informative [Neo4j koans](https://github.com/jimwebber/neo4j-tutorial) by Jim Webber and colleagues.

---

# Getting Started

## Prerequisites

In order to do the koans, you must have:

* Java JDK (preferably version 6 or higher)
* [MyBatis 3](http://mybatis.org/) Persistence Framework
* [JUnit 4](http://www.junit.org/)
* A database server (and client) installed
  * These koans come have been specifically tested with MySQL and PostgreSQL and come with some instructions around using those databases.  You should be able to use (or adapt) them to work with Oracle and other databases if you desire.
* The _sakila_ database and dataset (the PostgreSQL version is called "pagila").  See "Set up" section below for details.
* JDBC driver for your database of choice.

If you want to run the koans via the provided ant `build.xml` file, then you will also need [Apache Ant](http://ant.apache.org/) installed (preferably version 1.8 or higher).

I don't provide instructions here on how to set those up (other than the sakila database), as I assume you are familiar with programming in Java and setting up and using a relational database.

While MyBatis can be used with [other JVM languages](http://www.fdmtech.org/2011/12/mybatis-for-scala-1-0-beta-released), these koans are all in pure Java.

_Note_: My instructions below assume you know how to get around on the command line.  In particular, I'm a Unix/Linux guy.  Since this is pure Java, the koans should work on Windows, but some of my instructions assume you have a Unix/Linux environment.

---

## Editing the koans

No IDE is required.  You can use any editor you like or an IDE.  I have built and tested the koans using emacs and Eclipse.

---

## Set up

Unfortunately, the setup for the MyBatis koans is not as simple as the Ruby koans, since you have to set up and configure a database, load a standard dataset, and configure the MyBatis system for it.  So you'll need to roll up your sleeves a bit before you can get started meditating on the koans themselves.

---

### Get the Koans

After installing and testing the prerequisites, clone this repository from github:

    git clone git@github.com:midpeter444/mybatis-koans.git

Go into the mybatis-koans directory and either view it in your graphical directory explorer of choice or on the command line type `tree`.  (If you don't have the [tree](http://www.computerhope.com/unix/tree.htm) command on Unix, go install it.  If you are on Windows, I built a [simple version in Java](https://github.com/midpeter444/tree), which you can use.)

The koans come twice - once in "uncompleted" form and once in "completed" form.

The "uncompleted" koans are the ones you will fill in. They are JUnit 4 tests in the `src/net/thornydev/mybatis/koan/koanXX` directories.  Each koan has its own directory (and thus package name) in order to have separate MyBatis config files to exercise different aspects of the MyBatis data mapper framework.

The completed koans are there for reference in case you get stuck and need to see the solution.  They are in the `completed-koans` directory.

Also notice that there is an `sql` directory that has some visual aids and documentation about the sakila database you will be using - more on that later.

---

### Download and set up the sakila database/dataset

**Step 1:**  Download the sakila database

Here are three places to get it from:

* [http://code.google.com/p/sakila-sample-database-ports/](http://code.google.com/p/sakila-sample-database-ports/)
  * _I recommend using this one, as this is what I used to set up and test the koans_
  * This one has versions for MySQL, PostgreSQL, Oracle, SQLite, SQL Server and a few others
* Official MySQL version: [http://dev.mysql.com/doc/index-other.html](http://dev.mysql.com/doc/index-other.html)
* PostgreSQL "pagila" version from PGFoundry:  [http://pgfoundry.org/projects/dbsamples](http://pgfoundry.org/projects/dbsamples)

<br/>
**Step 2:**  Unzip the Sakila zip file in the `src/main/sql` directory of mybatis-koans

This will create a Sakila directory under which will be database-specific versions:

    $ ls -CF src/main/sql/Sakila
    interbase-sakila-db/  mysql-sakila-db/   postgres-sakila-db/  sqlite-sakila-db/
    ms-access-sakila-db/  oracle-sakila-db/  ReadME.txt           sql-server-sakila-db/

<br/>
**Step 3:**  Create the database and load the data

Follow the standard process for your database of choice for creating a database, running the DDL and DML scripts provided in the Sakila zip file.  Below I show how to do this for PostgreSQL and MySQL from the command line (tested on a Linux machine).

<div style="border: 2px solid grey; margin: 10px; padding: 0px 10px;">
  <p><strong>Creating and loading the PostgreSQL sakila database</strong></p>
  <pre>
  $ cd src/main/sql/Sakila/postgres-sakila-db
  # edit the next line to have your username rather than mine
  $ echo "GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO midpeter444;" >> postgres-sakila-schema.sql
  $ sudo su postgres
  $ createdb sakila
  $ psql sakila &lt; postgres-sakila-schema.sql
  $ psql sakila &lt; postgres-sakila-data.sql
  $ &lt;Ctrl-D&gt; (log-out as postgres back to your user)
  $ psql -h localhost  # log in here and check that the tables were created and that you can query them</pre>
</div>


<div style="border: 2px solid grey; margin: 10px; padding: 0px 10px;">
  <p><strong>Creating and loading the MySQL sakila database</strong></p>
  <pre>
  $ cd src/main/sql/Sakila/mysql-sakila-db
  $ mysql -p
  mysql> create database sakila;
  Query OK, 1 row affected (0.00 sec)
  mysql> exit
  $ mysql sakila -p &lt; sakila-schema.sql
  $ mysql sakila -p &lt; sakila-data.sql
  $ mysql -p  # log in here and check that the tables were created and that you can query them</pre>
</div>


<br/>
**Step 4:**  Study the database diagrams and structure to get familiar with it

In the `src/main/sql` directory, I have provided some analysis documents and visualizations reverse engineered from the PostgreSQL and MySQL sakila databases.

View the PNG files in the `postgres-viz/dbvis` or `mysql-viz/dbvis` directories to see physical data models and relationships between tables.  These were generated using the free version of [DbVisualizer](http://www.dbvis.com/).

For a deeper analysis open the `index.html` in either `postgres-viz/schemaSpy` or `mysql-viz/schemaSpy`.  These were generated using the open source [SchemaSpy](http://schemaspy.sourceforge.net/) tool.

Finally, you can also view the documentation that MySQL provides for the sakila database: [http://dev.mysql.com/doc/sakila/en/index.html](http://dev.mysql.com/doc/sakila/en/index.html)

---

### Configure the environment to start working on the koans

There are two ways to do the koans (that I have tested):

1. Create an Eclipse project, set up the Java Build Path to the dependencies in Eclipse and run the koan JUnit tests one at a time from within Eclipse.
  * If you choose this route you can put the dependencies (jars) in the provided `lib` directory or you can configure Eclipse to find them elsewhere.
2. Use your editor of choice (including Eclipse) to edit the koans and run the koans using ant from the command line.  You will need to put the jar dependencies in the `lib` directory (see below).

---

### Do the Koans in Eclipse

Open Eclipse and start a new Java Project.  Give it the project name "mybatis-koans" and uncheck the "Use default location" option.  Browse to the mybatis-koans directory where you downloaded the koans from GitHub.

Set up your Java Build Path to have the mybatis-3.x.x jar file, the JDBC library of your choice, and JUnit 4.

Navigate to Koan01.java in the net.thornydev.mybatis.koan.koan1 package.  Run it as a JUnit test by right-clicking in the editing screen and choose Run As > JUnit Test.  It should fail.  If it passes, then you are probably in the completed koans section.

Read the instructions in the Koan.  You will have a series of TODOs to complete.  Fill those in, using the [MyBatis User Guide](http://www.mybatis.org/core/) as help until the koan passes.  Then move to the next one.

---

### Do the Koans using ant (command line)

Make sure you have ant in your PATH.

In the mybatis-koans directory, run `ant -p` to make sure ant is working and to see the available targets.

You will need to put (or symlink to) the jar file dependencies in the `mybatis-koans/lib` directory.  For example, here is what my `lib` directory looks like:

    $ ls -l
    lrwxrwxrwx 1 (...) junit4.jar -> /home/midpeter444/java/lib/junit4.jar
    lrwxrwxrwx 1 (...) mybatis.jar -> /home/midpeter444/java/lib/mybatis.jar
    lrwxrwxrwx 1 (...) mysql-connector-java.jar -> /home/midpeter444/java/lib/mysql-connector-java.jar
    lrwxrwxrwx 1 (...) postgresql.jar -> /home/midpeter444/java/lib/postgresql.jar


Run `ant koan01`.  You should get a failing test with output like:

    koan01:
    [junit] Running net.thornydev.mybatis.koan.koan01.Koan01
    [junit] Testsuite: net.thornydev.mybatis.koan.koan01.Koan01
    [junit] Tests run: 1, Failures: 1, Errors: 0, Time elapsed: 0.081 sec
    [junit] Tests run: 1, Failures: 1, Errors: 0, Time elapsed: 0.081 sec
    [junit] 
    [junit] Testcase: learnBasicConfigurationSetup took 0.063 sec
    [junit] 	FAILED
    [junit] null
    [junit] junit.framework.AssertionFailedError
    [junit] 	at net.thornydev.mybatis.koan.koan01.Koan01.learnBasicConfigurationSetup(Koan01.java:29)
    [junit] 

Open `src/net/thornydev/mybatis/koan/koan01/Koan01.java` in your editor of choice and read the instructions in the Koan.  You will have a series of TODOs to complete.  Fill those in, using the [MyBatis User Guide](http://www.mybatis.org/core/) or other online resources as help until the koan passes.  Then move to the next one.


#### Tweaking the settings

By default, the koan test output will be written to a plain text formatted file in the top directory called `TEST-net.thornydev.mybatis.koan.koan01.Koan01.txt` (and so on for the other koans).  If you do not want these files you can set the `log.koan.output.to.file` property to "false" in the build.xml file, like so:

    <property name="log.koan.output.to.file" value="false"/>

MyBatis uses log4j, so a log4j.properties file is provided in the src directory.  By default it will log WARN level messages and higher to the console only.  If you want other settings, edit this file.

---

# Koan Topics

### Koan 01

**Configuration Koan**

Learn:

* how to configure a MyBatis config file, including a shared properties that will used by the MyBatis config file 
* how to wire up your Java code to use the MyBatis config file
* how to create a SqlSessionFactory

---

### Koan 02

**Xml mapper file**

Learn:

* how to reference a mapper file from a MyBatis config file
* how to set up a SQL mapping in a mapper file
* how to create a `SqlSession`
* how to call the SQL mapping and get data from the database via MyBatis
* the basic types of datastructures that MyBatis can return and how to specify them

---

### Koan 03

**Domain objects**

Learn:

* how to create a mapper xml file that returns a domain object, rather than a generic data structure
* how to return a list or map of domain objects from a mapped SQL query
* how to set up a TypeAlias in the MyBatis config file

---

### Koan 04

**Mapper interface**

Learn:

* how to create and use a Java Mapper interface to do the same type of queries in the previous koans

---

### Koan 05

**Multiple Arguments and RowBounds**

Learn:

* how to create and use a Java Mapper interface to do the same type of queries in the previous koans
* how to return a simple numeric value from a mapped SQL query
* how to pass in multiple arguments to a mapped query (including using annotations)
* how to use a MyBatis RowBounds object to limit the number of results returned

---

### Koan 06

**Annotation driven mappings**

Learn:

* how to do annotation driven mappings
* how to specify a mapper in the config file when you don't have an XML mapping file

---

### Koan 07

**Updates, inserts and deletes**

Learn:

* how to do updates, inserts and deletes
  * in xml mapping files
  * annotation driven in Java Mapper interfaces
* how to use the `selectKey` feature of MyBatis to dynamically create a new key for a domain object being inserted into the database
* the effect of calling `rollback`, `commit` or neither on the session object when executing DML statements via MyBatis

---

### Koan 08

**`<sql>` and dynamic string substitution**

Learn:

* how to use the `<sql>` MyBatis element to keep your SQL DRY ("don't repeat yourself")
* how to do dynamic string substitution in sections of SQL that are not bound variables to PreparedStatements

---

### Koan 09

**ResultMaps, part 1**

Learn:

* how to use a ResultMap for a single table query
* how to use a ResultMap for a multiple table query, using the "association" feature
* how to use ResultMaps as part of a "nested select" (the N+1 approach)

---

### Koan 10

**ResultMaps, part 2**

Learn:

* how to use a ResultMap for a multiple table query, using the "collection" feature

---

### Koan 11

**TypeHandlers**

Learn:

* How to use a MyBatis TypeHandler in order to set a domain specific object type as a field in a mapped object
  * Learn to set a TypeHandler for all object type (for any query that uses it)
  * Learn to set a TypeHandler for a specify field/column of a specify mapped query

---

### Koan 12

**MyBatis and Immutable Objects**

Learn:

* How to use a MyBatis with immutable objects that have no setter fields
  * Learn to set a TypeHandler for all object type (for any query that uses it)
  * Learn to set a TypeHandler for a specify field/column of a specify mapped query

---

# Current Status

As of last writing in May 2012, these koans are only just started and not complete.  Feel free to grab them and try them out.  Suggestions for improvements are welcome.

