package net.thornydev.mybatis.test.koan26;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

public class Koan26ResultHandler implements ResultHandler {

  Map<Integer,String> inMap = new HashMap<Integer,String>(); 
  
  public Map<Integer, String> getIdNameMap() {
    return inMap;
  }

  @Override
  public void handleResult(ResultContext rc) {
    @SuppressWarnings("unchecked")
    Map<String,Object> m = (Map<String,Object>)rc.getResultObject();
    Integer id = Integer.valueOf( ((Number)getFromMap(m, "id")).intValue() );
    inMap.put(id, (String)getFromMap(m, "country"));
  }

  private Object getFromMap(Map<String, Object> map, String key) {
    if (map.containsKey(key.toLowerCase())) {
      return map.get(key.toLowerCase());
    } else {
      return map.get(key.toUpperCase());
    }
  }
}
