package net.thornydev.mybatis.test.koan23;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

public interface Koan23Mapper {

  Category getCategoryByName(String catName);

  FilmWithCategories getFilmById(int id);

  FilmWithCategories getFilmWithRatingById(int id);

  int updateFilmRating(FilmWithCategories fwc);

  int deleteAllCategoriesForFilm(FilmWithCategories fwc);

  int addCategoryForFilm(@Param("film") FilmWithCategories fwc,
                         @Param("category") Category c,
                         @Param("date") Date date);

}
