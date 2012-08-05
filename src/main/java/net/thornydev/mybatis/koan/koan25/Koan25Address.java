package net.thornydev.mybatis.koan.koan25;

// Do not modify anything in this class for this koan
public class Koan25Address {
  private final int id;
  private String[] addresses;

  public Koan25Address(final int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public String[] getAddresses() {
    return addresses;
  }

  public void setAddresses(String[] addresses) {
    this.addresses = addresses;
  }
}
