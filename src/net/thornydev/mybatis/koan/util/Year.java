package net.thornydev.mybatis.koan.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Year {
	private Date date;
	
	public Year(String year) {
		if (year.length() != 4) {
			throw new IllegalArgumentException("Year must have four digits");
		}
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			this.date = format.parse(year + "-01-01");			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Year(Date date) {
		this.date = date;
	}
	
	public String getYear() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return String.valueOf( cal.get(Calendar.YEAR) - 1900 );
	}
	
	public Date getYearAsDate() {
		return date;
	}
}
