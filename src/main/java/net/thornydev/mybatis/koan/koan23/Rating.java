package net.thornydev.mybatis.koan.koan23;

public enum Rating {
  G,
  PG,
  NC_17,
  PG_13,
  R;

  // TODO: override the toString method in order to map between the
  // sakila database version of the name (title case) with the Java
  // version (all caps)
  @Override
  public String toString() {
    return null;
  }

  // Follow Josh Bloch's recommendation in Effective Java, 2ed
  // to define a static fromString method when you need to
  // tweak expected incoming strings to match what the default
  // valueOf method can handle
  public static Rating fromString(String s) {
    // TODO: implement this method
    return null;
  }
}
