package net.thornydev.mybatis.koan.koan20;

import java.util.List;

import net.thornydev.mybatis.koan.domain.Film;

public interface Koan20Mapper {
  List<String> selectLongFilmsByTitleOrRating(Film f);
}
