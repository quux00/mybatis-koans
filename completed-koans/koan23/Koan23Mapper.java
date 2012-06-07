package net.thornydev.mybatis.koan.koan23;

import net.thornydev.mybatis.koan.koan23.FilmWithCategories;

import org.apache.ibatis.annotations.Param;

public interface Koan23Mapper {
  
  Category getCategoryByName(String catName);
  FilmWithCategories getFilmById(int id);
  FilmWithCategories getFilmWithRatingById(int id);
  int updateFilmRating(FilmWithCategories fwc);
  int deleteAllCategoriesForFilm(FilmWithCategories fwc);
  int addCategoryForFilm(@Param("film") FilmWithCategories fwc, @Param("category") Category c);

}
