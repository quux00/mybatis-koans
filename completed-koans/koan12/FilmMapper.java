package net.thornydev.mybatis.koan.koan12;

import net.thornydev.mybatis.koan.domain.Film;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface FilmMapper {
  @Update("UPDATE film SET release_year = #{releaseYear}, replacement_cost = #{replacementCost} WHERE film_id = #{id}")
  int updateYearAndReplacementCost(Film f);

  @Select("SELECT film_id AS id, title, release_year AS releaseYear, replacement_cost AS replacementCost FROM film WHERE film_id = #{id}")
  Film getFilmById(int id);
}
