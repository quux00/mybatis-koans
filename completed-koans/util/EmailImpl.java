package net.thornydev.mybatis.koan.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailImpl implements Email {
	private final String username;
	private final String domain;
	
	public EmailImpl(String email) {
		String[] parts = validate(email);
		username = parts[0];
		domain = parts[1];
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getDomain() {
		return domain;
	}

	@Override
	public String getEmailAddress() {
		return String.format("%s@%s", username, domain);
	}
	
	private String[] validate(String email) {
		String[] parts = new String[2];
		
		// from: http://www.regular-expressions.info/email.html
		String re = "\\b([A-Z0-9._%+-]+)@([A-Z0-9.-]+\\.[A-Z]{2,4})\\b";
		Matcher m = Pattern.compile(re, Pattern.CASE_INSENSITIVE).matcher(email);
		if (m.matches()) {
			parts[0] = m.group(1);
			parts[1] = m.group(2);
		} else {
			throw new IllegalArgumentException("Malformed or illegal email format.");
		}
		
		return parts;
	}
	
//	public static void main(String[] args) {
//		Email e = new Email("michael.peterson@th.com");
//		System.out.println(e.getUsername());
//		System.out.println(e.getDomain());
//		System.out.println(e.getEmailAddress());
//	}
}
