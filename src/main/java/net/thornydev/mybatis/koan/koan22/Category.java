package net.thornydev.mybatis.koan.koan22;

/**
 * Simple DTO representation of the Category table
 * (ignoring the last_update field)
 */
public class Category {
  private int id;
  private String name;

  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
}
