package net.thornydev.mybatis.koan.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import net.thornydev.mybatis.koan.util.Year;

public class Film {
	private int id;
	private String title;
	private String description;
	private int rentalDuration;
	private int length;
	private String rating;
	private Date lastUpdate;
	
	// the next four need special type handlers (?)
	private Year releaseYear;
	private List<String> specialFeatures;
	private BigDecimal rentalRate;
	private BigDecimal replacementCost;

	// skipping "full text" column (or table) since it is treated differently
	// in the different databases

	public Film() {}

	public Film(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Year getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(Year releaseYear) {
		this.releaseYear = releaseYear;
	}

	public int getRentalDuration() {
		return rentalDuration;
	}

	public void setRentalDuration(int rentalDuration) {
		this.rentalDuration = rentalDuration;
	}

	public BigDecimal getRentalRate() {
		return rentalRate;
	}

	public void setRentalRate(BigDecimal rentalRate) {
		this.rentalRate = rentalRate;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public BigDecimal getReplacementCost() {
		return replacementCost;
	}

	public void setReplacementCost(BigDecimal replacementCost) {
		this.replacementCost = replacementCost;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public List<String> getSpecialFeatures() {
		return specialFeatures;
	}

	public void setSpecialFeatures(List<String> specialFeatures) {
		this.specialFeatures = specialFeatures;
	}
}