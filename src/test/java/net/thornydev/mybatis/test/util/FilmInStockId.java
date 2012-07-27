package net.thornydev.mybatis.test.util;

/**
 * Simple data holder for the ids
 * output from the film_in_stock
 * stored procedure.
 */
public class FilmInStockId {
  private Integer filmId;

  public Integer getFilmId() {
    return filmId;
  }

  public void setFilmId(Integer filmId) {
    this.filmId = filmId;
  }
}
