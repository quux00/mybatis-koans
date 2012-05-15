package net.thornydev.mybatis.koan.domain;

import java.util.Date;
import java.util.Map;

/**
 * Immutable Address class, which has a has-one relationship
 * with the City domain object.
 * 
 * This version of Address requires you to populate a HashMap mapping 
 * field names to values in order to set all of the final fields in the
 * constructor. 
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
	
	private Address(Address.Builder b) {
		this.id = b.id;
		this.address = b.address;
		this.address2 = b.address2;
		this.district = b.district;
		this.postalCode = b.postalCode;
		this.phone = b.phone;
		this.lastUpdate = b.lastUpdate;
		this.city = b.city;		
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
	
	// Ignore this constructor for Koan12
	public Address(Map<String, Object> fieldsMap) {
		this.id         = (Integer) fieldsMap.get("id");
		this.address    = (String)  fieldsMap.get("address");
		this.address2   = (String)  fieldsMap.get("address2");
		this.district   = (String)  fieldsMap.get("district");
		this.postalCode = (String)  fieldsMap.get("postalCode");
		this.phone      = (String)  fieldsMap.get("phone");
		this.city       = (City)    fieldsMap.get("city");
		if (fieldsMap.containsKey("lastUpdate")) {
			this.lastUpdate = (Date) fieldsMap.get("lastUpdate");
		} else {
			this.lastUpdate = new Date();
		}
	}

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
