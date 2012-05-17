package net.thornydev.mybatis.koan.koan14;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.thornydev.mybatis.koan.domain.Actor;
import net.thornydev.mybatis.koan.domain.Address;
import net.thornydev.mybatis.koan.domain.City;
import net.thornydev.mybatis.koan.util.ObjectFactoryCheck;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

public class Koan14ObjectFactory extends DefaultObjectFactory {
	
	private static final long serialVersionUID = -8362701815721739033L;

	@Override
	public <T> T create(Class<T> type, List<Class<?>> ctorArgTypes, List<Object> ctorArgs) {
		if (type.equals(Actor.class)) {
			return createActor(ctorArgs);
		} else if (type.equals(Address.class)) {
			return createAddress(ctorArgs);
		} else if (type.equals(City.class)) {
			return createCity(ctorArgs);
		} else {
			return super.create(type, ctorArgTypes, ctorArgs);
		}
	}
	
	/**
	 * Create a City object using the constructor that uses its
	 * three-param constructor.
	 * 
	 * This usage of ObjectFactory adds no new net value and you
	 * wouldn't use the ObjectFactory in production code this way,
	 * but it serves to illustrate how to mimic what MyBatis does
	 * behind the scenes with an ObjectFactory. 
	 */
	private <T> T createCity(List<Object> ctorArgs) {
		ObjectFactoryCheck.getInstance().setObjectFactoryUsed(true);

		final int expSize = 3; 
		if (ctorArgs.size() != expSize) {
			throw new IllegalArgumentException("Expected "+expSize+" constructor args for City class");
		}
		int id = (int) ctorArgs.get(0);
		String city = (String) ctorArgs.get(1);
		Date lastUpdate = (Date) ctorArgs.get(2);
		@SuppressWarnings("unchecked")
		T addr = (T) new City(id, city, lastUpdate);
		return addr;
	}

	private <T> T createActor(List<Object> ctorArgs) {		
		ObjectFactoryCheck.getInstance().setObjectFactoryUsed(true);

		final int expSize = 4;
		if (ctorArgs.size() != expSize) {
			throw new IllegalArgumentException("Expected "+expSize+" constructor args for Actor class");
		}
		
		Map<String,Object> argMap = new HashMap<String,Object>();
		argMap.put("id", ctorArgs.get(0));
		argMap.put("firstName", ctorArgs.get(1));
		argMap.put("lastName", ctorArgs.get(2));
		argMap.put("lastUpdate", ctorArgs.get(3));
		// FIXME: not sure this is the right/best way to do this!
		@SuppressWarnings("unchecked")
		T actor = (T) new Actor(argMap);
		return actor;		
	}

	private <T> T createAddress(List<Object> ctorArgs) {
		ObjectFactoryCheck.getInstance().setObjectFactoryUsed(true);

		final int expSize = 7; 
		if (ctorArgs.size() != expSize) {
			throw new IllegalArgumentException("Expected "+expSize+" constructor args for Address class");
		}

		// use the Builder
		Address addr = new Address.Builder().
				id( (Integer) ctorArgs.get(0) ).
				address( (String) ctorArgs.get(1) ).
				address2( (String) ctorArgs.get(2) ).
				district( (String) ctorArgs.get(3) ).
				postalCode( (String) ctorArgs.get(4) ).
				phone( (String) ctorArgs.get(5) ).
				lastUpdate( (Date) ctorArgs.get(6) ).
				build();
		
		@SuppressWarnings("unchecked")
		T t = (T)addr;
		return t;
	}

}
