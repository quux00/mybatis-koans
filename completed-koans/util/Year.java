package net.thornydev.mybatis.koan.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A object wrapper that represents a Year entity.
 * It can take a year either a Date object (in which case it ignores
 * all parts of the date except the year) or a String, which should
 * be a four-digit year, such as 1968.
 */
public class Year {
  private Date date;

  public Year(String year) {
    try {
      DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      // by convention set the date to first month, first day of the year it represents
      this.date = format.parse( dateString(year) );
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public Year(Date date) {
    this.date = date;
  }

  private String dateString(String year) {
    Matcher m = Pattern.compile("\\d{4}(-\\d{2}-\\d{2})?").matcher(year);
    if (! m.find() ) {
      throw new IllegalArgumentException("Year must match pattern 'yyyy' or 'yyyy-MM-dd'. Was: " + year);
    }

    // if capturing parens group not found then add a month and day to the date string
    if (m.group(1) == null) {
      return year + "-01-01";
    } else {
      return year;
    }
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
