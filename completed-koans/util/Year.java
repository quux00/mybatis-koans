package net.thornydev.mybatis.koan.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A object wrapper that represents a Year entity.
 * It can take a year either a Date object (in which case it ignores
 * all parts of the date except the year) or a String, which should
 * be a four-digit year, such as 1968.
 */
public class Year {
	private Date date;
	
	public Year(String year) {
		if (year.length() != 4) {
			throw new IllegalArgumentException("Year must have four digits");
		}
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			// by convention set the date to first month, first day of the year it represents
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
		return String.valueOf( cal.get(Calendar.YEAR) );
	}
	
	public Date getYearAsDate() {
		return date;
	}
	
	@Override
	public String toString() {
		return getYear();
	}

	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof Year)) return false;
		Year other = (Year) obj;
		return other.getYear().equals(this.getYear());
	}
}
