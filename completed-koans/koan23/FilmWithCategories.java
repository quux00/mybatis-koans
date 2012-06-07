package net.thornydev.mybatis.koan.koan23;

import java.util.ArrayList;
import java.util.List;

public class FilmWithCategories {
  private int id;
  private String title;
  private Rating rating;
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

  public Rating getRating() {
    return rating;
  }

  public void setRating(Rating rating) {
    this.rating = rating;
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
