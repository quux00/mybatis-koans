package net.thornydev.mybatis.koan.koan13;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.thornydev.mybatis.koan.domain.Actor;
import net.thornydev.mybatis.koan.domain.Address;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

public class Koan13ObjectFactory extends DefaultObjectFactory {
	
	private static final long serialVersionUID = -8362701815721739033L;

	@Override
	public <T> T create(Class<T> type, List<Class<?>> ctorArgTypes, List<Object> ctorArgs) {
		if (type.equals(Actor.class)) {
			return createActor(ctorArgs);
		} else if (type.equals(Address.class)) {
			return createAddress(ctorArgs);
		} else {
			return super.create(type, ctorArgTypes, ctorArgs);
		}
	}
	
	private <T> T createAddress(List<Object> ctorArgs) {
		Map<String,Object> argMap = new HashMap<String,Object>();
		argMap.put("id", ctorArgs.get(0));
		argMap.put("address", ctorArgs.get(1));
		argMap.put("address2", ctorArgs.get(2));
		argMap.put("district", ctorArgs.get(3));
		argMap.put("postalCode", ctorArgs.get(4));
		argMap.put("phone", ctorArgs.get(5));
		@SuppressWarnings("unchecked")
		T addr = (T) new Address(argMap);
		return addr;
	}

	private <T> T createActor(List<Object> ctorArgs) {
		final int expSize = 4; 
		if (ctorArgs.size() != expSize) {
			throw new IllegalArgumentException("Expected "+expSize+" constructor args for Actor class");
		}
		System.out.println(ctorArgs.toString());
		Integer id = (Integer)ctorArgs.get(0);
		String fname = (String)ctorArgs.get(1);
		String lname = (String)ctorArgs.get(2);
		Date lastUpd = (Date)ctorArgs.get(3);
		// FIXME: not sure this is the right/best way to do this!
		@SuppressWarnings("unchecked")
		T actor = (T) new Actor(id, fname, lname, lastUpd);
		return actor;
	}
}
