package net.thornydev.mybatis.koan.koan24;

import java.util.List;
import java.util.Map;

import net.thornydev.mybatis.koan.domain.Actor;

public interface Koan24Mapper {
  // TODO: these won't pass the test without some changes somewhere
  List<Actor> getActorsInFilm(int filmId);
  List<Map<String,Object>> getActorsInFilmAsMap(int filmId);
}
