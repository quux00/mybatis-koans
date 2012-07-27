package net.thornydev.mybatis.test.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import net.thornydev.mybatis.test.util.Year;

/**
 * Film domain object. The sakila film table is referenced by language
 * but this Film domain object is unaware of the Language class.
 */
public class Film {
  private int id;
  private String title;
  private String description;
  private int rentalDuration;
  private int length;
  private String rating;
  private Date lastUpdate;
  private BigDecimal rentalRate;
  private BigDecimal replacementCost;

  // the next two need special type handlers
  private Year releaseYear;
  private List<String> specialFeatures;

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

  @Override
  public boolean equals(Object o) {
    if (! (o instanceof Film)) return false;
    Film other = (Film)o;

    return this.id == other.id &&
           this.rentalDuration == other.rentalDuration &&
           this.length == other.length &&
           ((this.title == other.title) || (this.title.equals(other.title))) &&
           ((this.description == other.description) || (this.description.equals(other.description))) &&
           ((this.rating == other.rating) || (this.rating.equals(other.rating))) &&
           ((this.lastUpdate == other.lastUpdate) || (this.lastUpdate.equals(other.lastUpdate))) &&
           ((this.rentalRate == other.rentalRate) || (this.rentalRate.compareTo(other.rentalRate)) == 0) &&
           ((this.replacementCost == other.replacementCost) || (this.replacementCost.compareTo(other.replacementCost)) == 0);
  }
}