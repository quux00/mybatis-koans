package net.thornydev.mybatis.koan.koan22;

import java.util.ArrayList;
import java.util.List;

/**
 * FilmWithCategories is an abbreviated representation of the Film
 * table. We only focus on a few properties here to simplify things.
 * The key difference from the domain.Film class is that this one
 * has a has_many relationship to categories represented.
 */
public class FilmWithCategories {
  private int id;
  private String title;
  private List<Category> categories;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  public void addCategory(Category c) {
    if (categories == null) categories = new ArrayList<Category>();
    categories.add(c);
  }
}
