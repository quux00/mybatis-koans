package net.thornydev.mybatis.koan.domain;

import java.util.Date;

/**
 * Immutable Actor class.
 * Only getters are provided, not setters.
 */
public class Actor {
	private final Integer id;
	private final String firstName;
	private final String lastName;
	private final Date lastUpdate;

	public Actor(Integer id, String firstName, String lastName, Date lastUpdate) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.lastUpdate = lastUpdate;
	}
	
	public Actor(Integer id, String firstName, String lastName) {
		this(id, firstName, lastName, new Date());
	}

	public Integer getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	@Override
	public String toString() {
		return String.format("[Actor: id = %d; firstName = %s; lastName = %s]", id, firstName, lastName);
	}
}
