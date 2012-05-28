package net.thornydev.mybatis.koan.util;

/**
 * NullEmail implements Email as an example
 * of the Null object pattern:
 * http://en.wikipedia.org/wiki/Null_Object_pattern
 */
public class NullEmail implements Email {

	@Override
	public String getUsername() {
		return "";
	}

	@Override
	public String getDomain() {
		return "";
	}

	@Override
	public String getEmailAddress() {
		return "";
	}

}
