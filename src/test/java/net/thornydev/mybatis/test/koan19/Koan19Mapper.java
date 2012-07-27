package net.thornydev.mybatis.test.koan19;

import net.thornydev.mybatis.test.domain.Staff;

import org.apache.ibatis.annotations.Param;

public interface Koan19Mapper {
  Staff getStaffById(int id);
  void setEmail(@Param("email") String email, @Param("id") int id);
}
