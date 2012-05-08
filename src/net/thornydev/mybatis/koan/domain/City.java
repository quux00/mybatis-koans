package net.thornydev.mybatis.koan.domain;

import java.util.Date;

/**
 * City domain object. This domain object references one other
 * domain object - Country.
 * 
 * We provide setters and getters as well as a single constructor
 * that takes entries for all of its fields.
 */
public class City {
	private int id;
	private String city;
	private Date lastUpdate;
	private Country country;
	
	public City(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
}
