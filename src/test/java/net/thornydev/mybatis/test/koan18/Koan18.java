package net.thornydev.mybatis.test.koan18;

import static org.apache.ibatis.jdbc.SqlBuilder.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class Koan18 {

	@Test
	public void learnToBuildSimpleSelectStatement() {

		BEGIN();
		SELECT("*");
		FROM("customer");
		final String sql = SQL();

		assertEquals("SELECT * FROM customer", normalize(sql));

	}

	@Test
	public void learnToBuildComplexSelectStatement() {

		BEGIN();
		SELECT("l.language_id, l.name, l.last_update");
		SELECT("f.film_id, f.title, f.last_update as film_upd, f.special_features");
		FROM("language l");
		LEFT_OUTER_JOIN("film f on f.language_id = l.language_id");
		WHERE("l.language_id = #{id}");
		ORDER_BY("f.film_id DESC");
		final String sql = SQL();

		final String expectedSql = "SELECT l.language_id, l.name, l.last_update, "
				+ "f.film_id, f.title, f.last_update as film_upd, f.special_features "
				+ "FROM language l "
				+ "LEFT OUTER JOIN film f on f.language_id = l.language_id "
				+ "WHERE (l.language_id = #{id}) " + "ORDER BY f.film_id DESC";

		assertEquals(normalize(expectedSql), normalize(sql));

	}

	@Test
	public void learnToBuildInsertStatement() {

		BEGIN();
		INSERT_INTO("country");
		VALUES("country_id, country", "#{id}, #{country}");
		final String sql = SQL();

		final String expectedSql = "INSERT INTO country (country_id, country) "
				+ "VALUES (#{id}, #{country})";

		assertEquals(normalize(expectedSql), normalize(sql));

	}

	@Test
	public void learnToBuildUpdateStatement() {

		BEGIN();
		UPDATE("address");
		SET("address2 = 'foo'");
		SET("city_id = 22");
		WHERE("address_id = #{id}");

		final String sql = SQL();

		final String expectedSql = "UPDATE address SET address2 = 'foo', city_id = 22 "
				+ "WHERE (address_id = #{id})";
		assertEquals(normalize(expectedSql), normalize(sql));
	}

	@Test
	public void learnToBuildCompoundStatementWithSubSelect() {

		BEGIN();
		SELECT("city_id");
		FROM("city");
		WHERE("city = 'Grand Prairie'");
		final String subSelect = SQL();

		RESET();
		UPDATE("address");
		SET("postal_code = '55555'");
		WHERE("city_id = (" + subSelect + ")");

		final String sql = SQL();

		final String expectedSql = "UPDATE address SET postal_code = '55555' "
				+ "WHERE (city_id = (SELECT city_id FROM city WHERE (city = 'Grand Prairie')))";

		assertEquals(normalize(expectedSql), normalize(sql));

	}

	/* ---[ private helper methods ]--- */

	public String normalize(final String sql) {
		return sql.replaceAll("\\r?\\n", " ").replaceAll("\\s+", " ");
	}

}
