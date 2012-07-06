package net.thornydev.mybatis.koan.koan22;

public interface Koan22Mapper {

  Category getCategoryByName(String catName);
  FilmWithCategories getFilmById(int id);
  int deleteAllCategoriesForFilm(FilmWithCategories fwc);
  // TODO: you not allowed to change the signature of this interface
  //       but you might want to annotate it to help extract
  //       necessary values in the xml mapping code
  int addCategoryForFilm(FilmWithCategories fwc, Category c);

}
