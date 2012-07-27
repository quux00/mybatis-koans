package net.thornydev.mybatis.test.domain;

import java.util.Date;
import java.util.List;

/**
 * Language domain object. This domain object references one other
 * domain object, Film, and keeps a list of all Films that are in
 * this language.
 *
 * We provide setters and getters as well as a single constructor
 * that takes entries for all of two of its fields.
 */
public class Language {
  private int id;
  private String name;
  private Date lastUpdate;
  // has-many relationship modeled here
  private List<Film> films;

  public Language(int id, String langName) {
    this.id = id;
    this.name = langName;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Film> getFilms() {
    return films;
  }

  public void setFilms(List<Film> films) {
    this.films = films;
  }

  public Date getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
