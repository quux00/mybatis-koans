package net.thornydev.mybatis.koan.koan10;

import java.util.Date;
import java.util.List;

import net.thornydev.mybatis.koan.koan10.FilmK10;

public class LanguageK10 {
	private int id;
	private String name;
	private Date lastUpdate;
	// has many relationship
	private List<FilmK10> films;

	public LanguageK10(int id, String langName) {
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

	public List<FilmK10> getFilms() {
		return films;
	}

	public void setFilms(List<FilmK10> films) {
		this.films = films;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
