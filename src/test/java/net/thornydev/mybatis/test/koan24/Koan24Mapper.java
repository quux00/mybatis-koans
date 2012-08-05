package net.thornydev.mybatis.test.koan24;

import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import net.thornydev.mybatis.test.domain.Actor;

public interface Koan24Mapper {
  CopyOnWriteArrayList<Actor> getActorsInFilm(int filmId);
  CopyOnWriteArrayList<Map<String,Object>> getActorsInFilmAsMap(int filmId);
}
