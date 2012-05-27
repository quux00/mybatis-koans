package net.thornydev.mybatis.koan.koan18;

import static org.junit.Assert.*;
import static org.apache.ibatis.jdbc.SqlBuilder.*;

import org.junit.Test;

// In Koan18 we learn to use the SelectBuilder/SqlBuilder feature of MyBatis.
// In this koan we do not need to query the database.  Instead we are just
// generating SQL strings, so no mapper files are required.
// 
// To pass this koan you must do all the TODOs in this koan
public class Koan18 {

	@Test
	public void learnToBuildSimpleSelectStatement() {
		// TODO: build a simple select statement that will pass the assert below
		String sql = SQL();

		assertEquals("SELECT * FROM customer", normalize(sql));
	}

	@Test
	public void learnToBuildComplexSelectStatement() {
		// TODO: build a SQL statement that will pass the assert below
		String sql = SQL();
		
		String expectedSql = 
				"SELECT l.language_id, l.name, l.last_update, " + 
				"f.film_id, f.title, f.last_update as film_upd, f.special_features " +
				"FROM language l " +
				"LEFT OUTER JOIN film f on f.language_id = l.language_id " +
				"WHERE (l.language_id = #{id}) " + 
				"ORDER BY f.film_id DESC";		
		
		assertEquals(normalize(expectedSql), normalize(sql));
	}

	@Test
	public void learnToBuildInsertStatement() {
		// TODO: build a SQL insert statement that will pass the assert below
		String sql = SQL();
		
		String expectedSql = 
				"INSERT INTO country (country_id, country) " +
				"VALUES (#{id}, #{country})";	
		assertEquals(normalize(expectedSql), normalize(sql));
	}

	@Test
	public void learnToBuildUpdateStatement() {
		// TODO: build a SQL update statement that will pass the assert below
		String sql = SQL();
		
		String expectedSql = 
				"UPDATE address SET address2 = 'foo', city_id = 22 " +
				"WHERE (address_id = #{id})";	
		assertEquals(normalize(expectedSql), normalize(sql));
	}
	
	@Test
	public void learnToBuildCompoundStatementWithSubSelect() {
		// TODO: build a SQL update statement that has a subselect 
		//       in order to pass the assert below
		String sql = SQL();
		
		String expectedSql = 
				"UPDATE address SET postal_code = '55555' " + 
			    "WHERE (city_id = (SELECT city_id FROM city WHERE (city = 'Grand Prairie')))";
		assertEquals(normalize(expectedSql), normalize(sql));
	}
	
	/* ---[ private helper methods ]--- */

	public String normalize(final String sql) {
		return sql.replaceAll("\\r?\\n", " ").replaceAll("\\s+", " ");
	}
}
