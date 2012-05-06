package net.thornydev.mybatis.koan;

import java.util.Date;

/**
 * Country domain object.
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
