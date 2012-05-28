package net.thornydev.mybatis.koan.domain;

import net.thornydev.mybatis.koan.util.Email;

/**
 * Domain object that maps to the Staff table
 * of the sakila database. 
 * 
 * Currently, this is an incomplete impl of all
 * fields of the table.
 */
public class Staff {
	private int id;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private boolean active;
	
	private Email email;
	private Address address;

	public Staff() {}
	
	public Staff(int id, Email email) {
		this.id = id;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean isActive() {
		return active;
	}

	public Email getEmail() {
		return email;
	}

	public Address getAddress() {
		return address;
	}
}
