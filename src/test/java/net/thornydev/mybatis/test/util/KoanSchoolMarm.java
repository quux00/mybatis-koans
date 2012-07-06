package net.thornydev.mybatis.test.util;

/**
 * Simplistic singleton global to enforce rules of use in Koan14.
 * Koan students will not need to edit this class or use it directly.
 *
 * To use it, the ObjectFactory create method(s) you implement should call
 *   ObjectFactoryCheck.getInstance().setObjectFactoryUsed(true)
 * and the test koan should call
 *   ObjectFactoryCheck.getInstance().setObjectFactoryUsed(false)
 * to reset it before the next test runs.
 *
 * Same goes for the other rules it enforces.
 */
public class KoanSchoolMarm {
  private static final KoanSchoolMarm INSTANCE = new KoanSchoolMarm();

  public static KoanSchoolMarm getInstance() {
    return INSTANCE;
  }

  private boolean objectFactoryUsed = false;
  private boolean actorMapCtorUsed = false;
  private boolean addrBuilderUsed = false;

  public void setObjectFactoryUsed(boolean b) {
    this.objectFactoryUsed = b;
  }

  public boolean getObjectFactoryUsed() {
    return objectFactoryUsed;
  }

  public void setActorMapConstructorUsed(boolean b) {
    actorMapCtorUsed = b;
  }

  public boolean getActorMapConstructorUsed() {
    return actorMapCtorUsed;
  }

  public void setAddressBuilderConstructorUsed(boolean b) {
    addrBuilderUsed = b;
  }

  public boolean setAddressBuilderConstructorUsed() {
    return addrBuilderUsed;
  }
}
