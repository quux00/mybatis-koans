package net.thornydev.mybatis.test.koan21;

import java.math.BigDecimal;
import java.util.List;

import net.thornydev.mybatis.test.domain.Film;

import org.apache.ibatis.annotations.Param;

public interface Koan21Mapper {
  List<String> selectLongFilmsByTitleOrRating(Film f);
  List<String> selectFilmByTitleRatingAndOrMinLength(Film f);
  List<String> selectFilmByRentalRates(double[] rates);
  List<String> selectFilmByRentalRates2(@Param("rates") List<BigDecimal> rates);
  void updateFilmIfNecessary(Film f);
  Film getFilmById(int id);
}
