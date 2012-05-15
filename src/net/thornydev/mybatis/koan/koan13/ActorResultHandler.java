package net.thornydev.mybatis.koan.koan13;

import net.thornydev.mybatis.koan.domain.Actor;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

public class ActorResultHandler implements ResultHandler {
	
	private Actor actor = null;
	
	@Override
	public void handleResult(ResultContext rc) {
		int n = rc.getResultCount();
		Object o = rc.getResultObject();
		System.out.println("DEBUG333: result count: " + n);
		System.out.printf("DEBUG444: Object class: %s; Object = %s%n", o.getClass(), o.toString());

		actor = (Actor) o;
	}
	
	public Actor getActor() {
		return actor;
	}

}
