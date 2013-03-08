# Koan Topic Overview

Over time the number of koans has grown to be large. Some of them examine various edge cases of the framework or special usage scenarios.  If you want to be focus on just the "core" to learn the basics of MyBatis, I recommend doing koans 1-12, 15-18 and 21.

----

# Koan Topics

### Koan 01

**Configuration Koan**

Learn:

* how to configure a MyBatis config file, including a shared properties that will used by the MyBatis config file 
* how to wire up your Java code to use the MyBatis config file
* how to create a SqlSessionFactory

----

### Koan 02

**Xml mapper file**

Learn:

* how to reference a mapper file from a MyBatis config file
* how to set up a SQL mapping in a mapper file
* how to create a `SqlSession`
* how to call the SQL mapping and get data from the database via MyBatis
* the basic types of datastructures that MyBatis can return and how to specify them

----

### Koan 03

**Domain objects**

Learn:

* how to create a mapper xml file that returns a domain object, rather than a generic data structure
* how to return a list or map of domain objects from a mapped SQL query
* how to set up a TypeAlias in the MyBatis config file

----

### Koan 04

**Domain objects, using underscore to camelCase mapping**

Learn:

* how to tell MyBatis to convert underscores to camelCase to auto-map database column names to object property names

----

### Koan 05

**Mapper interface**

Learn:

* how to create and use a Java Mapper interface to do the same type of queries in the previous koans

----

### Koan 06

**Multiple Arguments and RowBounds**

Learn:

* how to create and use a Java Mapper interface to do the same type of queries in the previous koans
* how to return a simple numeric value from a mapped SQL query
* how to pass in multiple arguments to a mapped query (including using annotations)
* how to use a MyBatis RowBounds object to limit the number of results returned

----

### Koan 07

**Annotation driven mappings**

Learn:

* how to do annotation driven mappings
* how to specify a mapper in the config file when you don't have an XML mapping file

----

### Koan 08

**Updates, inserts and deletes**

Learn:

* how to do updates, inserts and deletes
  * in xml mapping files
  * annotation driven in Java Mapper interfaces
* how to use the `selectKey` feature of MyBatis to dynamically create a new key for a domain object being inserted into the database
* the effect of calling `rollback`, `commit` or neither on the session object when executing DML statements via MyBatis

----

### Koan 09

**`<sql>` and dynamic string substitution**

Learn:

* how to use the `<sql>` MyBatis element to keep your SQL DRY ("don't repeat yourself")
* how to do dynamic string substitution in sections of SQL that are not bound variables to PreparedStatements

----

### Koan 10

**ResultMaps, part 1**

Learn:

* how to use a ResultMap for a single table query
* how to use a ResultMap for a multiple table query, using the "association" feature
* how to use ResultMaps as part of a "nested select" (the N+1 approach)

----

### Koan 11

**ResultMaps, part 2**

Learn:

* how to use a ResultMap for a multiple table query, using the "collection" feature

----

### Koan 12

**TypeHandlers**

Learn:

* How to use a MyBatis TypeHandler in order to set a domain specific object type as a field in a mapped object
  * Learn to set a TypeHandler for all object type (for any query that uses it)
  * Learn to set a TypeHandler for a specify field/column of a specify mapped query

----

### Koan 13

**MyBatis and Immutable Objects, part 1**

Learn:

* How to use a MyBatis with immutable objects that have no setter fields
  * Learn to set a TypeHandler for all object type (for any query that uses it)
  * Learn to set a TypeHandler for a specify field/column of a specify mapped query
* How to use multiple mapper files that reference each other

----

### Koan 14

**MyBatis and Immutable Objects, part 2**

Learn:

* How to use a MyBatis with immutable objects that have no setter fields and without having to directly map to long constructor param lists by implementing MyBatis' ObjectFactory
* How to use a ResultHandler to do additional things with the objects created by MyBatis (or your ObjectFactory), such as filtering what results are returned

----

### Koan 15

**Database inserts, revisited**

Learn:

* How to use a TypeHandler to insert into a table where its mapped domain object depends on other domain objects
* How to insert null values into a nullable columns in the database

----

### Koan 16

**Calling stored procedures via XML mappings**

Learn:

* How to call a stored procedure and stored functions from MyBatis using XML mapping
* How to handle both IN and OUT parameter types

----

### Koan 17

**Calling stored procedures via Java annotation mappings**

Learn:

* How to call a stored procedure and stored functions from MyBatis using Java annotations

----

### Koan 18

**Statement Builders**

Learn:

* How to use the MyBatis SelectBuilder "DSL" to generate SQL statements
* How to use the MyBatis SqlBuilder "DSL" to generate SQL insert, update and delete statements
* How to use the MyBatis SqlBuilder "DSL" to generate compound SQL (subselects)

**[Update]**: In mybatis-3.2, SQLBuilder has been deprecated, so Koan18 is now vestigial.  However, I will leave it in for a while longer in case people are still using older versions and depending on SQLBuilder.  You should stop using it. See the MyBatis documentation for more details.


----

### Koan 19

**Using the Null object pattern with MyBatis**

Learn:

* How to create a Null object when a null value is returned from a database query

----

### Koan 20

**Prepared vs. non-prepared statements in MyBatis**

Learn:

* The difference between the #{} variable notation and ${}
* How to use the ${} notation with a domain object
* How to deal with an enum type in your sakila database (if using PostgreSQL or MySQL)

----

### Koan 21

**Dynamic SQL**

Learn:

* How to use the `<if>` conditional to build a SQL statement
* How to most effectively build a compound where and set clause using the `<where>` and `<set>` features
* How to loop over lists when building SQL using `<foreach>`

----

### Koan 22

**(Slightly) More Complicated Mappings**

Learn:

* How to do an insert that takes two domain objects and extract the appropriate ids from them in the MyBatis mapping
* How to map a three table join

----

### Koan 23

**Using Java Enums with MyBatis**

Learn:

* How to map a Java Enum to the result of a database query
* How to map a Java Enum to a database DML (insert/update/delete)

----

### Koan 24

**Get MyBatis to return non-default Collection Types**

Learn:

* How to have MyBatis return a CopyOnWriteArrayList instead of an ArrayList from a query
* How to have a case-insensitive Map returned instead of a regular HashMap from a query

----

### Koan 25

**Map/shoehorn multiple database columns into one Java field**

Learn:

* How to take a query like `select foo1, foo2, foo3` and create a domain object that has an foo array, rather than three separate fields, one for each foo.

----

### Koan 26

**Return Maps from MyBatis where the key is the id (primary key) and the value is either a domain object or a particular string from the table you are querying from**

Learn:

* How to return `Map<Integer,DomainObject>` instead of `List<Map<String,Object>>`
* How to return `Map<Integer,String>` instead of `List<Map<String,Object>>` from a query like: `select id, foo` and the map is a mapping of id to foo values.
