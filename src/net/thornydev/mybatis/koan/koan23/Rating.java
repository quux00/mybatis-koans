package net.thornydev.mybatis.koan.koan23;

public enum Rating {
  G, 
  PG, 
  NC_17, 
  PG_13, 
  R;

  @Override
  public String toString() {
    return super.toString().replace('_', '-');
  }
  
  // following Josh Bloch's recommendation in Effective Java, 2e
  // to define a static from fromString method if you need to 
  // tweak expected incoming strings to match what the default
  // valueOf method can handle
  public static Rating fromString(String s) {
    return Rating.valueOf(s.replace('-', '_').toUpperCase());
  }
}
