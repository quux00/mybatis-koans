package net.thornydev.mybatis.koan.domain;

import java.util.Date;
import java.util.List;

import net.thornydev.mybatis.koan.koan10.SimpleFilm;

public class Language {
	private int id;
	private String name;
	private Date lastUpdate;
	// has many relationship
	private List<SimpleFilm> films;

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

	public List<SimpleFilm> getFilms() {
		return films;
	}

	public void setFilms(List<SimpleFilm> films) {
		this.films = films;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
