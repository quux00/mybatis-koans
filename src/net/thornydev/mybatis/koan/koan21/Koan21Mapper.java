package net.thornydev.mybatis.koan.koan21;

import java.util.List;

import net.thornydev.mybatis.koan.domain.Film;

public interface Koan21Mapper {
  List<String> selectLongFilmsByTitleOrRating(Film f);
}
