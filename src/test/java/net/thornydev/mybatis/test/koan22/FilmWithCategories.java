package net.thornydev.mybatis.test.koan22;

import java.util.ArrayList;
import java.util.List;

public class FilmWithCategories {

  private int id;
  private String title;

  private List<Category> categories;

  public int getId() {
    return id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(final List<Category> categories) {
    this.categories = categories;
  }

  public void addCategory(final Category c) {
    if (categories == null)
      categories = new ArrayList<Category>();
    categories.add(c);
  }

}
