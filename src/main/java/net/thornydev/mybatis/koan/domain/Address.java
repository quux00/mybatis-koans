package net.thornydev.mybatis.koan.domain;

import java.util.Date;

import net.thornydev.mybatis.koan.util.KoanSchoolMarm;

/**
 * Immutable Address class, which has a has-one relationship
 * with the City domain object.
 */
public class Address {
  private final Integer id;
  private final String address;
  private final String address2;
  private final String district;
  private final String postalCode;
  private final String phone;
  private final Date lastUpdate;

  private final City city;

  /* ---[ Internal Builder class ]--- */

  public static class Builder {
    private Integer id;
    private String address;
    private String address2;
    private String district;
    private String postalCode;
    private String phone;
    private Date lastUpdate;
    private City city;

    public Address.Builder id(Integer id) {
      this.id = id;
      return this;
    }

    public Address.Builder address(String address) {
      this.address = address;
      return this;
    }

    public Address.Builder address2(String address2) {
      this.address2 = address2;
      return this;
    }

    public Address.Builder district(String district) {
      this.district = district;
      return this;
    }

    public Address.Builder postalCode(String postalCode) {
      this.postalCode = postalCode;
      return this;
    }

    public Address.Builder phone(String phone) {
      this.phone = phone;
      return this;
    }

    public Address.Builder lastUpdate(Date lastUpdate) {
      this.lastUpdate = lastUpdate;
      return this;
    }

    public Address.Builder city(City city) {
      this.city = city;
      return this;
    }

    public Address build() {
      return new Address(this);
    }
  }

  /* ---[ Long list of (too many) constructors ]--- */

  private Address(Address.Builder b) {
    this.id = b.id;
    this.address = b.address;
    this.address2 = b.address2;
    this.district = b.district;
    this.postalCode = b.postalCode;
    this.phone = b.phone;
    this.lastUpdate = b.lastUpdate;
    this.city = b.city;

    // do not edit this line
    KoanSchoolMarm.getInstance().setAddressBuilderConstructorUsed(true);
  }

  public Address(Integer id, String address, String address2, String district,
                 String postalCode, String phone, Date lastUpdate, City city) {
    this.id = id;
    this.address = address;
    this.address2 = address2;
    this.district = district;
    this.postalCode = postalCode;
    this.phone = phone;
    this.lastUpdate = lastUpdate;
    this.city = city;
  }

  public Address(Integer id, String address, String address2,
                 String district, String postalCode, String phone, City city) {
    this(id, address, address2, district, postalCode, phone, new Date(), city);
  }

  public Address(Integer id, String address, String address2,
                 String district, String postalCode, String phone) {
    this(id, address, address2, district, postalCode, phone, new Date(), null);
  }

  /* ---[ Getters and Setters ]--- */

  public Integer getId() {
    return id;
  }

  public String getAddress() {
    return address;
  }

  public String getAddress2() {
    return address2;
  }

  public String getDistrict() {
    return district;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public String getPhone() {
    return phone;
  }

  public Date getLastUpdate() {
    return lastUpdate;
  }

  public City getCity() {
    return city;
  }
}
