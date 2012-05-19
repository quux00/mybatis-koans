package net.thornydev.mybatis.koan.domain;

import java.util.Date;

/**
 * Country domain object.  This domain object references no other
 * domain objects - it is "standalone".
 *
 * In this domain object, we allow it to be populated only by
 * JavaBeans style setters and getters.  No constructor or
 * Builder is provider.
 */
public class Country {
  private int id;
  private String country;
  private Date lastUpdate;

  // setters and getters for all fields

  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getCountry() {
    return country;
  }
  public void setCountry(String country) {
    this.country = country;
  }
  public Date getLastUpdate() {
    return lastUpdate;
  }
  public void setLastUpdate(Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
