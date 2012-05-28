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
	private final int id;
	private final String firstName;
	private final String lastName;
	private final String username;
	private final String password;
	private final boolean active;
	
	private final Email email;
	private final Address address;
	
	@SuppressWarnings("unused")
	private Staff() {
		this(Integer.MIN_VALUE, null, null, null, null, false, null, null);
	}
	
	public Staff(int id, String firstName, String lastName, String username,
			String password, boolean active, Email email, Address address) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.active = active;
		this.email = email;
		this.address = address;
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
