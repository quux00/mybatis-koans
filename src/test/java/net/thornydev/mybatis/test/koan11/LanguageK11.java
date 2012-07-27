package net.thornydev.mybatis.test.koan11;

import java.util.Date;
import java.util.List;

import net.thornydev.mybatis.test.koan11.FilmK11;

public class LanguageK11 {
  private int id;
  private String name;
  private Date lastUpdate;
  // has many relationship
  private List<FilmK11> films;

  public LanguageK11(int id, String langName) {
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

  public List<FilmK11> getFilms() {
    return films;
  }

  public void setFilms(List<FilmK11> films) {
    this.films = films;
  }

  public Date getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
