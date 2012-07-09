package net.thornydev.mybatis.test.koan22;

import org.apache.ibatis.annotations.Param;

public interface Koan22Mapper {

	Category getCategoryByName(String catName);

	FilmWithCategories getFilmById(int id);

	int deleteAllCategoriesForFilm(FilmWithCategories fwc);

	int addCategoryForFilm(@Param("film") FilmWithCategories fwc,
			@Param("category") Category c);

}
