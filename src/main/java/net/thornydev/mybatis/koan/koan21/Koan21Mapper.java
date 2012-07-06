package net.thornydev.mybatis.koan.koan21;

import java.math.BigDecimal;
import java.util.List;

import net.thornydev.mybatis.koan.domain.Film;

public interface Koan21Mapper {
  List<String> selectLongFilmsByTitleOrRating(Film f);
  List<String> selectFilmByTitleRatingAndOrMinLength(Film f);
  List<String> selectFilmByRentalRates(double[] rates);
  // TODO: you may need to annotate this method to get it to pass, depending how
  //       you write the SQL mapping
  List<String> selectFilmByRentalRates2(List<BigDecimal> rates);
  void updateFilmIfNecessary(Film f);
  Film getFilmById(int id);
}
