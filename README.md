**Note: IN PROGRESS!** This repo is a fork of the [original mybatis-koans repo](https://github.com/midpeter444/mybatis-koans) and is in progress to add maven support.  When it is completed and ready-to-go, it will be merged back into the original mybatis-koans repo.  For now we recommended that you use the original mybatis-koans repo unless you want to help us finish this one.

# MyBatis Koans

A [koan](http://en.wikipedia.org/wiki/K%C5%8Dan) is a question or statement to be meditated upon in order to improve and test a student's progress. Among programmers, software koans have become a clever way to learn a software language or tool.  As the [Ruby koan website](http://rubykoans.com/) says: "The Koans walk you along the path to enlightenment" -- in this case to learn and practice with the [MyBatis 3](http://www.mybatis.org/core/) data mapper framework.

A software koan comes in the form of a broken unit test that you must fix to get it to pass, usually by filling in the blanks or entire missing sections.  The koan is intended to teach one or a small set of cohesive features about the language or tool being studied.

This set of koans focus on the excellent [MyBatis data mapper framework](http://code.google.com/p/mybatis/wiki/Welcome?tm=6) for Java.  MyBatis 3 has made significant changes from the previous iBATIS framework and these koans are designed to help you learn how MyBatis 3 works.

The structure of these koans is inspired by the challenging and informative [Neo4j koans](https://github.com/jimwebber/neo4j-tutorial) by Jim Webber and colleagues.

---

# Directory of Contents

* [Overview](#Overview)
  * [Prerequisites](#Prerequisites)
  * [Setup overview](#SetUpOverview)
  * [Main steps to doing the koans](#MainSteps)
* [Setup](#Setup)
  * [Clone the koans repo](#cloneRepo)
    * [The koan directory structure](#koanDirStr)
  * [Setup the sakila database](#createSakila)
  * [Build Tools](#getDeps)
    * [Maven](#getDepsMaven)
    * [Ant](#getDepsAnt)
  * [Run completed koans](#runComp)
* [Do the koans](#doKoans)
  * [Test your koans](#runMainKoans)
* [Directory of Koans](#koanDirectory)
* [A Note on Solutions](#noteOnSolutions)
* [A Note on Best Practices](#noteOnBestPractices)
* [Current Status](#currentStatus)
* [Contributors](#contributors)

---

<a name="Overview"></a>
# Overview

To do the koans you will need a relational database, the Java JDK, JDBC drivers, JUnit, the MyBatis Persistence Framework, a Java build tool and an editor/IDE.  The mybatis-koan setup tries be flexible to allow you to use your build tool and database of choice.

The koans come twice - once in "uncompleted" form and once in "completed" form.

The "uncompleted" koans are the ones you will fill in. They are JUnit 4 tests in the `src/main/java/net/thornydev/mybatis/koan/koanXX` directories.  Each koan has its own directory (and thus package name) in order to have separate MyBatis config files to exercise different aspects of the MyBatis data mapper framework.

The completed koans are there for reference in case you get stuck and need to see the solution and also to test that you have your environment set up.  They are in the `src/test/java/net/thornydev/mybatis/koan/koanXX` directories.

_Note_: where different solutions were required between MySQL, PostgreSQL and/or H2, we have created additional subdirectories named after the database.

While MyBatis can be used with [other JVM languages](http://www.fdmtech.org/2011/12/mybatis-for-scala-1-0-beta-released), these koans are all in pure Java.

To do these koans, the [sakila](http://dev.mysql.com/doc/sakila/en/index.html) example database was chosen.  It is a sample database schema and dataset originally built in MySQL that has been created for [many other relational databases](http://code.google.com/p/sakila-sample-database-ports/). In addition to one-to-many and many-to-many relationships for us to model, it has stored procedures and stored functions that we will learn to access via MyBatis.



<a name="Prerequisites"></a>

## Prerequisites

More specifically you must have:

* An understanding of relational databases and SQL
* Experience programming in Java
* The Java JDK installed (preferably version 6 or higher)
* Either:
  * [Maven](http://maven.apache.org/download.html) (preferably version 3 or higher), or
  * [Ant](http://ant.apache.org/) (preferably 1.8 or higher)
* An editor or IDE (We have tested with Eclipse only)
  * You may also want to have the [m2e Eclipse plugin](http://www.eclipse.org/m2e/) installed if you plan to use maven  


<a name="SetUpOverview"></a>

## Set up overview

Unfortunately, the setup for the MyBatis koans is not as simple as the Ruby koans, since you have to set up and configure a database, load a standard dataset, and configure the MyBatis system for it.  So you'll need to roll up your sleeves a bit before you can get started meditating on the koans themselves.

However, we now provide a fast-track: using maven and the H2 database is the fastest way to get going.  Using maven with PostgreSQL or MySQL requires only a little more work.

<a name="MainSteps"></a>

## Main steps to doing the koans

To give you sense the flow here are the steps for getting set up and then working through the koans. In the sections that follow we provide more details on these steps.

**[Step 0](#chooseDatabase):**  Choose the database server and build tool you want to use
**Step 1:**  Install any missing prerequisites listed above

**[Step 2](#cloneRepo):**  Clone or download the mybatis-koans from GitHub and take a look at the directory structure

**[Step 3](#createSakila):**  Create the sakila database and load the dataset (not necessary for H2)

**[Step 4](#studySakila):**  Study the provided sakila database diagrams to get familiar with it

**[Step 5](#getDeps):**  Run maven to download the dependencies or, if using ant, download the dependencies manually and install them in the koan lib directory

**[Step 6](#runComp):**  Run a few of the completed koans to make sure everything is working on your system

**[Step 7](#eclipse):**  Start working on the koans in your editor / IDE of choice

**[Step 8](#runMainKoans):**  Run the koan you are working on to see if it passes the tests

**Step 9:**  If you are having trouble completing the koan, research your options online, using the [MyBatis User Guide](http://www.mybatis.org/core/), or *as a last resort* take a peek at the completed koan we provide.  Ideally you will look at the completed koan only after yours is done to see if you came up with a different solution.

**Step 10:**  Repeat Steps 8 and 9 until all the koans are finished.

**Step 11:**  Think of additional koans that exercise MyBatis features you'd like to learn better. 

**Step 12:**  Fork this repo on GitHub, write your own and make a pull request to add back to the MyBatis community.

<br />
<a name="Setup"></a>

# Setup

We provide three ways to run the koans (that we have tested):

1. Load them into Eclipse (as a maven project) and run them one at a time using JUnit built into Eclipse
2. Run/build them as pure maven targets -- either within an IDE or run from the command line
3. Run/build them as pure ant targets  -- either within an IDE or run from the command line

We have completed the koans for three databases:

1. [PostgreSQL](http://www.postgresql.org/)
2. [MySQL](http://www.mysql.com/)
3. [H2](http://www.h2database.com/html/main.html), a pure Java database

You are welcome to do the koans a different database and contribute solutions back.

So you will need to decide what options you'd like to take.  The closest thing to a "push-button" solution is to use maven and H2.  To get the koans set up for that route, the only thing you will need to have pre-installed is maven and Java.  Using any other database or ant will require additional setup, as described below.


<a name="chooseDatabase"></a>

### Database

* If you are going to use H2 as your database, everything you need comes with the koan download
* If you are going to use PostgreSQL or MySQL, you will need to install the database server (and client) software and get it configured to have at least one user with a username and password.  You will NOT need to download the sakila database schema and dataset - they come with the koans - but you will need to load the sakila schema and dataset.
* If you are going to use some other database, you will need to:
  * install that database server and client
  * download and create the sakila schema and load the dataset
     ** recommended site: [http://code.google.com/p/sakila-sample-database-ports/](http://code.google.com/p/sakila-sample-database-ports/)
  * download and set up the JDBC driver for that database, or add that JDBC dependency to the maven pom.xml file, if you are going to use maven

<br />
<a name="cloneRepo"></a>

## Clone the koans repo (or download it)

If you have [git](http://git-scm.com/downloads) installed, clone this repo:

    git clone git@github.com:midpeter444/mybatis-koans.git

If you don't have git, you can just [download a zip or tarball](https://github.com/midpeter444/mybatis-koans/downloads) of the koans.

<a name="koanDirStr"></a>

### The koan directory structure

There are tree main directories.

**db:** 
The sakila database files are here. For H2, we provide the actual binary db file.  For MySQL and PostgreSQL, we provide the SQL files for the schema and default dataset.  In addition, documentation of the schema is available in the `postgesql/doc` and `mysql/doc` subdirectories.

**lib:** 
This is where to put jars (or links to jars) if you will be using ant.  If you use maven, then you can ignore this directory.

**src:** 
The koan source code is organized using maven's default directory structure.  Maven splits src into `main` and `test`.  The koans are organized such that the incomplete koans that you will work on are in `main` and the completed koans are in `test`.

You can still use ant if you don't want to use maven.  The ant build.xml file is set up to handle this directory structure.

<br />
<a name="createSakila"></a>

## Setup the sakila database

### H2

Nothing to do here - the binary database is provided in the `db/h2` directory.

### PostgreSQL or MySQL : Some assembly required

If you want to use a more enterprise-strength database, then you will need to not only install those database servers first, but also populate them with the sakila schema and dataset.  I provide instructions on how to do this from the command line (tested on a Linux machine):

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

### Other database servers

For other databases, get the sakila schema and dataset from [here](http://code.google.com/p/sakila-sample-database-ports/).


<br />
<a name="studySakila""></a>

### Visual aids for the sakila schema

In the `db/postgesql/doc` and `db/mysql/doc` directories, I have provided some analysis documents and visualizations reverse engineered from the PostgreSQL and MySQL sakila databases.

View PNG files in the `db/postgesql/doc/dbvis` or `db/mysql/doc/dbvis` directories to see physical data models and relationships between tables.  These were generated using the free version of [DbVisualizer](http://www.dbvis.com/).

For a deeper analysis open the `index.html` in either `doc/schemaSpy` directory.  These were generated using the open source [SchemaSpy](http://schemaspy.sourceforge.net/) tool.

Finally, you can also read through the documentation that MySQL provides for the sakila database: [http://dev.mysql.com/doc/sakila/en/index.html](http://dev.mysql.com/doc/sakila/en/index.html)


<br />
<a name="getDeps"></a>

## Build Tools

<a name="getDepsMaven"></a>

### Maven: I just want to get going!

If you have the prerequisites in place, the fastest way to get going is to use maven.  You can use the [Eclipse m2e plugin](http://www.eclipse.org/m2e/) if you want the best support for doing it all in Eclipse.  The instructions below assume you don't have m2e, but will ultimately work the same with it.

H2 is the default database in the pom.  If you want to use MySQL or PostgreSQL, uncomment that section of the pom dependencies to get their JDBC driver.  If you want to use another database, add its JDBC driver to the maven pom.

#### From the command line

From the top dir of the koans, type:

    $ mvn compile

This will download all the dependencies for running the mybatis koans with H2 and then compile both the incomplete and completed koans.  (**TODO: true??**)

If you see no errors while downloading and compiling, then try [running a couple of the completed koans](#runComp) in the "test" directory to see if everything seems to be working.


<a name="getDepsAnt"></a>

### I prefer Apache Ant

When using the ant for the koans, you will not have any dependency management system. You will need to put (or symlink to) the jar file dependencies in the `mybatis-koans/lib` directory.  For example, here is what my `lib` directory looks like:

    $ ls -l
    lrwxrwxrwx 1 (...) junit4.jar -> /home/midpeter444/java/lib/junit4.jar
    lrwxrwxrwx 1 (...) mybatis.jar -> /home/midpeter444/java/lib/mybatis.jar
    lrwxrwxrwx 1 (...) mysql-connector-java.jar -> /home/midpeter444/java/lib/mysql-connector-java.jar
    lrwxrwxrwx 1 (...) postgresql.jar -> /home/midpeter444/java/lib/postgresql.jar


To run with H2, you will also need to have: (**TODO: Test all these - true?**)

* Apache Commons IO:  [http://commons.apache.org/io/download_io.cgi](http://commons.apache.org/io/download_io.cgi)
* The H2 db jar: [http://www.h2database.com/html/download.html](http://www.h2database.com/html/download.html)
* The logback-class and logback-core jars: [http://logback.qos.ch/download.html](http://logback.qos.ch/download.html)
* The slf4j-api jar: [http://www.slf4j.org/download.html](http://www.slf4j.org/download.html)

So in the end you'll need to have the lib directory have those jars or links to those jars, such as I have here:

    $ ls -l
    lrwxrwxrwx 1 (...) junit4.jar -> /home/midpeter444/java/lib/junit4.jar
    lrwxrwxrwx 1 (...) mybatis.jar -> /home/midpeter444/java/lib/mybatis.jar
    lrwxrwxrwx 1 (...) mysql-connector-java.jar -> /home/midpeter444/java/lib/mysql-connector-java.jar
    lrwxrwxrwx 1 (...) postgresql.jar -> /home/midpeter444/java/lib/postgresql.jar
    lrwxrwxrwx 1 (...) slf4j-api.jar -> /home/midpeter444/java/lib/slf4j-1.6.6/slf4j-api-1.6.6.jar
    lrwxrwxrwx 1 (...) commons-io.jar -> /home/midpeter444/java/lib/commons-io-2.4/commons-io-2.4.jar
    lrwxrwxrwx 1 (...) h2.jar -> /home/midpeter444/java/lib/h2/bin/h2-1.3.166.jar
    lrwxrwxrwx 1 (...) logback-classic.jar -> /home/midpeter444/java/lib/h2/bin/logback-classic-1.0.6.jar
    lrwxrwxrwx 1 (...) logback-core.jar -> /home/midpeter444/java/lib/h2/bin/logback-core-1.0.6.jar


<br />
<a name="runComp"></a>

### Run the completed koans

Before you try running the completed koans, modify the `src/test/java/net/thornydev/mybatis/test/config.properties` file to set up the database driver, and username and password to use.  (If you are using H2, then you can leave the defaults.)

#### maven

To run individual completed koans from the command line, use this syntax:

    $ mvn clean verify -P run-comp-koans-h2 -D koanName=Koan02

This says to use the H2 database and run Koan02.  Change the koan name to run different ones.  To use mysql or postgresql, change the suffix of the -P argument, like so:

    $ mvn clean verify -P run-comp-koans-pg -D koanName=Koan03
    $ mvn clean verify -P run-comp-koans-mysql -D koanName=Koan04

To run all the tests for a given database, leave off the -D target:

    $ mvn clean verify -P run-comp-koans-h2

Ideally, among all the verbiage that maven spits out, you will see output that includes this:

    [INFO] H2 server spawned at tcp://localhost:9092
    [INFO] 
    [INFO] --- maven-surefire-plugin:2.12:test (test-koans) @ sql_mybatis-koans ---
    [INFO] Surefire report directory: /home/midpeter444/databases/mybatis/sql_mybatis-koans/target/surefire-reports
    [INFO] Using configured provider org.apache.maven.surefire.junitcore.JUnitCoreProvider
    
    -------------------------------------------------------
     T E S T S
    -------------------------------------------------------
    Concurrency config is parallel='none', perCoreThreadCount=true, threadCount=2, useUnlimitedThreads=false
    Running net.thornydev.mybatis.test.koan02.Koan02
    Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.749 sec
    learnToQueryViaXmlMapperReturningHashMap(net.thornydev.mybatis.test.koan02.Koan02)  Time elapsed: 0.704 seclearnToQueryMapperReturningHashMapWithParameterInput(net.thornydev.mybatis.test.koan02.Koan02)  Time elapsed: 0.009 seclearnToQueryViaXmlMapperReturningListOfHashMaps(net.thornydev.mybatis.test.koan02.Koan02)  Time elapsed: 0.036 sec
    Results :
    
    Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
    
    [INFO] 
    [INFO] --- h2-maven-plugin:1.0:stop (h2-finish) @ sql_mybatis-koans ---
    [INFO] H2 server stopped
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------

Note that for the H2 version it starts ("spawns") the H2 database, runs the koan (Koan02 in this case), which has three test targets and then stops the H2 server.  If you choose mysql or postgresql it will just run the koan, **not** start and stop the db server.

#### ant

Make sure you have ant in your PATH. In the mybatis-koans directory, run `ant -p` to make sure ant is working and to see the available targets.

To run individual completed koans from the command line, use this syntax:

    $ ant comp-koan04

You can also run all the koans for the database you've chosen with:

    $ ant comp-all-xx  # replace xx with the database name
    $ ant comp-all-h2  # example when using H2

_Note_: If you are using H2, you need to **first** run the ant target `runH2` in one window and then run the koans in another window.

##### Tweaking the ant settings

By default, the koan test output will be written to the console only. If you want the output to be written to a plain text formatted file in the top directory called `TEST-net.thornydev.mybatis.koan.koanXX.KoanXX.txt`, set the `log.koan.output.to.file` property to "true" in the build.xml file, like so:

    <property name="log.koan.output.to.file" value="true"/>

MyBatis uses log4j, so a log4j.properties file is provided in the src directory.  By default it will log WARN level messages and higher to the console only.  If you want other settings, edit this file.

**TODO: need to check if this is still right with the new setup**


<br />
<a name="eclipse"></a>

### Do the Koans in Eclipse

Load the koans into Eclipse as a maven project.  From there you can run them either from maven targets, ant targets or by running each with the JUnit built into Eclipse.

**OLD INSTRUCTIONS**

Open Eclipse and start a new Java Project.  Give it the project name "mybatis-koans" and uncheck the "Use default location" option.  Browse to the mybatis-koans directory where you downloaded the koans from GitHub.

Set up your Java Build Path to have the mybatis-3.x.x jar file, the JDBC library of your choice, and JUnit 4.

Navigate to Koan01.java in the net.thornydev.mybatis.koan.koan1 package.  Run it as a JUnit test by right-clicking in the editing screen and choose Run As > JUnit Test.  It should fail.  If it passes, then you are probably in the completed koans section.

Read the instructions in the Koan.  You will have a series of TODOs to complete.  Fill those in, using the [MyBatis User Guide](http://www.mybatis.org/core/) as help until the koan passes.  Then move to the next one.



**Need more details here??**
==> Need to document how to start H2 server from Eclipse


<a name="doKoans"></a>

# Do the koans

Once you are satisfied that the completed koans work, you should then begin to work on the incomplete koans. Start with Koan01 in the `src/main/java/net/thornydev/mybatis/koan/koan01` directory. 

Before you start, modify the `src/main/java/net/thornydev/mybatis/config.properties` file to set up the database driver, and username and password to use.  (If you are using H2, then you can leave the defaults.)

Whenever you start a new koan, open the file KoanXX.java and read the overview and instructions in the javadoc comments at the top of the file. The goal is to fill in all the TODOs that get the tests already set up to pass.

<a name="runMainKoans"></a>

## Test your koans

To run the koans you are completing, do:

    $ mvn clean verify -P run-koans-h2 -D koanName=Koan01

or

    $ ant koan01   # remember to do 'ant runH2' first in another console if using H2

or run them from within Eclipse as JUnit tests.

**TODO: the above target doesn't exist --- need to build**


---

<a name="koanDirectory"></a>

# Directory of Koans

See the [directory of koans](sql_mybatis-koans/KOANS.md) for a description of what each koan tests.

---

<a name="noteOnSolutions"></a>

# A Note on Solutions

For many koans, there are probably many ways to make it work (even within the constraints put in place to exercise a given feature of MyBatis). If you have an alternative solution that will help others see the possibilities, feel free to send a pull request to get it added to the repo.

---

<a name="noteOnBestPractices"></a>

# A Note on Best Practices

As you go through the koans, you'll see that I change styles/idioms from time to time. Sometimes I start a session for each test, then later not.  Sometimes I use mappers with a Java interface, sometimes not.  Sometimes I turn camelCase mapping on, sometimes not. These koans are not intended to recommend best practice, but rather to see the variations of possibilities that MyBatis 3 allows. I mix it up so you can be reminded of these variations. You should decide, in conjunction with the recommendations in the User Guide and reading other tutorials and code examples, on what is best practice for your code base.

---

<a name="currentStatus"></a>

# Current Status

The original koans were released in May 2012.  In July, [Andrei Pozolotin](https://github.com/carrot-garden) contributed some changes to make the koans work with maven, so we are releasing the revised setup for the koans.

So far, we have tested them carefully with MyBatis-3.1.1 using H2-1.3.168, PostgreSQL 9.1.3 and MySQL 5.5 on Linux and using H2 on Windows 7. If you try them with other combinations and have problems, let me know.  (Suggested patches are welcome.)

Right now these koans focus only on the MyBatis Persistence Framework. Future koans could also focus on the other "modules" of MyBatis, such as Schema Migrations, the MyBatis code generator and MyBatis-Spring integration, Scala integration and Caches (such as EHCache and memcached).

Even within the Persistence Framework, these koans do **not** cover some functionality in MyBatis 3, such as:

* caching
* altering the defaults of a wide variety of settings, such as "useGeneratedKeys", turning off "aggressiveLazyLoading", or altering "autoMappingBehavior"
* low-level plugins, such as Executor, ParameterHandler, ResultSetHandler, StatementHandler
* implementing our own TransactionFactory
* JNDI lookups 
* databaseIdProvider

If you have found any of these to be really useful, feel free to suggest a new koan or write one yourself to add it here with a [pull request](https://help.github.com/articles/using-pull-requests/).


---

<a name="contributors"></a>

# Contributors

* [Andrei Pozolotin](https://github.com/carrot-garden) did significant work to revamp the structure of the koans to be maven compatible and wrote much of the maven setup to get things going - thanks!
* [Michael Kolakowski](https://github.com/mkolakow) fixed some typos on the original README
