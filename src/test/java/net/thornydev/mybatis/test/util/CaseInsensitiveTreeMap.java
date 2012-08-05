package net.thornydev.mybatis.test.util;

import java.util.TreeMap;

public class CaseInsensitiveTreeMap<K,V> extends TreeMap<String, V> {

  private static final long serialVersionUID = -5474210389842104764L;

  public CaseInsensitiveTreeMap() {
    super(String.CASE_INSENSITIVE_ORDER);
  }
}
