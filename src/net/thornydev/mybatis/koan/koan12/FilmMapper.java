package net.thornydev.mybatis.koan.koan12;

import net.thornydev.mybatis.koan.domain.Film;

import org.apache.ibatis.annotations.Select;

public interface FilmMapper {
	// TODO: create an annotated mapping to update the release_year and replacement_cost fields via a Film object
	int updateYearAndReplacementCost(Film f);

	@Select("SELECT film_id AS id, title, release_year AS releaseYear, replacement_cost AS replacementCost FROM film WHERE film_id = #{id}")
	Film getFilmById(int id);
}
