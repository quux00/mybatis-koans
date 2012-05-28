package net.thornydev.mybatis.koan.koan19;

import java.util.List;

import net.thornydev.mybatis.koan.domain.Staff;
import net.thornydev.mybatis.koan.util.Email;
import net.thornydev.mybatis.koan.util.EmailImpl;
import net.thornydev.mybatis.koan.util.NullEmail;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

public class Koan19ObjectFactory extends DefaultObjectFactory {
  private static final long serialVersionUID = 2339L;

  @Override
  public <T> T create(Class<T> type, List<Class<?>> ctorArgTypes, List<Object> ctorArgs) {
    if (type.equals(Staff.class)) {
      return createStaff(ctorArgs);
    } else {
      return super.create(type, ctorArgTypes, ctorArgs);
    }
  }

  private <T> T createStaff(List<Object> ctorArgs) {
    String emailStr = (String) ctorArgs.get(1);
    Email email = null;
    if (emailStr == null || emailStr.equals("")) {
      email = new NullEmail();
    } else {
      email = new EmailImpl(emailStr);
    }

    Staff staff = new Staff((Integer) ctorArgs.get(0), email);

    @SuppressWarnings("unchecked")
    T t = (T) staff;
    return t;
  }

  // This was an attempt to let the EmailTypeHandler determine which type of
  // Email object to return, but as of MyBatis-3.1.1 it doesn't work.  If the
  // email setting is null in the db, even though the EmailTypeHandler returns
  // a NullEmail object, null is still passed to this ObjectFactory ... :-(
  @SuppressWarnings("unused")
  private <T> T createStaff2(List<Object> ctorArgs) {
    System.out.println("DEBUG aaa: " + ctorArgs.get(1));
    Staff staff = new Staff((Integer) ctorArgs.get(0), (Email) ctorArgs.get(1));
    @SuppressWarnings("unchecked")
    T t = (T) staff;
    return t;
  }
}
