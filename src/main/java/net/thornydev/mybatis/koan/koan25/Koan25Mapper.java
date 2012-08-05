package net.thornydev.mybatis.koan.koan25;

import org.apache.ibatis.annotations.Param;

public interface Koan25Mapper {
  Koan25Address getAddressById(int id);
  int addAddress2Data(@Param("id") int id, @Param("a2") String address2);
}
