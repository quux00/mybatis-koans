package net.thornydev.mybatis.test.koan14;

import java.util.ArrayList;
import java.util.List;

import net.thornydev.mybatis.test.domain.Actor;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

public class ActorResultHandler implements ResultHandler {

  private final List<Actor> actors = new ArrayList<Actor>();

  @Override
  public void handleResult(final ResultContext rc) {

    final Actor actor = (Actor) rc.getResultObject();

    if (actor.getLastName().length() != 4) {
      actors.add(actor);
    }

  }

  public List<Actor> getActors() {
    return actors;
  }

}
