package net.thornydev.mybatis.koan.koan23;

/**
 * Model the category table as a Java enum
 */
public enum Category {
  
  ACTION(1),
  ANIMATION(2),
  CHILDREN(3),
  CLASSICS(4),
  COMEDY(5),
  DOCUMENTARY(6),
  DRAMA(7),
  FAMILY(8),
  FOREIGN(9),
  GAMES(10),
  HORROR(11),
  MUSIC(12),
  NEW(13),
  SCI_FI(14),
  SPORTS(15),
  TRAVEL(16);

  // corresponds to the ID in the category table 
  // in the sakila database
  private int id;

  Category(int id) {
    this.id = id;
  }
  
  public int getId() {
    return id;
  }
  
  @Override
  public String toString() {
    String std = super.toString(); 
    return std.substring(0, 1).toUpperCase() + std.substring(1, std.length()).toLowerCase();
  }

  public static Category fromString(String s) {
    return Category.valueOf(s.toUpperCase());
  }
}

