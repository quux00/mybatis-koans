package net.thornydev.mybatis.test.koan25;

import java.util.List;

import net.thornydev.mybatis.test.util.KoanSchoolMarm;

  import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
  
  public class Koan25ObjectFactory extends DefaultObjectFactory {
  
    private static final long serialVersionUID = 3627013739044L;
  
    @Override
    public <T> T create(final Class<T> type, final List<Class<?>> ctorArgTypes,
                        final List<Object> ctorArgs) {
      if (type.equals(Koan25Address.class)) {
        return this.<T>createAddress(ctorArgs);
      } else {
        return super.<T>create(type, ctorArgTypes, ctorArgs);
      }
    }
  
    private <T> T createAddress(final List<Object> ctorArgs) {
      KoanSchoolMarm.getInstance().setObjectFactoryUsed(true);
  
      final int expSize = 3;
      if (ctorArgs.size() != expSize) {
        throw new IllegalArgumentException("Expected " + expSize
                                           + " constructor args for Address class");
      }
  
      // construct with required actual ctor args (id in this case)
      final Koan25Address addr = new Koan25Address(((Number)ctorArgs.get(0)).intValue());
      String[] addresses = new String[]{ctorArgs.get(1).toString(), 
                                        ctorArgs.get(2).toString()};
      addr.setAddresses(addresses);
  
      @SuppressWarnings("unchecked")
      final T t = (T) addr;
      return t;
    }
  }
