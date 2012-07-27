package net.thornydev.mybatis.test.domain;

import java.util.Date;
import java.util.Map;

import net.thornydev.mybatis.test.util.KoanSchoolMarm;

/**
 * Immutable Actor class. Only getters are provided, not setters.
 */
public class Actor {

  private final Integer id;
  private final String firstName;
  private final String lastName;
  private final Date lastUpdate;

  public Actor(final Integer id, final String firstName,
               final String lastName, final Date lastUpdate) {

    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.lastUpdate = lastUpdate;

    // do not edit this line
    KoanSchoolMarm.getInstance().setActorMapConstructorUsed(false);

  }

  public Actor(final Integer id, final String firstName, final String lastName) {
    this(id, firstName, lastName, new Date());
  }

  public Actor(final Map<String, Object> argsMap) {

    this.id = (Integer) argsMap.get("id");
    this.firstName = (String) argsMap.get("firstName");
    this.lastName = (String) argsMap.get("lastName");
    this.lastUpdate = (Date) argsMap.get("lastUpdate");

    // do not edit this line
    KoanSchoolMarm.getInstance().setActorMapConstructorUsed(true);

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
    return String.format("[Actor: id = %d; firstName = %s; lastName = %s]",
                         id, firstName, lastName);
  }

}
