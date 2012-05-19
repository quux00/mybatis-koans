package net.thornydev.mybatis.koan.koan14;

import java.util.ArrayList;
import java.util.List;

import net.thornydev.mybatis.koan.domain.Actor;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

public class ActorResultHandler implements ResultHandler {

  private List<Actor> actors = new ArrayList<Actor>();

  @Override
  public void handleResult(ResultContext rc) {
    Actor actor = (Actor) rc.getResultObject();
    if (actor.getLastName().length() != 4) {
      actors.add(actor);
    }
  }

  public List<Actor> getActors() {
    return actors;
  }
}
