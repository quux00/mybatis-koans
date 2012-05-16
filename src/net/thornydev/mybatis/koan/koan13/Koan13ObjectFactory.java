package net.thornydev.mybatis.koan.koan13;

import java.util.List;

import net.thornydev.mybatis.koan.util.ObjectFactoryCheck;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

public class Koan13ObjectFactory extends DefaultObjectFactory {
	
	private static final long serialVersionUID = -8362701815721739033L;

	@Override
	public <T> T create(Class<T> type, List<Class<?>> ctorArgTypes, List<Object> ctorArgs) {
		// TODO: override this create method and call the appropriate createXXX
		//       method below based on the Class type passed in
		// NOTE: make sure to handle the case where the Class passed in is not
		//       one of the specific types we are overriding below - those need
		//       to invoke the MyBatis default object factory creation code
		return null;
	}
	
	/**
	 * Create a City object using the constructor that uses its
	 * three-param constructor.
	 * 
	 * This usage of ObjectFactory adds no new net value and you
	 * wouldn't use the ObjectFactory in production code this way,
	 * but it serves to illustrate how to mimic what MyBatis does
	 * behind the scenes with an ObjectFactory. 
	 * 
	 * This method expects three constructorArgs as specified in the
	 * city-mapper.xml file.
	 */
	private <T> T createCity(List<Object> ctorArgs) {
		// leave this intact
		ObjectFactoryCheck.getInstance().setObjectFactoryUsed(true);
		
		// TODO: fill in, using the three param constructor of City
		return null;
	}

	/**
	 * Create an Actor object using the constructor that takes a Map
	 * of field names to field values.
	 * 
	 * This method expects four constructorArgs as specified in the
	 * actor-mapper.xml file.
	 */
	private <T> T createActor(List<Object> ctorArgs) {		
		// leave this intact
		ObjectFactoryCheck.getInstance().setObjectFactoryUsed(true);
		
		// TODO: fill in using the constructor of Actor that takes a Map
		return null;
	}

	/**
	 * Create an Address object using the Builder idiom.
	 * 
	 * This method expects seven constructorArgs as specified in the
	 * address-mapper.xml file.
	 */
	private <T> T createAddress(List<Object> ctorArgs) {
		// leave this intact
		ObjectFactoryCheck.getInstance().setObjectFactoryUsed(true);
		
		// TODO: fill in using the Address.Builder inner class of Address
		return null;
	}

}
