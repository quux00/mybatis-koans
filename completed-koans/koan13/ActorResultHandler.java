package net.thornydev.mybatis.koan.koan13;

import net.thornydev.mybatis.koan.domain.Actor;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

public class ActorResultHandler implements ResultHandler {
	
	private Actor actor = null;
	
	@Override
	public void handleResult(ResultContext rc) {
		actor = (Actor) rc.getResultObject();
	}
	
	public Actor getActor() {
		return actor;
	}
}
