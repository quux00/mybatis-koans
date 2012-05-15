package net.thornydev.mybatis.koan.util;

/**
 * Simplistic singleton global to check that a user-defined ObjectFactory  
 * was used to create domain objects.
 * 
 * To use it, the ObjectFactory create method(s) you implement should call 
 *   ObjectFactoryCheck.getInstance().setObjectFactoryUsed(true)
 * and the test koan should call
 *   ObjectFactoryCheck.getInstance().setObjectFactoryUsed(false)
 * to reset it before the next test runs
 */
public class ObjectFactoryCheck {
	private static final ObjectFactoryCheck INSTANCE = new ObjectFactoryCheck();
	
	public static ObjectFactoryCheck getInstance() {
		return INSTANCE;
	}
	
	private boolean flag = false;
	
	public void setObjectFactoryUsed(boolean b) {
		this.flag = b;
	}
	
	public boolean getObjectFactoryUsed() {
		return flag;
	}
}
