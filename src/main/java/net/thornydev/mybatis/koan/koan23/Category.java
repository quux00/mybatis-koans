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

  // TODO: override the toString method in order to map between the
  // sakila database version of the name (title case) with the Java
  // version (all caps)
  @Override
  public String toString() {
    return null;
  }

}

