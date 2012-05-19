package net.thornydev.mybatis.koan.koan13;

import java.util.List;

import net.thornydev.mybatis.koan.domain.Actor;

public interface ActorMapper {
  Actor getActorById(int id);
  List<Actor> getActorByFullName(String firstName, String lastName);
  int insertNewActor(Actor a);
  int insertNewActorGetNextIdFromDb(Actor a);
}
