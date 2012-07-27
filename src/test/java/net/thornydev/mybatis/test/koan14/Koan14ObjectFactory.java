package net.thornydev.mybatis.test.koan14;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.thornydev.mybatis.test.domain.Actor;
import net.thornydev.mybatis.test.domain.Address;
import net.thornydev.mybatis.test.domain.City;
import net.thornydev.mybatis.test.util.KoanSchoolMarm;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

public class Koan14ObjectFactory extends DefaultObjectFactory {

	private static final long serialVersionUID = -8362701815721739033L;

	@Override
	public <T> T create(final Class<T> type, final List<Class<?>> ctorArgTypes,
			final List<Object> ctorArgs) {

		if (type.equals(Actor.class)) {
			return this.<T>createActor(ctorArgs);
		} else if (type.equals(Address.class)) {
			return this.<T>createAddress(ctorArgs);
		} else if (type.equals(City.class)) {
			return this.<T>createCity(ctorArgs);
		} else {
			return super.<T>create(type, ctorArgTypes, ctorArgs);
		}
	}

	/**
	 * Create a City object using the constructor that uses its three-param
	 * constructor.
	 * 
	 * This usage of ObjectFactory adds no new net value and you wouldn't use
	 * the ObjectFactory in production code this way, but it serves to
	 * illustrate how to mimic what MyBatis does behind the scenes with an
	 * ObjectFactory.
	 */
	private <T> T createCity(final List<Object> ctorArgs) {

		KoanSchoolMarm.getInstance().setObjectFactoryUsed(true);

		final int expSize = 3;

		if (ctorArgs.size() != expSize) {
			throw new IllegalArgumentException("Expected " + expSize
					+ " constructor args for City class");
		}

		final int id = (Integer) ctorArgs.get(0);
		final String city = (String) ctorArgs.get(1);
		final Date lastUpdate = (Date) ctorArgs.get(2);

		@SuppressWarnings("unchecked")
		final T addr = (T) new City(id, city, lastUpdate);

		return addr;

	}

	private <T> T createActor(final List<Object> ctorArgs) {

		KoanSchoolMarm.getInstance().setObjectFactoryUsed(true);

		final int expSize = 4;

		if (ctorArgs.size() != expSize) {
			throw new IllegalArgumentException("Expected " + expSize
					+ " constructor args for Actor class");
		}

		final Map<String, Object> argMap = new HashMap<String, Object>();

		argMap.put("id", ctorArgs.get(0));
		argMap.put("firstName", ctorArgs.get(1));
		argMap.put("lastName", ctorArgs.get(2));
		argMap.put("lastUpdate", ctorArgs.get(3));

		// FIXME: not sure this is the right/best way to do this!
		@SuppressWarnings("unchecked")
		final T actor = (T) new Actor(argMap);

		return actor;

	}

	private <T> T createAddress(final List<Object> ctorArgs) {

		KoanSchoolMarm.getInstance().setObjectFactoryUsed(true);

		final int expSize = 7;

		if (ctorArgs.size() != expSize) {
			throw new IllegalArgumentException("Expected " + expSize
					+ " constructor args for Address class");
		}

		// use the Builder
		final Address addr = new Address.Builder()
				.id((Integer) ctorArgs.get(0))
				.address((String) ctorArgs.get(1))
				.address2((String) ctorArgs.get(2))
				.district((String) ctorArgs.get(3))
				.postalCode((String) ctorArgs.get(4))
				.phone((String) ctorArgs.get(5))
				.lastUpdate((Date) ctorArgs.get(6)).build();

		@SuppressWarnings("unchecked")
		final T t = (T) addr;

		return t;

	}

}
